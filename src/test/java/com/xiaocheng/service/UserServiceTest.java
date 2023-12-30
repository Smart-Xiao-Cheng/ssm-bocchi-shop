package com.xiaocheng.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaocheng.mapper.UserMapper;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUsersByPage() {

    }
}
