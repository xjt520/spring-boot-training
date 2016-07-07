package com.zmdm.ws;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * Created by xjt520 on 16/7/7.
 */
public class Clinet {
    public static void main(String args[]) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://localhost:8080/ws-server-1.0/api/hello");
        factory.setServiceClass(HelloWorld.class);

        HelloWorld client = (HelloWorld) factory.create();
        String response = client.sayHi("hi, you beautiful world!");
        System.out.println("Response from server: " + response);
    }
}
