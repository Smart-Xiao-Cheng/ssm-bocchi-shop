package com.xiaocheng.modelmapper.converter;

import com.xiaocheng.dto.OrderDTO;
import com.xiaocheng.mapper.UserMapper;
import com.xiaocheng.pojo.Order;
import com.xiaocheng.pojo.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 自定义转换器，将Order转换为OrderDTO
@Component
public class OrderToOrderDTOConverter implements Converter<Order, OrderDTO> {

    @Autowired
    private UserMapper userMapper;
    // 将Order转换为OrderDTO
    @Override
    public OrderDTO convert(MappingContext<Order, OrderDTO> context) {
        Order source = context.getSource();
        OrderDTO destination = new OrderDTO();

        destination.setOrderId(source.getOrderId());
        destination.setOrderDate(source.getOrderDate());
        destination.setTotalAmount(source.getTotalAmount());
        destination.setOrderStatus(source.getOrderStatus());
        destination.setOrderNumber(source.getOrderNumber());

        User user = userMapper.getUserById(source.getUserId());
        destination.setUsername(user.getUserName());

        return destination;
    }
}