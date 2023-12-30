package com.xiaocheng.mapper;


import com.github.pagehelper.Page;
import com.xiaocheng.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper // 这个注解表示这是一个mybatis的mapper类
public interface UserMapper {
    // 获取所有用户
    @Select("select * from users")
    List<User> getAllUsers();
    // 根据用户名获取用户
    @Select("select * from users where userName=#{name}")
    User getUserByName(@Param("name") String name);
    // 根据用户名更新用户信息
    @Update("update users set userName=#{user.userName},password=#{user.password},email=#{user.email},phone=#{user.phone},imageUrl=#{user.imageUrl} where userName=#{user.userName}")
    void updateUserByName(@Param("user") User user);
    // 根据用户名删除用户
    @Delete("delete from users where userName=#{name}")
    void deleteUserByName(@Param("name") String name);
    // 添加用户
    @Insert("INSERT INTO users (userName, password, email, phone, gender) VALUES (#{user.userName}, #{user.password}, #{user.email}, #{user.phone}, #{user.gender})")
    int addUser(@Param("user") User user);
    // 根据用户名获取用户id
    @Select("select userID from users where userName=#{name}")
    int getUserIdByName(@Param("name") String name);
    // 根据用户id获取用户
    @Select("select * from users where userID=#{id}")
    User getUserById(@Param("id") int id);
    // 查询是否存在该用户
    @Select("select count(*) from users where userName=#{name}")
    int isExistUser(@Param("name") String name);
    // 验证用户密码是否正确
    @Select("select count(*) from users where userName=#{name} and password=#{password}")
    int checkUser(@Param("name") String name, @Param("password") String password);
    // 获得用户总数
    @Select("select count(*) from users")
    int getUserCount();

    @SelectProvider(type = UserSqlProvider.class, method = "selectUsersByPage")
    List<User> selectUsersByPage(@Param("sort") String sort,
                                 @Param("sortOrder") String sortOrder,
                                 @Param("keywords") String keywords);

    public static class UserSqlProvider {
        public String selectUsersByPage(Map<String, Object> params) {
            String sort = (String) params.get("sort");
            String sortOrder = (String) params.get("sortOrder");
            String keywords = (String) params.get("keywords");

            return new SQL() {{
                SELECT("*");
                FROM("users");
                if (keywords != null && !keywords.isEmpty()) {
                    WHERE("name LIKE CONCAT('%', #{keywords}, '%') OR username LIKE CONCAT('%', #{keywords}, '%')");
                }
                ORDER_BY(sort + " " + sortOrder);
            }}.toString();
        }
    }

    // 根据id更新用户信息
    @Update("update users set userName=#{user.userName},password=#{user.password},email=#{user.email},phone=#{user.phone},gender=#{user.gender} where userID=#{user.userId}")
    int updateUserById(@Param("user") User user);

    // 根据id删除用户
    @Delete("delete from users where userID=#{id}")
    int deleteUserById(@Param("id") int id);

    @Select("select * from users where userName like CONCAT('%', #{username}, '%')")
    List<User> getUsersByKeyword(@Param("username") String username);
}
