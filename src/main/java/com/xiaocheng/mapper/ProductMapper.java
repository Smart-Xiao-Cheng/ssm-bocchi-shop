package com.xiaocheng.mapper;

import com.xiaocheng.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {
    // 获取所有产品
    @Select("select * from products")
    @Results({
            @Result(property = "productId", column = "productID"),
            @Result(property = "productName", column = "productName"),
            @Result(property = "productDescription", column = "productDescription"),
            @Result(property = "price", column = "price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "imageUrl", column = "imageUrl")
    })
    List<Product> getAllProducts();

    // 根据产品名获取产品
    @Select("select * from products where productName=#{name}")
    Product getProductByName(@Param("name") String name);

    // 根据产品名更新产品信息
    @Update("update products set productName=#{product.productName},productDescription=#{product.productDescription},price=#{product.price},quantity=#{product.quantity},imageUrl=#{product.imageUrl} where productName=#{product.productName}")
    int updateProductByName(@Param("product") Product product);

    // 根据产品名删除产品
    @Delete("delete from products where productName=#{name}")
    int deleteProductByName(@Param("name") String name);

    // 添加产品
    @Insert("insert into products(productName,productDescription,price,quantity) values(#{product.productName},#{product.productDescription},#{product.price},#{product.quantity})")
    int addProduct(@Param("product") Product product);

    // 根据产品名获取产品id
    @Select("select productID from products where productName=#{name}")
    int getProductIdByName(@Param("name") String name);

    // 根据产品id获取产品
    @Select("select * from products where productID=#{id}")
    Product getProductById(@Param("id") int id);

    // 查询是否存在该产品
    @Select("select count(*) from products where productName=#{name}")
    int isExistProduct(@Param("name") String name);

    @Select("select * from products where productName like CONCAT('%',#{keyword},'%')")
    @Results({
            @Result(property = "productId", column = "productID"),
            @Result(property = "productName", column = "productName"),
            @Result(property = "productDescription", column = "productDescription"),
            @Result(property = "price", column = "price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "imageUrl", column = "imageUrl")
    })
    List<Product> getProductsByKeyword(@Param("keyword") String keyword);

    // 根据productId更新产品信息
    @Update("update products set productName=#{product.productName},productDescription=#{product.productDescription},price=#{product.price},quantity=#{product.quantity},imageUrl=#{product.imageUrl} where productID=#{product.productId}")
    int updateProductById(@Param("product") Product product);

    // 根据产品id删除产品
    @Delete("delete from products where productID=#{id}")
    int deleteProductById(@Param("id") int id);
}