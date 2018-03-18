package com.ximi.wendashequ.service.impl;

import com.ximi.wendashequ.dao.TicketDAO;
import com.ximi.wendashequ.dao.UserDao;
import com.ximi.wendashequ.model.Ticket;
import com.ximi.wendashequ.model.User;
import com.ximi.wendashequ.service.UserService;
import com.ximi.wendashequ.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:  用户
 */
@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private TicketDAO ticketDAO;
    @Override
    public User findUserById(int id) {
        return userDao.selectUserById(id);
    }

    @Override
    public Map<String, String> registerUser(String username, String password) {
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空！");
            return map;
        }
        User user = userDao.selectUserByName(username);
        if (user != null){
            map.put("msg","用户名已存在，请更换一个");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));//随机生成uuid并截取五位作为盐值
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));//加密
        user.setHeadUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507957794750&di=788bd2ca4e75d4c8c6e925bbe98a70db&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201502%2F11%2F20150211103803_BaME4.thumb.224_0.jpeg");
        userDao.addUser(user);

        //登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    @Override
    public Map<String, String> loginUser(String username, String password) {
        Map<String,String> map = new HashMap<>();
        User user = userDao.selectUserByName(username);
        if (user == null){
            map.put("msg","用户名或密码错误");
            return map;
        }
        if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    @Override
    public User findUserByName(String name) {
        return userDao.selectUserByName(name);
    }

    private String addLoginTicket(int userId) {
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpireTime(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        ticketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public void logout(String ticket) {
        ticketDAO.updateStatus(ticket, 1);
    }

}
