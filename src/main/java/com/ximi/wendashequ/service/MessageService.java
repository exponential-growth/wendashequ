package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.Message;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/8.
 *
 * @Description:
 */

public interface MessageService {

    int addMessage(Message message);

    List<Message> getMessages(String conversationId);

    List<Message> getMessageList(int userId);

    int getHasReadCount(String conversationId);
}
