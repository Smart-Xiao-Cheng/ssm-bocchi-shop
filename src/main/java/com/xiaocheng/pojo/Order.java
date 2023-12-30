package com.xiaocheng.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private int orderStatus;
    private float totalAmount;
    private String orderNumber;
}
