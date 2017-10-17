package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.dao.QuestionDAO;
import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.model.Question;
import com.ximi.wendashequ.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private QuestionDAO questionDAO;
    @Autowired
    private HostHolder hostHolder;
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
            int count = questionDAO.addQuestion(question);
            if (count > 0){
                return WendaUtil.getJSONObject(0);
            }
        }catch (Exception e){
            log.error("发布问题失败"+e.getMessage());
        }

        return WendaUtil.getJSONObject(1,"失败");
    }
}
