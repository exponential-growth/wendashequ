package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Comment;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/7.
 *
 * @Description:
 */

public interface CommentService {
    //新增评论
    void addComment(Comment comment);
    //查找评论
    List<Comment> selectComments(int entityType,int entityId);
    //获取评论数
    int getCommentCount(int entityType,int entityId);
    boolean deleteComment(int id,int status);
    Comment getCommentById(int commentId);
}
