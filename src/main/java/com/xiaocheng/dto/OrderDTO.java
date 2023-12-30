package com.xiaocheng.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {
    private int orderId;
    private String orderNumber;
    private String username;
    private Timestamp orderDate;
    private float totalAmount;
    private int orderStatus; // 1: 未付款 2: 已付款 3: 已发货 4: 已完成
}
