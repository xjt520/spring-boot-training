package com.zmdm.ws;

import javax.jws.WebService;

/**
 * Created by xjt520 on 16/7/7.
 */
@WebService
public interface HelloWorld {
    String sayHi(String text);
}
