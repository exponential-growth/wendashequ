package com.ximi.wendashequ.service;

import com.ximi.wendashequ.model.User;

import java.util.Map;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:
 */

public interface UserService {
    User findUserById(int id);
    Map<String,String> registerUser(String username, String password);
    Map<String,String> loginUser(String username, String password);
}
