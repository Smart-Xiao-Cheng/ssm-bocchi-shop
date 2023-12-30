package com.xiaocheng.service;

import com.xiaocheng.mapper.OrderMapper;
import com.xiaocheng.pojo.Order;
import com.xiaocheng.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testConvertToOrderDTO() {
    }

    @Test
    public void testFindOrdersWithConditions() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp beginTime = new Timestamp(dateFormat.parse("2023-01-01 00:00:00").getTime());
        Timestamp endTime = new Timestamp(dateFormat.parse("2023-12-31 23:59:59").getTime());
//        Integer userId = 1;
        Integer orderStatus = 1;
        String orderNumber = "1234567890";

        List<Order> orders = orderService.findOrdersWithConditions(null, beginTime, endTime, orderStatus, orderNumber);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

}
