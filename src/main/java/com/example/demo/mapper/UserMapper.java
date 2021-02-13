package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findById(@Param("token") String token);
}
