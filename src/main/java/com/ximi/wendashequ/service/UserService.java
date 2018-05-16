package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.User;

import java.util.Map;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description:
 */

public interface UserService {
    //查用户
    User findUserById(int id);
    //注册用户
    Map<String,String> registerUser(String username, String password);
    //用户登录
    Map<String,String> loginUser(String username, String password);
    // 查找
    User findUserByName(String name);
    //登出
    void logout(String ticket);
}
