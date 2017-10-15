package com.ximi.wendashequ.interceptor;

import com.ximi.wendashequ.dao.TicketDAO;
import com.ximi.wendashequ.dao.UserDao;
import com.ximi.wendashequ.model.HostHolder;
import com.ximi.wendashequ.model.Ticket;
import com.ximi.wendashequ.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 陶世磊 on 2017/10/15.
 *
 * @Description:  通行证
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    UserDao userDao;
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            Ticket loginTicket = ticketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpireTime().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }

            User user = userDao.selectUserById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
