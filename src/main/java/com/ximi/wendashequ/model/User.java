package com.ximi.wendashequ.model;

import lombok.Data;

/**
 * Created by 单广美 on 2017/10/10.
 *
 * @Description:
 */
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
}
