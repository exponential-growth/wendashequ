package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.Comment;
import com.ximi.wendashequ.model.EntityType;
import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.service.CommentService;
import com.ximi.wendashequ.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by 陶世磊 on 2018/3/7.
 *
 * @Description: 评论中心
 */
@Controller
public class CommentController {

    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = "/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        try {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setEntityId(questionId);
        if (hostHolder.getUser() != null){
            comment.setUserId(hostHolder.getUser().getId());
        }else {
            //匿名用户
            comment.setUserId(WendaUtil.ANONYMOUS_USERID);
        }
        comment.setCreatedTime(new Date());
        comment.setEntityType(EntityType.ENTITY_QUESTION);

        commentService.addComment(comment);

        }catch (Exception e){
            logger.error("添加评论出现异常："+e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
