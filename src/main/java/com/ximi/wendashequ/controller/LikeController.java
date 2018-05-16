package com.ximi.wendashequ.controller;

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
    //注入 like服务层
    @Autowired
    LikeService likeService;
    //注入 用户全局对象
    @Autowired
    HostHolder hostHolder;
    //注入 评论服务层
    @Autowired
    CommentService commentService;
    //点赞请求
    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        //如果用户已经登录
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONObject(999);
        }
        //获取点赞数
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        //返回数据
        return WendaUtil.getJSONObject(0,String.valueOf(likeCount));
    }
    // 踩的请求
    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        //如果用户已经登录
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONObject(999);
        }
        //Comment comment = commentService.getCommentById(commentId);
        //获取不喜欢的数
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        //返回数据
        return WendaUtil.getJSONObject(0,String.valueOf(likeCount));
    }

}
