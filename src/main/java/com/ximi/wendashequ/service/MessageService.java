package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Message;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/8.
 *
 * @Description:
 */

public interface MessageService {

    // 新增私信
    int addMessage(Message message);
    // 获取对话信息
    List<Message> getMessages(String conversationId);
    // 获取消息列表
    List<Message> getMessageList(int userId);
    // 获取未读
    int getHasReadCount(String conversationId);
}
