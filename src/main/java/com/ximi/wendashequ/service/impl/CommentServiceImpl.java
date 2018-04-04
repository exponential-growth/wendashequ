package com.ximi.wendashequ.service.impl;

import com.ximi.wendashequ.dao.CommentDao;
import com.ximi.wendashequ.model.Comment;
import com.ximi.wendashequ.service.CommentService;
import com.ximi.wendashequ.util.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/7.
 *
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    SensitiveService sensitiveService;
    @Override
    public void addComment(Comment comment) {

        //过滤敏感内容
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDao.addComment(comment);
    }

    @Override
    public List<Comment> selectComments(int entityType, int entityId) {
        return commentDao.selectComments(entityType,entityId);
    }

    @Override
    public int getCommentCount(int entityType, int entityId) {
        return commentDao.getCommentCount(entityType,entityId);
    }

    @Override
    public boolean deleteComment(int id, int status) {
        return  commentDao.updateStatus(id,status) > 0;
    }
}
