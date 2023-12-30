package com.xiaocheng.mapper;

import com.xiaocheng.pojo.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductMapperTest {
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testGetAllProducts () {
        Product product = productMapper.getProductById(1);
        System.out.println(product);

    }
}
