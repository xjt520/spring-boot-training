package com.fast.util;

import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by xjt520 on 16/7/2.
 */
public class WebUtil {

    public static void outJson(ServletResponse response, Map<String, String> resultMap){
        response.reset();
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONObject.fromObject(resultMap).toString());
        } catch (Exception e) {
            System.out.println("输出JSON报错:"+e.getMessage());
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }

    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

}
