package com.zmdm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by xjt520 on 16/6/19.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model){
        model.addAttribute("title", "Home Page");
        model.addAttribute("body", "hello velocity !");
        return "home";
    }


}
