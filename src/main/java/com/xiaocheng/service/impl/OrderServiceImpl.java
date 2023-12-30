package com.xiaocheng.service.impl;

import com.xiaocheng.dto.OrderDTO;
import com.xiaocheng.mapper.OrderMapper;
import com.xiaocheng.pojo.Order;
import com.xiaocheng.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderMapper.getOrdersByUserId(userId);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrder(int orderId) {
        return orderMapper.deleteOrder(orderId);
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public OrderDTO convertToOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public int getOrderCount() {
        return orderMapper.getOrderCount();
    }

    @Override
    public float getTotalAmount() {
        return orderMapper.getTotalAmount();
    }

    @Override
    public List<Map<String, Object>> getRecentSevenDaysOrders() {
        return orderMapper.getRecentSevenDaysOrders();
    }

    @Override
    public List<Map<String, Object>> getRecentSevenDaysTotalAmount() {
        return orderMapper.getRecentSevenDaysTotalAmount();
    }

    @Override
    public List<Order> getRecentOrders() {
        return orderMapper.getRecentOrders();
    }

    @Override
    public List<Order> findOrdersWithConditions(Integer userId, Timestamp beginTime, Timestamp endTime, Integer orderStatus, String orderNumber) {
        return orderMapper.findOrdersWithConditions(userId, beginTime, endTime, orderStatus, orderNumber);
    }
}
