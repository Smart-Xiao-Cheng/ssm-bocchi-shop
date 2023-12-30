package com.xiaocheng.service;

import com.xiaocheng.dto.OrderDetailDTO;
import com.xiaocheng.mapper.OrderDetailMapper;
import com.xiaocheng.pojo.OrderDetail;
import com.xiaocheng.service.impl.OrderDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderDetailServiceTest {
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Test
    public void testConvertToOrderDetailDTO() {
        OrderDetail orderDetail = orderDetailMapper.getOrderDetailById(2);
        System.out.println(orderDetailService.convertToOrderDetailDTO(orderDetail));
    }
}
