package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified, avatar_url) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findById(@Param("accountId") Integer creator);

    @Update("update user set token = #{token}, gmt_modified = #{gmtModified} where account_id = #{accountId}")
    void update(User dbUser);
}
