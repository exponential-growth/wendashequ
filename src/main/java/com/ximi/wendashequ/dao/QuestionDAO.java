package com.ximi.wendashequ.dao;

import com.ximi.wendashequ.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 陶世磊 on 2017/10/11.
 *
 * @Description:
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    //添加问题啊
    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FIELDS+
            ")values (#{title},#{content},#{createDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);
    //查询
    //@Select({"select"+SELECT_FIELDS+"from"+TABLE_NAME+"where user_id = #{userId} limit #{offset},#{limit}"})
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
}
