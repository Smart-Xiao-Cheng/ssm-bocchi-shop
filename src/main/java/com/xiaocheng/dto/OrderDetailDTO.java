package com.xiaocheng.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private int orderDetailId;
    private int orderId;
    private String productName;
    private int quantity;
    private float unitPrice;
}
