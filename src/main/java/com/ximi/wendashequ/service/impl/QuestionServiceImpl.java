package com.ximi.wendashequ.service.impl;

import com.ximi.wendashequ.dao.QuestionDAO;
import com.ximi.wendashequ.model.Question;
import com.ximi.wendashequ.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDAO questionDAO;
    @Override
    public List<Question> findQuestions(int userId,int offset,int limit) {
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    @Override
    public int addQuestion(Question question) {
        //使用HtmlUtils工具来对输入的内容进行转义
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        return questionDAO.addQuestion(question);
    }
}
