package com.xiaocheng.service;

import com.xiaocheng.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    int updateAdminByName(Admin admin);
    int deleteAdminByName(String name);
    int addAdmin(Admin admin);
    int getAdminIdByName(String name);
    Admin getAdminById(int id);
    int isExistAdmin(String name);
    int checkAdmin(String name, String password);
    Admin getAdminByName(String name);
    List<Admin> getAdminsByKeyword(String keyword);
    int deleteAdminById(int adminId);
}
