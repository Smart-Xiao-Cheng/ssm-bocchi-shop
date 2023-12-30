package com.xiaocheng.pojo;

import lombok.Data;

@Data
public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int productId;
    private int quantity;
    private float unitPrice;
}
