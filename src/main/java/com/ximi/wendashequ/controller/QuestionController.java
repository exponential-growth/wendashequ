package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.model.Question;
import com.ximi.wendashequ.service.QuestionService;
import com.ximi.wendashequ.service.UserService;
import com.ximi.wendashequ.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by 陶世磊 on 2017/10/16.
 *
 * @Description:
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        Question question = new Question();
        try{
            question.setTitle(title);
            question.setContent(content);
            question.setCreateDate(new Date());
            question.setCommentCount(0);
            if (hostHolder.getUser() == null){
                //强制跳转  返回状态码  999
                return WendaUtil.getJSONObject(999);
            }
            //设置id
            question.setUserId(hostHolder.getUser().getId());
            int count = questionService.addQuestion(question);
            if (count > 0){
                return WendaUtil.getJSONObject(0);
            }
        }catch (Exception e){
            log.error("发布问题失败"+e.getMessage());
        }

        return WendaUtil.getJSONObject(1,"失败");
    }
    @RequestMapping(value = "/{id}")
    public String showQuestion(Model model, @PathVariable("id") int id){
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.findUserById(question.getUserId()));

        return "detail";
    }
}
