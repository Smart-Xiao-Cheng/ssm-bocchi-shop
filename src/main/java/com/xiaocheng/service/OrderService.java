package com.xiaocheng.service;

import com.xiaocheng.dto.OrderDTO;
import com.xiaocheng.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(int userId);
    int updateOrder(Order order);
    int deleteOrder(int orderId);
    int addOrder(Order order);
    Order getOrderById(int orderId);
    OrderDTO convertToOrderDTO(Order order);
    int getOrderCount();
    float getTotalAmount();
    List<Map<String, Object>> getRecentSevenDaysOrders();
    List<Map<String, Object>> getRecentSevenDaysTotalAmount();
    List<Order> getRecentOrders();
    List<Order> findOrdersWithConditions(Integer userId, Timestamp beginTime, Timestamp endTime, Integer orderStatus, String orderNumber);
}
