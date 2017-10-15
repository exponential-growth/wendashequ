package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Question;

import java.util.List;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:
 */

public interface QuestionService {
    List<Question> findQuestions(int userId,int offset,int limit);
}
