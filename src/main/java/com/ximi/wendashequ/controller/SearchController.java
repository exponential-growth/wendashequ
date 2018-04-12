package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.Question;
import com.ximi.wendashequ.model.ViewObject;
import com.ximi.wendashequ.service.QuestionService;
import com.ximi.wendashequ.service.SearchService;
import com.ximi.wendashequ.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 单广美 on 2018/04/12.
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;


    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count,
                    "<em style='color: #00BC9B'>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            int resultCount = 0;
            for (Question question : questionList) {
                Question q = questionService.findQuestionById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("user", userService.findUserById(q.getUserId()));
                vos.add(vo);
                resultCount++;
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
            model.addAttribute("resultCount",resultCount);
        } catch (Exception e) {
            logger.error("搜索评论失败" + e.getMessage());
        }
        return "result";
    }
}
