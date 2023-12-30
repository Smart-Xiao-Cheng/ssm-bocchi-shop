package com.xiaocheng.service.impl;

import com.xiaocheng.dto.OrderDetailDTO;
import com.xiaocheng.mapper.OrderDetailMapper;
import com.xiaocheng.mapper.OrderMapper;
import com.xiaocheng.pojo.OrderDetail;
import com.xiaocheng.service.OrderDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailMapper.getAllOrderDetails();
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        return orderDetailMapper.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public int updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.updateOrderDetail(orderDetail);
    }

    @Override
    public int deleteOrderDetail(int orderDetailId) {
        return orderDetailMapper.deleteOrderDetail(orderDetailId);
    }

    @Override
    public int addOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.addOrderDetail(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(int orderDetailId) {
        return orderDetailMapper.getOrderDetailById(orderDetailId);
    }

    @Override
    public OrderDetailDTO convertToOrderDetailDTO(OrderDetail orderDetail){
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }
}
