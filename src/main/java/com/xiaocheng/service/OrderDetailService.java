package com.xiaocheng.service;

import com.xiaocheng.dto.OrderDetailDTO;
import com.xiaocheng.pojo.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
    int updateOrderDetail(OrderDetail orderDetail);
    int deleteOrderDetail(int orderDetailId);
    int addOrderDetail(OrderDetail orderDetail);
    OrderDetail getOrderDetailById(int orderDetailId);
    OrderDetailDTO convertToOrderDetailDTO(OrderDetail orderDetail);
}
