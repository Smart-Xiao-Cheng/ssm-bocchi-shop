package com.xiaocheng.controller;

import com.xiaocheng.pojo.Product;
import com.xiaocheng.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    // 将商品加入购物车
    @PostMapping("/addToCart")
    @ResponseBody
    public Map<String, Object> addToCart (@RequestParam int productId, HttpServletRequest request) {
        // 从服务层获取商品对象
        Product product = productService.getProductById(productId);

        // 从会话中获取购物车
        Map<Product, Integer> shoppingCartMap = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCartMap");

        // 如果会话中还没有购物车，创建一个新的购物车
        if (shoppingCartMap == null) {
            shoppingCartMap = new HashMap<>();
        }

        // 检查购物车中是否已经有这个商品
        if (shoppingCartMap.containsKey(product)) {
            // 如果有，获取当前的数量并加一
            int currentQuantity = shoppingCartMap.get(product);
            shoppingCartMap.put(product, currentQuantity + 1);
        } else {
            // 如果没有，将商品添加到购物车中，并设置数量为1
            shoppingCartMap.put(product, 1);
        }

        // 更新 totalAmount
        // 计算购物车中所有商品的总金额
        float totalAmount = 0f;
        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }

        // 将更新后的购物车和总金额保存到会话中
        request.getSession().setAttribute("ShoppingCartMap", shoppingCartMap);
        request.getSession().setAttribute("totalAmount", totalAmount);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        // 重定向到原来的页面
        return response;
    }

    // 删除购物车商品信息
    @RequestMapping("/deleteCartItem")
    public String deleteCartItem (@RequestParam int deleteProductId, HttpServletRequest request) {
        // 从服务层获取商品对象
        Product product = productService.getProductById(deleteProductId);

        // 从会话中获取购物车
        Map<Product, Integer> shoppingCartMap = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCartMap");

        // 从购物车中删除这个商品
        shoppingCartMap.remove(product);

        // 更新 totalAmount
        // 计算购物车中所有商品的总金额
        float totalAmount = 0f;
        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }

        // 将更新后的购物车和总金额保存到会话中
        request.getSession().setAttribute("ShoppingCartMap", shoppingCartMap);
        request.getSession().setAttribute("totalAmount", totalAmount);

        // 重定向到原来的页面
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping("/clearShoppingCart")
    public String clearShoppingCart (HttpServletRequest request) {
        // 清空购物车的商品和更新总金额
        request.getSession().setAttribute("shoppingCartMap", new HashMap<Product, Integer>());
        request.getSession().setAttribute("totalAmount", 0f);

        // 重定向到原来的页面
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping("/updateCartItem")
    public String updateCartItem (@RequestParam int updateProductId, @RequestParam int quantity, HttpServletRequest request) {
        // 从服务层获取商品对象
        Product product = productService.getProductById(updateProductId);

        // 从会话中获取购物车
        Map<Product, Integer> shoppingCartMap = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCartMap");

        // 更新购物车中这个商品的数量
        shoppingCartMap.put(product, quantity);

        // 更新 totalAmount
        // 计算购物车中所有商品的总金额
        float totalAmount = 0f;
        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }

        // 将更新后的购物车和总金额保存到会话中
        request.getSession().setAttribute("ShoppingCartMap", shoppingCartMap);
        request.getSession().setAttribute("totalAmount", totalAmount);

        // 重定向到原来的页面
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping("/checkout")
    @ResponseBody
    public Map<String, Object> checkout (HttpServletRequest request) {
        // TODO


        Map<String, Object> response = new HashMap<>();
        return null;
    }
}
