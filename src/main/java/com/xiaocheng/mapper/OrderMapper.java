package com.xiaocheng.mapper;

import com.xiaocheng.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface OrderMapper {
    // 获取所有订单
    @Select("select * from orders")
    @Results({
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "userId", column = "userID"),
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    List<Order> getAllOrders();

    // 根据用户ID获取订单
    @Select("select * from orders where userID=#{userId}")
    @Results({
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "userId", column = "userID"),
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    List<Order> getOrdersByUserId(@Param("userId") int userId);

    // 更新订单信息
    @Update("update orders set userID=#{order.userId},orderDate=#{order.orderDate},orderStatus=#{order.orderStatus},totalAmount=#{order.totalAmount} where orderID=#{order.orderId}")
    int updateOrder(@Param("order") Order order);

    // 删除订单
    @Delete("delete from orders where orderID=#{orderId}")
    int deleteOrder(@Param("orderId") int orderId);

    // 添加订单
    @Insert("insert into orders(userID,orderDate,orderStatus,totalAmount) values(#{order.userId},#{order.orderDate},#{order.orderStatus},#{order.totalAmount})")
    int addOrder(@Param("order") Order order);

    // 根据订单ID获取订单
    @Select("select * from orders where orderID=#{orderId}")
    @Results({
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "userId", column = "userID"),
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    Order getOrderById(@Param("orderId") int orderId);

    // 获取订单总数
    @Select("select count(*) from orders")
    int getOrderCount();

    // 获取订单总金额
    @Select("select sum(totalAmount) from orders")
    float getTotalAmount();

    // 获取最近7天的订单数
    @Select("select date(orderDate) as orderDate, count(*) as orderCount from orders where orderDate >= date_sub(curdate(), interval 7 day) group by date(orderDate)")
    @Results({
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderCount", column = "orderCount")
    })
    List<Map<String, Object>> getRecentSevenDaysOrders();

    // 获取最近7天的订单总金额
    @Select("select date(orderDate) as orderDate, sum(totalAmount) as totalAmount from orders where orderDate >= date_sub(curdate(), interval 7 day) group by date(orderDate)")
    @Results({
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    List<Map<String, Object>> getRecentSevenDaysTotalAmount();

    // 获取最近6个订单
    @Select("select * from orders order by orderDate desc limit 6")
    @Results({
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "userId", column = "userID"),
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    List<Order> getRecentOrders();

    @SelectProvider(type = OrderSqlProvider.class, method = "findOrdersWithConditions")
    @Results({
            @Result(property = "orderId", column = "orderID"),
            @Result(property = "userId", column = "userID"),
            @Result(property = "orderDate", column = "orderDate"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "totalAmount", column = "totalAmount")
    })
    List<Order> findOrdersWithConditions(@Param("userId") Integer userId,
                                         @Param("beginTime") Timestamp beginTime,
                                         @Param("endTime") Timestamp endTime,
                                         @Param("orderStatus") Integer orderStatus,
                                         @Param("orderNumber") String orderNumber);

    class OrderSqlProvider {
        public String findOrdersWithConditions(Map<String, Object> params) {
            return new SQL() {{
                SELECT("*");
                FROM("orders");
                if (params.get("userId") != null) {
                    WHERE("userID = #{userId}");
                }
                if (params.get("beginTime") != null && params.get("endTime") != null) {
                    WHERE("orderDate BETWEEN #{beginTime} AND #{endTime}");
                }
                if (params.get("orderStatus") != null) {
                    WHERE("orderStatus = #{orderStatus}");
                }
                if (params.get("orderNumber") != null) {
                    WHERE("orderNumber = #{orderNumber}");
                }
            }}.toString();
        }
    }
}

