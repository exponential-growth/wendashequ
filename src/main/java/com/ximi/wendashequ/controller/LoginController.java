package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 单广美 on 2017/10/14.
 *
 * @Description:  登录注册
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/reLogin",method = RequestMethod.GET)
    public String reLogin(Model model,
                          @RequestParam(value = "next",required = false) String next){
        model.addAttribute("next", next);
        return "login";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("remember") boolean remember,
                           HttpServletResponse response,
                           @RequestParam(value = "next",required = false) String next){

        Map<String,String> map = userService.registerUser(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            if (remember) {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            if (StringUtils.isNotBlank(next)) {
                return "redirect:" + next;
            }
            return "redirect:/";
        } else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password,
                        @RequestParam("remember") boolean remember,
                        HttpServletResponse response,
                        @RequestParam(value="next", required = false) String next){
        Map<String,String> map = userService.loginUser(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            if (remember) {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            if (StringUtils.isNotBlank(next)) {
                return "redirect:" + next;
            }
            return "redirect:/";
        } else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
