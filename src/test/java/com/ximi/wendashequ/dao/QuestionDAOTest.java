package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.WendashequApplication;
import com.ximi.wendashequ.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description: 单元测试  测试问题表
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendashequApplication.class)
public class QuestionDAOTest {
    @Autowired
    QuestionDAO questionDao;
    @Test
    public void addQuestion(){
        for (int i = 0;i < 5;++i){
            Question question = new Question();
            question.setTitle(String.format("标题%d",i));
            question.setContent(String.format("内容%d",i));
            question.setUserId(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreateDate(date);
            questionDao.addQuestion(question);
        }
    }
    @Test
    public void selectLatestQuestions() throws Exception {
        List<Question> questionList = questionDao.selectLatestQuestions(1,0,4);
    }

}