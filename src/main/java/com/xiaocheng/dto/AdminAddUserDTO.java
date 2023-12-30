package com.xiaocheng.dto;

import lombok.Data;

@Data
public class AdminAddUserDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private int gender;
}
