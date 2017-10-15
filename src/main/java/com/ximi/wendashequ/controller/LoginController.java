package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by 陶世磊 on 2017/10/14.
 *
 * @Description:  登录注册
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/reLogin",method = RequestMethod.GET)
    public String reLogin(){
        return "login";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password){

        Map<String,String> map = userService.registerUser(username,password);
        if (map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
        return "redirect:/";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password){

        Map<String,String> map = userService.loginUser(username,password);
        if (map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
        return "redirect:/";
    }
}
