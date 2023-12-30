package com.xiaocheng.service.impl;

import com.xiaocheng.mapper.AdminMapper;
import com.xiaocheng.pojo.Admin;
import com.xiaocheng.service.AdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.getAllAdmins();
    }

    @Override
    public int updateAdminByName(Admin admin) {
        return adminMapper.updateAdminByName(admin);
    }

    @Override
    public int deleteAdminByName(String name) {
        return adminMapper.deleteAdminByName(name);
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public int getAdminIdByName(String name) {
        return adminMapper.getAdminIdByName(name);
    }

    @Override
    public Admin getAdminById(int id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public int checkAdmin(String name, String password) {
        return adminMapper.checkAdmin(name, password);
    }

    @Override
    public int isExistAdmin(String name) {
        return adminMapper.isExistAdmin(name);
    }

    @Override
    public Admin getAdminByName(String name) {
        return adminMapper.getAdminByName(name);
    }

    @Override
    public List<Admin> getAdminsByKeyword(String keyword) {
        return adminMapper.getAdminsByKeyword(keyword);
    }

    @Override
    public int deleteAdminById(int adminId) {
        return adminMapper.deleteAdminById(adminId);
    }
}
