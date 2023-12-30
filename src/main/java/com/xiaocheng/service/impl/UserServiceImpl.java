package com.xiaocheng.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaocheng.mapper.UserMapper;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public void updateUserByName(User user) {
        userMapper.updateUserByName(user);
    }

    @Override
    public void deleteUserByName(String name) {
        userMapper.deleteUserByName(name);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int getUserIdByName(String name) {
        return userMapper.getUserIdByName(name);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int isExistUser(String name) {
        return userMapper.isExistUser(name);
    }

    @Override
    public int checkUser(String name, String password) {
        return userMapper.checkUser(name, password);
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public List<User> getUsersByPage(int page, int limit, String sort, String sortOrder, String keywords) {
        // 使用PageHelper设置分页参数
        PageHelper.startPage(page, limit);
        // 调用Mapper方法进行查询
        List<User> users = userMapper.selectUsersByPage(sort, sortOrder, keywords);
        return users;
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public List<User> getUsersByKeyword(String username) {
        return userMapper.getUsersByKeyword(username);
    }
}
