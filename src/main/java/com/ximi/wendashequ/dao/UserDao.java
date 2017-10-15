package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:  通过  注解  的方式整合mybatis
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String TABLE_FIELDS=" name,password,salt,HEAD_URL ";
    String SELECT_FILES = " id,name,password,salt,HEAD_URL ";
    //添加用户
    @Update({"insert into"+TABLE_NAME+"("+TABLE_FIELDS+
            ")values(#{name},#{password},#{salt},#{headUrl}"+")"})
    int addUser(User user);
    //根据id查询用户
    User selectUserById(int id);
    //根据username查询用户
    User selectUserByName(String name);
    //根据用户id修改对应的用户的密码
    @Update({"update" +TABLE_NAME+"set password = #{password} where id = #{id}"})
    void updatePassword(@Param("id") int id,
                        @Param("password") String password);
    //删除用户
    @Delete({"delete from"+TABLE_NAME+"where id = #{id}"})
    void deleteUserById(int id);

}
