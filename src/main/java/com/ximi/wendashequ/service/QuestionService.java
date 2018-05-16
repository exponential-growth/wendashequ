package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Question;

import java.util.List;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description:
 */

public interface QuestionService {
    // 查找问题
    List<Question> findQuestions(int userId,int offset,int limit);
    // 添加问题
    int addQuestion(Question question);
    // 查找问题
    Question findQuestionById(int id);
    // 添加评论数
    int addCommentCount(int id,int commentCount);
}
