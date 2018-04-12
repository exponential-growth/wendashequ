package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Question;

import java.util.List;

/**
 * Created by 单广美 on 2018/4/12.
 *
 * @Description:
 */

public interface SearchService {
    List<Question> searchQuestion(String keyword, int offset, int count,
                                         String hlPre, String hlPos) throws Exception;
    boolean indexQuestion(int qid, String title, String content) throws Exception;
    }
