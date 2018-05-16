package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.Question;
import com.ximi.wendashequ.model.ViewObject;
import com.ximi.wendashequ.service.QuestionService;
import com.ximi.wendashequ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description: 拦截首页的请求
 */

@Controller
public class HomeController {
    //注入 用户service层
    @Autowired
    private UserService userService;
    // 注入 问题service层
    @Autowired
    private QuestionService questionService;
    //拦截主页的请求
    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
        //获取主页的信息
        model.addAttribute("vos", getQuestions(0, 0, 20));
        return "index";
    }
    // 拦截查看用户主页的请求
    @RequestMapping(value = {"/user/{userId}"}, method = RequestMethod.GET)
    public String user(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 5));
        return "index";
    }
    //获取问题
    public List<ViewObject> getQuestions(int userId, int offset, int limit) {
        //根据用户的id查找问题
        List<Question> questionList = questionService.findQuestions(userId, offset, limit);
        //遍历问题，加入自定义的集合中
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.findUserById(question.getUserId()));
            vos.add(vo);
        }
        //返回数据
        return vos;
    }
}

