package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Comment;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/7.
 *
 * @Description:
 */

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> selectComments(int entityType,int entityId);
    int getCommentCount(int entityType,int entityId);
    boolean deleteComment(int id,int status);
}
