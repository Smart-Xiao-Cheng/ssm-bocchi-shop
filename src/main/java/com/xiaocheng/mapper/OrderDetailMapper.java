package com.xiaocheng.mapper;

import com.xiaocheng.pojo.OrderDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderDetailMapper {
    // 获取所有订单详情
    @Select("select * from orderDetails")
    @Results({
            @Result(property = "orderDetailId", column = "orderDetailID"),
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "productId", column = "productID"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "unitPrice", column = "unitPrice")
    })
    List<OrderDetail> getAllOrderDetails();

    // 根据订单ID获取订单详情
    @Select("select * from orderDetails where orderID=#{orderId}")
    @Results({
            @Result(property = "orderDetailId", column = "orderDetailID"),
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "productId", column = "productID"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "unitPrice", column = "unitPrice")
    })
    List<OrderDetail> getOrderDetailsByOrderId(@Param("orderId") int orderId);

    // 更新订单详情信息
    @Update("update orderDetails set orderID=#{orderDetail.orderId},productID=#{orderDetail.productId},quantity=#{orderDetail.quantity},unitPrice=#{orderDetail.unitPrice} where orderDetailID=#{orderDetail.orderDetailId}")
    int updateOrderDetail(@Param("orderDetail") OrderDetail orderDetail);

    // 删除订单详情
    @Delete("delete from orderDetails where orderDetailID=#{orderDetailId}")
    int deleteOrderDetail(@Param("orderDetailId") int orderDetailId);

    // 添加订单详情
    @Insert("insert into orderDetails(orderID,productID,quantity,unitPrice) values(#{orderDetail.orderId},#{orderDetail.productId},#{orderDetail.quantity},#{orderDetail.unitPrice})")
    int addOrderDetail(@Param("orderDetail") OrderDetail orderDetail);

    // 根据订单详情ID获取订单详情
    @Select("select * from orderDetails where orderDetailID=#{orderDetailId}")
    @Results({
            @Result(property = "orderDetailId", column = "orderDetailID"),
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "productId", column = "productID"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "unitPrice", column = "unitPrice")
    })
    OrderDetail getOrderDetailById(@Param("orderDetailId") int orderDetailId);
}