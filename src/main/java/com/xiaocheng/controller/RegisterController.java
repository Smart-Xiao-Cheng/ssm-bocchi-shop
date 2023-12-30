package com.xiaocheng.controller;

import com.xiaocheng.dto.UserDTO;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;
    // 用户注册
    @RequestMapping("/submit")
    public String register(UserDTO userParam) {
        String username = userParam.getUsername();
        String password1 = userParam.getPassword1();
        String password2 = userParam.getPassword2();
        // 检查是否用户名已经存在
        if (userService.isExistUser(username) > 0) {
            return "redirect:/register?error=username";
        }
        // 检查两次输入的密码是否一致
        if (!password1.equals(password2)) {
            return "redirect:/register?error=password";
        }
        // 设置用户信息
        User user = new User();
        user.setUserName(username);
        user.setPassword(password1);
        // 保存用户信息
        int isSaved = userService.addUser(user);
        if (isSaved > 0) {
            return "redirect:/login";
        } else {
            return "redirect:/register?error=database";
        }
    }
}
