package com.ximi.wendashequ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 陶世磊 on 2017/10/10.
 *
 * @Description:
 */
@Controller
public class IndexController {
    @RequestMapping(value="/profile/{aa}/{bb}")
    @ResponseBody
    public String profile(@PathVariable("aa") String aa,
                          @PathVariable("bb") int bb,
                          @RequestParam("cc") int cc){
        return String.format("{%s},{%d},{%d}",aa,bb,cc);

    }
    @RequestMapping(value = "/template")
    public String template(Model model,
                           HttpServletRequest request,
                           HttpServletResponse response){
        model.addAttribute("name","tsl");
        String[] animals = new String[]{"熊猫","大象","牛蛙"};
        model.addAttribute("animals",animals);
        Map<String,Object> hashMap = new HashMap<>();
        return "ttt";
    }

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                           HttpServletResponse response,
                          @CookieValue("JSESSIONID") String Jsessionid){
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE:"+Jsessionid);
        //获取请求头的信息
        Enumeration<String> headernames = request.getHeaderNames();
        while(headernames.hasMoreElements()){
            String name = headernames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if (request.getCookies() != null){
            for (Cookie cookie:request.getCookies()){
                sb.append("cookieName:"+cookie.getName()+"value:"+cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");

        response.addCookie(new Cookie("TSL","521"));
        response.addHeader("我是请求头啊","我是请求头 的内容！");
        return sb.toString();
    }



    //统一的异常处理
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }

    //测试抛出异常
    @RequestMapping(value = "/exception/{admin}")
    @ResponseBody
    public String exception(@PathVariable("admin") String admin){
        if ("admin".equals(admin)){
            return "hello admin";
        }else {
            throw new IllegalArgumentException("参数错误！");
        }
    }

    //aop  处理日志信息


}
