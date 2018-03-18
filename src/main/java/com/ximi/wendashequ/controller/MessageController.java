package com.ximi.wendashequ.controller;

import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.model.Message;
import com.ximi.wendashequ.model.User;
import com.ximi.wendashequ.model.ViewObject;
import com.ximi.wendashequ.service.MessageService;
import com.ximi.wendashequ.service.UserService;
import com.ximi.wendashequ.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 陶世磊 on 2018/3/8.
 *
 * @Description:
 */
@Controller
public class MessageController {

    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @RequestMapping(path = {"/msg/addMessage"},method= {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){

        try {
            if (hostHolder.getUser()==null){
                return WendaUtil.getJSONObject(999,"未登录");
            }
            User user = userService.findUserByName(toName);
            if (user == null) {
                return WendaUtil.getJSONObject(1, "用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            messageService.addMessage(message);
            return WendaUtil.getJSONObject(0);
        }catch (Exception e){
            logger.error("发送消息失败" + e.getMessage());
            return WendaUtil.getJSONObject(2,"发送私信失败");
        }
    }
    @RequestMapping(path = "/msg/list",method = RequestMethod.GET)
    public String showMessage(Model model){
        if (hostHolder.getUser()==null){
            return "redirect:/reLogin";
        }
        List<Message> messageList = messageService.getMessageList(hostHolder.getUser().getId());
        List<ViewObject> viewObject = new ArrayList<>();
        int localUserId = hostHolder.getUser().getId();
        for (Message message:messageList){
            ViewObject o = new ViewObject();
            //未读
            int hasRead = 0;
            o.set("message",message);
            int targetId = message.getFromId() == localUserId?message.getToId():message.getFromId();
            o.set("user",userService.findUserById(targetId));
            hasRead = messageService.getHasReadCount(message.getConversationId());
            o.set("hasRead",hasRead);
            viewObject.add(o);
        }
        model.addAttribute("conversations",viewObject);

        return "letter";
    }

    @RequestMapping(path = "/msg/letterDetail",method = RequestMethod.GET)

    public String messageDetail(@RequestParam("conversationId") String conversationId, Model model){
        List<Message> messageList = messageService.getMessages(conversationId);
        List<ViewObject> details = new ArrayList<>();

        for (Message message:messageList){
            ViewObject o = new ViewObject();
            o.set("message",message);

            o.set("user",userService.findUserById(message.getFromId()));
            details.add(o);
        }
        model.addAttribute("details",details);
        return "letterDetail";
    }

}
