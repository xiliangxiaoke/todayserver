package cn.com.todayfarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.net.httpserver.Authenticator.Success;

@Controller
public class HelloWorldHandler {

	@RequestMapping(value="/hello")
	public String hello() {
		System.out.println("enter helloworld");
		return "success";
	}
	
}
