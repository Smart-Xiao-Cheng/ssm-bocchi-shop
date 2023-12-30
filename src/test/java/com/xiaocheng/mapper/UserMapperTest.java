package com.xiaocheng.mapper;

import com.xiaocheng.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetAllUsers() {
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        userMapper.addUser(user);
    }
}
