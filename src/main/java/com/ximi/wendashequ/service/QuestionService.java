package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Question;

import java.util.List;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description:
 */

public interface QuestionService {
    List<Question> findQuestions(int userId,int offset,int limit);
    int addQuestion(Question question);
    Question findQuestionById(int id);

    int addCommentCount(int id,int commentCount);
}
