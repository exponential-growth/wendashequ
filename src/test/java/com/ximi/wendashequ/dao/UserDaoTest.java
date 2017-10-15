package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.WendashequApplication;
import com.ximi.wendashequ.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendashequApplication.class)
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Test
    public void addUser() throws Exception {
        Random random = new Random();
        for (int i = 0;i<5;++i){
            User user = new User();
            user.setName(String.format("用户%d",i));
            user.setHeadUrl(String.format("http://%d.png",random.nextInt(100)));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);
        }
    }

    @Test
    public void selectUserById() throws Exception {
        User user = userDao.selectUserById(2);
        Assert.assertNotNull(user);

    }

    @Test
    public void updatePassword() throws Exception {
        userDao.updatePassword(1,"XXX");

    }

    @Test
    public void deleteUserById() throws Exception {
        userDao.deleteUserById(1);
    }

}