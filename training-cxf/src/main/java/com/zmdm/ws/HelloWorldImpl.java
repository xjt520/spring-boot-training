package com.zmdm.ws;


import javax.jws.WebService;

/**
 * Created by xjt520 on 16/7/7.
 */
@WebService(endpointInterface = "com.zmdm.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHi(String text) {
        System.out.println("=============HelloWorldImpl==============="+text);
        return "Hello:"+text;
    }
}
