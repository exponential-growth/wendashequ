package com.ximi.wendashequ.model;

import org.springframework.stereotype.Component;

/**
 * Created by 陶世磊 on 2017/10/15.
 *
 * @Description:  保存登录用户
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
