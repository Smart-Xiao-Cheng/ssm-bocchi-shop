package com.xiaocheng.modelmapper.converter;

import com.xiaocheng.dto.OrderDTO;
import com.xiaocheng.mapper.OrderMapper;
import com.xiaocheng.pojo.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderToOrderDTOConverterTest {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testConvert() {
        Order order = orderMapper.getOrderById(2);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        System.out.println(orderDTO);
    }
}
