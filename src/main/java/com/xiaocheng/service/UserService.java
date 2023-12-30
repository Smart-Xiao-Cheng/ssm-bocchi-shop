package com.xiaocheng.service;

import com.xiaocheng.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserByName(String name);
    public void updateUserByName(User user);
    public void deleteUserByName(String name);
    public int addUser(User user);
    public int getUserIdByName(String name);
    public User getUserById(int id);
    public int isExistUser(String name);
    public int checkUser(String name, String password);
    public int getUserCount();
    List<User> getUsersByPage(int limit, int offset, String sort, String sortOrder, String keywords);
    int updateUserById(User user);
    int deleteUserById(int id);
    List<User> getUsersByKeyword(String username);
}
