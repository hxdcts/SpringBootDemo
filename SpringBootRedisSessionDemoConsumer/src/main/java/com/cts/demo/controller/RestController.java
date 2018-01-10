package com.cts.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@RequestMapping(value="demo",method = RequestMethod.GET)
	public String excue(){

	        return "get value from zookeeper ";
	}
	@RequestMapping("/uid")
    String uid(HttpSession session) {
		if(session.getAttribute("uid") == null){
			return "空";
		}
        return session.getAttribute("uid").toString()+"==="+session.getAttribute("cts");
    }
}

	