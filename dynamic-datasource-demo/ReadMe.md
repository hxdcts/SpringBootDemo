## spring-boot+aop实现多数据源切换
当对同一个请求的QPS达到一定程度时，系统的响应会出现瓶颈，一般都是在数据库上，这个时候数据库一般会采取各种措施，例如主从服务，分表分库，读写分离，缓存技术等等。一旦这几种出现，我们在技术上也要做相应的变通。大多数情况是从原始的单库单表变成了多库多表

例如：我们有一个表user_info，我们护绿其他字段，里面有两个字段id、remarks，当数据量达到一定程序后，系统做了分表分库（你也可以当成是主从）


假设我们有一个库test和test2
test库中数据如下：

id | remarks|
---|---
1 | test1
2 | test1

test2库中数据如下：

id | remarks|
---|---
1 | test2
2 | test2

最初单库单表时，我们可能直接使用spring-boot自动配置的方式，系统没有任何问题。如果我们现在要从两个表中查询数据原来的自动单数据源的方式就不再适用了，这个时候可能就涉及到了多数据源的程序了。多数据源有多种方式，接下来我们介绍采用spring-boot+AOP方式实现多数据源切换。

首先由于使用了spring-boot，我们还是让程序继承spring-boot-starter-parent，这样可以少管理一些版本。

```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.2.RELEASE</version>
</parent>
```
其次，我们引入相关依赖

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
	<groupId>com.zaxxer</groupId>
	<artifactId>HikariCP</artifactId>
</dependency>
<dependency>
	<groupId>com.github.pagehelper</groupId>
	<artifactId>pagehelper-spring-boot-starter</artifactId>
	<version>1.2.0</version>
	<exclusions>
		<exclusion>
			<artifactId>spring-boot-starter</artifactId>
			<groupId>org.springframework.boot</groupId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
```

以上依赖，为了引入了spring-web、mybatis、AOP、jdbc、mysql驱动、HikariCP连接池。

接下来我们开始实现动态数据源主要代码功能。

> 1、创建线程共享工具

由于我们的数据源信息要保证在同一线程下切换后不要被其他线程修改，所以我们将数据源信息保存在ThreadLocal共享中。

```
/**
 * 动态数据源持有者，负责利用ThreadLocal存取数据源名称
 */
public class DynamicDataSourceHolder {
	/**
	 * 本地线程共享对象
	 */
	private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

	public static void putDataSource(String name) {
		THREAD_LOCAL.set(name);
	}

	public static String getDataSource() {
		return THREAD_LOCAL.get();
	}

	public static void removeDataSource() {
		THREAD_LOCAL.remove();
	}
}
```
>2、实现动态数据源AbstractRoutingDataSource

spring为我们提供了AbstractRoutingDataSource，即带路由的数据源。继承后我们需要实现它的determineCurrentLookupKey()，该方法用于自定义实际数据源名称的路由选择方法，由于我们将信息保存到了ThreadLocal中，所以只需要从中拿出来即可。

```
/**
 * 动态数据源实现类
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource{
	//数据源路由，此方用于产生要选取的数据源逻辑名称
	@Override
	protected Object determineCurrentLookupKey() {
		//从共享线程中获取数据源名称
		return DynamicDataSourceHolder.getDataSource();
	}
}
```

>3、创建数据源切换方法注解

我们切换数据源时，一般都是在调用mapper接口的方法前实现，所以我们定义一个方法注解，当AOP检测到方法上有该注解时，根据注解中value对应的名称进行切换。

```
/**
 * 目标数据源注解，注解在方法上指定数据源的名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TargetDataSource {
	String value();//此处接收的是数据源的名称
}
```
>4、定义处理AOP切面 

动态数据源切换是基于AOP的，所以我们需要声明一个AOP切面，并在切面前做数据源切换，切面完成后移除数据源名称。
```
/**
 * 数据源AOP切面定义
 */
@Component
@Aspect
@Slf4j
public class DataSourceAspect {
	//切换放在mapper接口的方法上，所以这里要配置AOP切面的切入点
	@Pointcut("execution( * com.comven.example.mapper.*.*(..))")
	public void dataSourcePointCut() {
	}

