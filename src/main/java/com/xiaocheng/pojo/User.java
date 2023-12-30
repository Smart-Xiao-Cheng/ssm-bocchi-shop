package com.xiaocheng.pojo;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String imageUrl;
    private int gender;
    private String address;
}
