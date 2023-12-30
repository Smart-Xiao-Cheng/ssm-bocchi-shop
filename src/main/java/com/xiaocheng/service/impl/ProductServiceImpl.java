package com.xiaocheng.service.impl;

import com.xiaocheng.mapper.ProductMapper;
import com.xiaocheng.pojo.Product;
import com.xiaocheng.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public Product getProductByName(String name) {
        return productMapper.getProductByName(name);
    }

    @Override
    public int updateProductByName(Product product) {
        return productMapper.updateProductByName(product);
    }

    @Override
    public int deleteProductByName(String name) {
        return productMapper.deleteProductByName(name);
    }

    @Override
    public int addProduct(Product product) {
        return productMapper.addProduct(product);
    }

    @Override
    public int getProductIdByName(String name) {
        return productMapper.getProductIdByName(name);
    }

    @Override
    public Product getProductById(int id) {
        return productMapper.getProductById(id);
    }

    @Override
    public int isExistProduct(String name) {
        return productMapper.isExistProduct(name);
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        return productMapper.getProductsByKeyword(keyword);
    }

    @Override
    public int updateProductById(Product product) {
        return productMapper.updateProductById(product);
    }

    @Override
    public int deleteProductById(int id) {
        return productMapper.deleteProductById(id);
    }
}
