package com.xiaocheng.mapper;

import com.xiaocheng.pojo.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    @Select("select * from admins")
    @Results({
            @Result(property = "adminId", column = "adminId"),
            @Result(property = "adminName", column = "adminName"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "role", column = "role"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "gender", column = "gender")
    })
    List<Admin> getAllAdmins();

    @Select("select * from admins where adminName=#{name}")
    @Results({
            @Result(property = "adminId", column = "adminId"),
            @Result(property = "adminName", column = "adminName"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "role", column = "role"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "gender", column = "gender")
    })
    Admin getAdminByName(@Param("name") String name);

    @Update("update admins set adminName=#{admin.adminName},password=#{admin.password},email=#{admin.email},phone=#{admin.phone},imageUrl=#{admin.imageUrl} where adminName=#{admin.adminName}")
    int updateAdminByName(@Param("admin") Admin admin);

    @Delete("delete from admins where adminName=#{name}")
    int deleteAdminByName(@Param("name") String name);

    @Insert("insert into admins(adminName,password,email,phone,imageUrl,gender,role) values(#{admin.adminName},#{admin.password},#{admin.email},#{admin.phone},#{admin.imageUrl},#{admin.gender},#{admin.role})")
    int addAdmin(@Param("admin") Admin admin);

    @Select("select adminId from admins where adminName=#{name}")
    int getAdminIdByName(@Param("name") String name);

    @Select("select * from admins where adminId=#{id}")
    @Results({
            @Result(property = "adminId", column = "adminId"),
            @Result(property = "adminName", column = "adminName"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "role", column = "role"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "gender", column = "gender")
    })
    Admin getAdminById(@Param("id") int id);

    @Select("select count(*) from admins where adminName=#{name}")
    int isExistAdmin(@Param("name") String name);

    @Select("select count(*) from admins where adminName=#{name} and password=#{password}")
    int checkAdmin(@Param("name") String name, @Param("password") String password);

    @Select("select * from admins where adminName like CONCAT('%',#{keyword},'%')")
    @Results({
            @Result(property = "adminId", column = "adminId"),
            @Result(property = "adminName", column = "adminName"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "role", column = "role"),
            @Result(property = "createAt", column = "createAt"),
            @Result(property = "updateAt", column = "updateAt"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "gender", column = "gender")
    })
    List<Admin> getAdminsByKeyword(@Param("keyword") String keyword);

    @Delete("DELETE FROM admins WHERE adminID = #{adminId}")
    int deleteAdminById(@Param("adminId") int adminId);
}