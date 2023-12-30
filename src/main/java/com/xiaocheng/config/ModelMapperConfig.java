package com.xiaocheng.config;

import com.xiaocheng.modelmapper.converter.OrderDetailToOrderDetailDTOConverter;
import com.xiaocheng.modelmapper.converter.OrderToOrderDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Autowired
    private OrderToOrderDTOConverter orderToOrderDTOConverter;
    @Autowired
    private OrderDetailToOrderDetailDTOConverter orderDetailToOrderDetailDTOConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(orderToOrderDTOConverter);
        modelMapper.addConverter(orderDetailToOrderDetailDTOConverter);
        return modelMapper;
    }
}