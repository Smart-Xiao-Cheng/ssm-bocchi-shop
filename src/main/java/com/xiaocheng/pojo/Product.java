package com.xiaocheng.pojo;

import lombok.Data;

import java.util.Objects;

@Data
public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private float price;
    private int quantity;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId() &&
                Float.compare(getPrice(), product.getPrice()) == 0 &&
                Objects.equals(getProductName(), product.getProductName()) &&
                Objects.equals(getProductDescription(), product.getProductDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getPrice());
    }
}
