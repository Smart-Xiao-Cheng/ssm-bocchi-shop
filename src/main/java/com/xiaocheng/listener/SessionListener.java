package com.xiaocheng.listener;

import com.xiaocheng.pojo.Product;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        HttpSession session = se.getSession();
        Map<Product, Integer> shoppingCartMap = new HashMap<>();
        // 将购物车放入 Session 中
        session.setAttribute("shoppingCartMap", shoppingCartMap);
        // 将totalAmount属性 放入Session中
        float totalAmount = 0f;
        session.setAttribute("totalAmount", totalAmount);
        // 将 isLogin 属性放入 Session 中
        session.setAttribute("isLogin", false);
//        System.out.println("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        // 当用户关闭会话时，把购物车的信息存到数据库中

//        System.out.println("Session 被销毁");
    }
}
