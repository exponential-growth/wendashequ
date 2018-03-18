package com.ximi.wendashequ.service.impl;

import com.ximi.wendashequ.dao.MessageDao;
import com.ximi.wendashequ.model.Message;
import com.ximi.wendashequ.service.MessageService;
import com.ximi.wendashequ.util.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陶世磊 on 2018/3/8.
 *
 * @Description:
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SensitiveService sensitiveService;

    @Override
    public int addMessage(Message message) {
        //过滤敏感词
        message.setContent(sensitiveService.filter(message.getContent()));

        return messageDao.addMessage(message);
    }

    @Override
    public List<Message> getMessages(String conversationId) {
        return messageDao.selectMessages(conversationId);
    }

    @Override
    public List<Message> getMessageList(int userId) {
        return messageDao.selectMessageList(userId);
    }

    @Override
    public int getHasReadCount(String conversationId) {
        return messageDao.selectMessageNotReadCount(conversationId);
    }
}