	@Before("dataSourcePointCut()")
	public void before(JoinPoint joinPoint) {
		Object target = joinPoint.getTarget();
		String method = joinPoint.getSignature().getName();
		Class<?>[] clazz = target.getClass().getInterfaces();
		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		try {
			Method m = clazz[0].getMethod(method, parameterTypes);
			//如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
			if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
				TargetDataSource data = m.getAnnotation(TargetDataSource.class);
				String dataSourceName = data.value();
				DynamicDataSourceHolder.putDataSource(dataSourceName);
				log.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
			} else {
				log.debug("switch datasource fail,use default");
			}
		} catch (Exception e) {
			log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
		}
	}

	//执行完切面后，将线程共享中的数据源名称清空
	@After("dataSourcePointCut()")
	public void after(JoinPoint joinPoint){
		DynamicDataSourceHolder.removeDataSource();
	}
}
```

>5、定义多个数据源

之前我们假设中访问两个库两个表，假设test库数据源我们命名为test1，test2库数据源我们命名为test2。

我们先定义一个实际数据源配置类

```
/**
 * 实际数据源配置
 */
@Component
@Data
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {
	private HikariDataSource test1;
	private HikariDataSource test2;
}
```
在application.properties中，我们的配置是这样的

```
#test1数据源配置
hikari.test1.jdbc-url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false
hikari.test1.username=root
hikari.test1.password=123456
hikari.test1.maximum-pool-size=10
#test2数据源配置
hikari.test2.jdbc-url=jdbc:mysql://127.0.0.1:3306/test2?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false
hikari.test2.username=root
hikari.test2.password=123456
hikari.test2.maximum-pool-size=10
```

接下来我们采用@Bean注解完成动态数据源对象的申明

```
/**
 * 数据源配置
 */
@Configuration
@EnableScheduling
@Slf4j
public class DataSourceConfig {

	@Autowired
	private DBProperties properties;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		//按照目标数据源名称和目标数据源对象的映射存放在Map中
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("test1", properties.getTest1());
		targetDataSources.put("test2", properties.getTest2());
		//采用是想AbstractRoutingDataSource的对象包装多数据源
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		//设置默认的数据源，当拿不到数据源时，使用此配置
		dataSource.setDefaultTargetDataSource(properties.getTest1());
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}

}
```

>6、在mapper接口方法上做切换

由于我们的动态数据源配置了默认库，所以如果mapper方法是操作默认库的可以不需要注解，如果要操作非默认数据源，我们需要在方法上添加@TargetDataSource("数据源名称")注解。两个方法selectByOddUserId我们定义为奇数Id从test1库获取数据，selectByEvenUserId定义为偶数Id从test2库获取数据，
```
public interface UserInfoMapper {
	/**
	 * 从test1数据源中获取用户信息
	 */
	UserInfo selectByOddUserId(Integer id);
	/**
	 * 从test2数据源中获取用户信息
	 */
	@TargetDataSource("test2")
	UserInfo selectByEvenUserId(Integer id);
}
```
完成以上6个步骤，还不行，因为使用了spring-boot会自Autoconfiguration，所以我们需要在启动类注解上作如下修改，不让spring-boot给我们自动配置。

```
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```

好了，我们采用Junit进行代码测试

```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class AppTest {
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Test
	public void testDynamicDatasource() {
		UserInfo userInfo;
		for (int i = 1; i <= 1; i++) {
			//i为奇数时调用selectByOddUserId方法获取，i为偶数时调用selectByEvenUserId方法获取
			userInfo = i % 2 == 1 ? userInfoMapper.selectByOddUserId(i) : userInfoMapper.selectByEvenUserId(i);
			log.info("{}->={}", userInfo.getId(),userInfo.getRemarks());
		}
	}
}
```

运行testDynamicDatasource()方法，按照我的的思路应该分别打印出1->test1和2->test2,如下图运行效果

```
[2017-09-01 21:29:47,558][INFO ] c.c.exmaple.AppTest[29] - 1->=test1
[2017-09-01 21:29:47,598][INFO ] c.c.exmaple.AppTest[29] - 2->=test2
```
可以看到，完成了我们想要的执行结果，说明我们的处理成功了


>总结

利用AbstractRoutingDataSource以及AOP，我们实现了多数据源的切换，可以满足我们想要的大部分情况，而且相对来说逻辑简单，容易理解。


[源码地址](http://git.oschina.net/comven/dynamic-datasource-demo)
