package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.model.Ticket;
import org.apache.ibatis.annotations.*;

/**
 * Created by 陶世磊 on 2017/10/15.
 *
 * @Description:
 */
@Mapper
public interface TicketDAO {
    String TABLE_NAME = " ticket ";
    String INSERT_FIELDS = " user_id, expire_time, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{expireTime},#{status},#{ticket})"})
    int addTicket(Ticket ticket);

    Ticket selectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
