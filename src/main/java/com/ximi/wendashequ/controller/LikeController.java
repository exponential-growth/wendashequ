package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.Comment;
import com.ximi.wendashequ.model.EntityType;
import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.service.CommentService;
import com.ximi.wendashequ.service.LikeService;
import com.ximi.wendashequ.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    //点赞
    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONObject(999);
        }
        //Comment comment = commentService.getCommentById(commentId);

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONObject(0,String.valueOf(likeCount));
    }
    // 踩
    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONObject(999);
        }
        //Comment comment = commentService.getCommentById(commentId);

        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        //返回
        return WendaUtil.getJSONObject(0,String.valueOf(likeCount));
    }

}
