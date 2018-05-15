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
 * @Description:
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 20));
        return "index";
    }

    @RequestMapping(value = {"/user/{userId}"}, method = RequestMethod.GET)
    public String user(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 5));
        return "index";
    }

    public List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.findQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.findUserById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}

