package com.xiaocheng.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Admin {
    private int adminId;
    private String adminName;
    private String password;
    private String email;
    private String phone;
    private int role; // 1:超级管理员 2:普通管理员 3:商家 4:订单管理员
    private Timestamp createAt;
    private Timestamp updateAt;
    private String imageUrl;
    private int gender;
}
