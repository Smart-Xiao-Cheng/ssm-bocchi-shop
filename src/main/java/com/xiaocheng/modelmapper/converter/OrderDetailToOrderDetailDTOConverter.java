package com.xiaocheng.modelmapper.converter;

import com.xiaocheng.dto.OrderDetailDTO;
import com.xiaocheng.mapper.ProductMapper;
import com.xiaocheng.pojo.OrderDetail;
import com.xiaocheng.pojo.Product;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailToOrderDetailDTOConverter implements Converter<OrderDetail, OrderDetailDTO> {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderDetailDTO convert(MappingContext<OrderDetail, OrderDetailDTO> context) {
        OrderDetail source = context.getSource();
        OrderDetailDTO destination = new OrderDetailDTO();

        destination.setOrderDetailId(source.getOrderDetailId());
        destination.setOrderId(source.getOrderId());
        destination.setQuantity(source.getQuantity());
        destination.setUnitPrice(source.getUnitPrice());

        Product product = productMapper.getProductById(source.getProductId());
        destination.setProductName(product.getProductName());

        return destination;
    }
}