package com.xiaocheng.controller;

import com.xiaocheng.dto.UserDTO;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    // 用户登录验证
    @PostMapping("/verify")
    public String login(UserDTO userParam, HttpServletRequest request) {
        String username = userParam.getUsername();
        String password = userParam.getPassword1();
        if (userService.checkUser(username, password) > 0) {
            User user = userService.getUserByName(username);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("isLogin", true);
            return "redirect:/";
        } else {
            request.getSession().setAttribute("loginFail", "true");
            return "redirect:/login";
        }
    }

    @GetMapping("/clearLoginFail")
    public String clearLoginFail(HttpServletRequest request) {
        request.getSession().setAttribute("loginFail", "false");
        return "redirect:/login";
    }
}
