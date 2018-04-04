package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 单广美 on 2018/3/8.
 *
 * @Description:
 */
@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id,content,conversation_id, created_date";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    @Insert({"INSERT  INTO ",TABLE_NAME,"(",INSERT_FIELDS,")" +
            "VALUES (#{fromId},#{toId},#{content},#{conversationId},#{createdDate})"})
    int addMessage(Message message);
    @Select({"SELECT ",SELECT_FIELDS,"from",TABLE_NAME," WHERE conversation_id = #{conversationId}"})
    List<Message> selectMessages(@Param("conversationId") String conversationId);

    @Select({"SELECT ",INSERT_FIELDS,",COUNT(id) as id  from (SELECT * from ",TABLE_NAME," ORDER BY created_date desc) tt " +
            "WHERE from_id = #{userId} or to_id = #{userId} GROUP BY conversation_id"})
    List<Message> selectMessageList(@Param("userId") int userId);

    @Select({"SELECT COUNT(id)  from",TABLE_NAME,"WHERE has_read = 0 and conversation_id=#{conversationId}" })
    int selectMessageNotReadCount(@Param("conversationId") String conversationId);
}
