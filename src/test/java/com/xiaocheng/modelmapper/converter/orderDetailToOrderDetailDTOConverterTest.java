package com.xiaocheng.modelmapper.converter;

import com.xiaocheng.dto.OrderDetailDTO;
import com.xiaocheng.mapper.OrderDetailMapper;
import com.xiaocheng.mapper.OrderMapper;
import com.xiaocheng.pojo.OrderDetail;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class orderDetailToOrderDetailDTOConverterTest {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Test
    public void testConvert() {
        OrderDetail orderDetail = orderDetailMapper.getOrderDetailById(2);
        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
        System.out.println(orderDetailDTO);
    }
}
