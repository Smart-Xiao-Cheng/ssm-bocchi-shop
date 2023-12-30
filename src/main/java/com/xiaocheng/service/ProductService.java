package com.xiaocheng.service;

import com.xiaocheng.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductByName(String name);

    int updateProductByName(Product product);

    int deleteProductByName(String name);

    int addProduct(Product product);

    int getProductIdByName(String name);

    Product getProductById(int id);

    int isExistProduct(String name);

    List<Product> getProductsByKeyword(String keyword);

    int updateProductById(Product product);

    int deleteProductById(int id);
}
