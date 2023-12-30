package com.xiaocheng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaocheng.dto.AdminDTO;
import com.xiaocheng.pojo.Order;
import com.xiaocheng.pojo.Product;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.impl.AdminServiceImpl;
import com.xiaocheng.service.impl.OrderServiceImpl;
import com.xiaocheng.service.impl.ProductServiceImpl;
import com.xiaocheng.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private AdminServiceImpl adminService;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        // 用于当用户第一次访问首页时，创建 Session
        request.getSession();
        return "/shop/index";
    }
    @RequestMapping("/login")
    public String login() {
        return "/shop/login";
    }

    @RequestMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error, Model model) {
        return "/shop/register";
    }

    @RequestMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "/shop/productList";
    }

    @RequestMapping("/order")
    public String order(HttpServletRequest request) {
        // 判断用户是否登录，如果未登录则跳转到登录页面
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin) {
            User user = (User) session.getAttribute("user");
            List<Order> orders = orderService.getOrdersByUserId(user.getUserId());
            session.setAttribute("orders", orders);
            return "/shop/myOrder";
        } else {
            return "/shop/login";
        }
    }

    @RequestMapping("/cart")
    public String cart() {
        return "/shop/cart";
    }

    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) throws Exception{
        Cookie[] cookies = request.getCookies();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();
        Boolean adminIsLogin = (Boolean) session.getAttribute("adminIsLogin");
        if (adminIsLogin != null && adminIsLogin) {
            return "/admin/index";
        }
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("admin")) {
                    String decodedAdminJson = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
                    AdminDTO adminDTO = mapper.readValue(decodedAdminJson, AdminDTO.class);
                    if (adminService.checkAdmin(adminDTO.getName(), adminDTO.getPassword()) > 0) {
                        return "/admin/index";
                    }
                }
            }
        }
        return "redirect:/admin/login";
    }

    @RequestMapping("/admin/login")
    public String adminLogin(HttpServletRequest request) throws Exception{
        Cookie[] cookies = request.getCookies();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();
        Boolean adminIsLogin = (Boolean) session.getAttribute("adminIsLogin");
        if (adminIsLogin != null && adminIsLogin) {
            return "redirect:/admin";
        }
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("admin")) {
                    String decodedAdminJson = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
                    AdminDTO adminDTO = mapper.readValue(decodedAdminJson, AdminDTO.class);
                    if (adminService.checkAdmin(adminDTO.getName(), adminDTO.getPassword()) > 0) {
                        return "redirect:/admin";
                    }
                }
            }
        }
        return "/admin/login";
    }

    @RequestMapping("/admin/test")
    public String test() {
        return "/admin/test";
    }
}
