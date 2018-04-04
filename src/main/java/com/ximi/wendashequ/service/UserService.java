package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.User;

import java.util.Map;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description:
 */

public interface UserService {
    User findUserById(int id);
    Map<String,String> registerUser(String username, String password);
    Map<String,String> loginUser(String username, String password);
    User findUserByName(String name);
    void logout(String ticket);
}
