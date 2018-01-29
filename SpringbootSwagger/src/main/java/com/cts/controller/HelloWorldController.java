package com.cts.controller;

import java.net.Authenticator.RequestorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.User;
import com.cts.service.HelloService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/")
public class HelloWorldController {
	@Autowired
	HelloService helloService;
	@ApiOperation(value="hello，测试第二个接口", notes="根据参数显示hello")
	/**
	 * 参考网址：https://my.oschina.net/dlam/blog/808315
	name：参数名
	paramType：参数类型
	 	header：请求参数放置于Request Header，使用@RequestHeader获取
		query：请求参数放置于请求地址，使用@RequestParam获取
		path：（用于restful接口）-->请求参数的获取：@PathVariable
		body：（不常用）
		form（不常用）
	required：参数是否必须传
		true | false
	dataType：参数类型
	defaultValue：参数的默认值
	 */
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="用户名",dataType="String", paramType = "query",required=true,defaultValue="cts"),
            @ApiImplicitParam(name="date",value="日期",dataType="Date", paramType = "query")
    })
	@RequestMapping(value="/hello",method=RequestMethod.POST)
	public String hello(String name,String date){
		return helloService.hello(name,date);
	}
	@ApiOperation(value="hi，测试第二个接口")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	@RequestMapping(value="hi",method = RequestMethod.POST)
	public String helloWorld(@RequestBody User user){
		System.out.println(user.toString());
		return "hi,"+user.getName();
	}
}

	