package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){
        User dbUser = userMapper.findById(Integer.valueOf(user.getAccountId()));
        if(dbUser == null){
            // 插入
            userMapper.insert(user);
        }else{
            // 更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
