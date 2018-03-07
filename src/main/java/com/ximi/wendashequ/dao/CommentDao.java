package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 陶世磊 on 2018/3/7.
 *
 * @Description:
 */
@Mapper
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, entity_id, entity_type, created_time,status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    //添加评论
    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FIELDS+
            ")values (#{userId},#{content},#{entityId},#{entityType},#{createdTime},#{status})"})
    int addComment(Comment comment);

    //查询评论
    @Select({"SELECT ",SELECT_FIELDS," from ",TABLE_NAME," WHERE " +
            "entity_type=#{entityType} and entity_id=#{entityId} order by id desc"})
    List<Comment> selectComments(@Param("entityType") int entityType,
                                         @Param("entityId") int entityId);
    //获取评论数量
    @Select({"SELECT  COUNT(id) from ",TABLE_NAME," WHERE entity_type=#{entityType} and entity_id=#{entityId} "})
    int getCommentCount(@Param("entityType") int entityType,
                        @Param("entityId") int entityId);
    //删除评论
    @Select({"UPDATE ",TABLE_NAME," SET status = #{status} WHERE  id = #{id}"})
    int updateStatus(@Param("id") int id,
                     @Param("status") int status);
}
