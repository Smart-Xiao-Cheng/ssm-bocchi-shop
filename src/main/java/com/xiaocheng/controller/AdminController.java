package com.xiaocheng.controller;

import com.xiaocheng.dto.AdminAddUserDTO;
import com.xiaocheng.dto.AdminDTO;
import com.xiaocheng.dto.OrderDTO;
import com.xiaocheng.pojo.Admin;
import com.xiaocheng.pojo.Order;
import com.xiaocheng.pojo.Product;
import com.xiaocheng.pojo.User;
import com.xiaocheng.service.impl.AdminServiceImpl;
import com.xiaocheng.service.impl.OrderServiceImpl;
import com.xiaocheng.service.impl.ProductServiceImpl;
import com.xiaocheng.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductServiceImpl productService;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${productImage.upload.path}")
    private String productImageUploadPath;

    @ModelAttribute("currentTimeMillis")
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    // 管理员登录验证
    @PostMapping("/login/verify")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember", required = false) Integer remember,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        if (remember == null) {
            remember = 0;
        }
        if (adminService.checkAdmin(username, password) > 0) {
            if (remember == 1) {
                AdminDTO adminDTO = new AdminDTO();
                adminDTO.setName(username);
                adminDTO.setPassword(password);
                // 将对象转换为JSON字符串
                ObjectMapper mapper = new ObjectMapper();
                String adminJson = mapper.writeValueAsString(adminDTO);
                String encodedAdminJson = URLEncoder.encode(adminJson, StandardCharsets.UTF_8.toString());
                Cookie cookie = new Cookie("admin", encodedAdminJson);
                cookie.setMaxAge(60); // 设置Cookie的有效时间为60秒
                response.addCookie(cookie);
            }
            HttpSession session = request.getSession();
            Admin admin = adminService.getAdminByName(username);
//            System.out.println(admin);
            // 将管理员对象存入Session
            session.setAttribute("admin", admin);
            // 将管理员登录状态存入Session
            session.setAttribute("adminIsLogin", true);
            return "redirect:/admin";
        } else {
            return "redirect:/admin/login";
        }

    }

    // 显示登录页面
    @GetMapping("/login/verify")
    public String showLoginPage() {
        return "/admin/login";
    }

    // 管理员退出登录
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("adminIsLogin");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("admin")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/admin/login";
    }

    // 在管理员主页显示总收入，总用户，总订单数
    @GetMapping("/welcome/dashboard")
    @ResponseBody
    public Map<String, Integer> getDashboardData() {
        Map<String, Integer> dashboardData = new HashMap<>();
        dashboardData.put("totalIncome", (int) orderService.getTotalAmount());
        dashboardData.put("totalUsers", userService.getUserCount());
        dashboardData.put("totalOrders", orderService.getOrderCount());
        return dashboardData;
    }

    @GetMapping("/welcome/orders/sevenDays")
    @ResponseBody
    public List<Map<String, Object>> getRecentSevenDaysOrders() {
        return orderService.getRecentSevenDaysOrders();
    }

    @GetMapping("/welcome/orders/sevenDaysTotalAmount")
    @ResponseBody
    public List<Map<String, Object>> getRecentSevenDaysTotalAmount() {
        return orderService.getRecentSevenDaysTotalAmount();
    }

    // 在管理主页显示最近6个订单
    @GetMapping("/welcome/orders/recentOrders")
    @ResponseBody
    public List<OrderDTO> getRecentOrders() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orderList = orderService.getRecentOrders();
        for (Order order : orderList) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @GetMapping("/user/userInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "userId") String sort,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String keywords) {

        List<User> users;
        if (keywords != null && !keywords.isEmpty()) {
            users = userService.getUsersByKeyword(keywords);
        } else {
            users = userService.getAllUsers();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", users);

        return result;
    }

    @PostMapping("/user/useradd")
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody AdminAddUserDTO userParam) {

        User user = new User();
        user.setUserName(userParam.getUsername());
        user.setPassword(userParam.getPassword());
        user.setEmail(userParam.getEmail());
        user.setPhone(userParam.getPhone());
        user.setGender(userParam.getGender());

        int result = userService.addUser(user);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("status", "success");
            response.put("message", "添加成功");
        } else {
            response.put("status", "error");
            response.put("message", "添加失败");
        }
        return response;
    }


    @GetMapping("/user/userInfo/{userId}")
    @ResponseBody
    public AdminAddUserDTO getUserInfoByUserId(@PathVariable String userId) {
        // 转换 userId 为 Integer 类型
        int id = Integer.parseInt(userId);
        // 调用 userService 获取用户信息
        User user = userService.getUserById(id);
        AdminAddUserDTO userDTO = new AdminAddUserDTO();
        userDTO.setUsername(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setGender(user.getGender());
        // 返回用户信息
        return userDTO;
    }

    @PostMapping("/user/update")
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody User userParam) {
        // 从 userParam 中获取更新的用户信息
        User user = new User();
        user.setUserId(userParam.getUserId());
        user.setUserName(userParam.getUserName());
        user.setPassword(userParam.getPassword());
        user.setEmail(userParam.getEmail());
        user.setPhone(userParam.getPhone());
        user.setGender(userParam.getGender());

        // 调用 userService 更新用户信息
        userService.updateUserById(user);

        // 根据更新操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "更新成功");
        return response;
    }

    @DeleteMapping("/user/delete/{userId}")
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable String userId) {
        // 转换 userId 为 Integer 类型
        int id = Integer.parseInt(userId);
        // 调用 userService 删除用户
        userService.deleteUserById(id);
        // 根据删除操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "删除成功");
        return response;
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    public Map<String, Object> deleteUsers(@RequestParam String id) {
        // 将 id 字符串分割为一个数组
        String[] idArr = id.split(",");
        // 遍历 id 数组，对每个 ID，调用 userService 删除对应的用户
        for (String userIdStr : idArr) {
            int userId = Integer.parseInt(userIdStr);
            userService.deleteUserById(userId);
        }
        // 根据删除操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "删除成功");
        return response;
    }

    @GetMapping("/user/export")
    @ResponseBody
    public List<User> exportUsers() {
        // 调用 userService 获取所有用户的数据
        List<User> users = userService.getAllUsers();
        // 返回用户数据
        return users;
    }


    @PostMapping("/user/import")
    @ResponseBody
    public Map<String, Object> importUsers(@RequestBody List<User> users) {
        // 遍历 users 列表，对每个 User，调用 userService 添加这个用户
        for (User user : users) {
            userService.addUser(user);
        }
        // 根据添加操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "导入成功");
        return response;
    }

    @GetMapping("/profile/info")
    public ResponseEntity<Admin> getAdminInfo(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        return ResponseEntity.ok(admin);
    }


    @PostMapping("/profile/update")
    @ResponseBody
    public Map<String, String> updateAdminProfile(Admin admin, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        Admin sessionAdmin = (Admin) session.getAttribute("admin");
        if(sessionAdmin == null) {
            response.put("status", "fail");
            response.put("message", "管理员未登录");
            return response;
        }
        sessionAdmin.setRole(admin.getRole());
        sessionAdmin.setGender(admin.getGender());
        sessionAdmin.setPhone(admin.getPhone());
        sessionAdmin.setEmail(admin.getEmail());
        int result = adminService.updateAdminByName(sessionAdmin);
        if(result > 0) {
            response.put("status", "success");
            response.put("message", "信息更新成功");
            session.setAttribute("admin", sessionAdmin);
        } else {
            response.put("status", "fail");
            response.put("message", "信息更新失败");
        }
        return response;
    }


    @PostMapping("/profile/updateAvatar")
    @ResponseBody
    public Map<String, String> updateAdminAvatar(@RequestPart("file") MultipartFile file, HttpSession session) throws IOException {
        Map<String, String> response = new HashMap<>();
        Admin sessionAdmin = (Admin) session.getAttribute("admin");
        if(sessionAdmin == null) {
            response.put("status", "fail");
            response.put("message", "管理员未登录");
            return response;
        }
        if(file.isEmpty()) {
            response.put("status", "fail");
            response.put("message", "文件为空");
            return response;
        }
        // resources下的资源文件夹路径
        String resourcePath = "/static/image/avatar/";
        // 获取资源文件夹的绝对路径
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        // 获取文件夹的File对象
        File folder = classPathResource.getFile();
        // 获取文件的扩展名
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 使用管理员的id作为文件名
        String fileName = sessionAdmin.getAdminId() + extension;
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        File dest = new File(folder, fileName);
        // 如果存在同名的文件，先删除这个文件
        if (dest.exists()) {
            dest.delete();
        }
        try {
            // 将上传的文件移动到dest下
            file.transferTo(dest);
//            file.transferTo(sourcefile);
            sessionAdmin.setImageUrl("image/avatar/" + fileName);
            adminService.updateAdminByName(sessionAdmin);
            response.put("status", "success");
            response.put("message", "头像上传成功");
            response.put("imageUrl", "image/avatar/" + fileName);
            session.setAttribute("admin", sessionAdmin);
            session.setAttribute("currentTimeMillis", System.currentTimeMillis());
        } catch (IOException e) {
            response.put("status", "fail");
            response.put("message", "头像上传失败: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    @PostMapping("/welcome/update/password")
    @ResponseBody
    public Map<String, String> updatePassword(@RequestParam String oldPassword,
                                              @RequestParam String password,
                                              @RequestParam String rePassword,
                                              HttpSession session) {
        Map<String, String> response = new HashMap<>();
        Admin sessionAdmin = (Admin) session.getAttribute("admin");
        if(sessionAdmin == null) {
            response.put("status", "fail");
            response.put("message", "管理员未登录");
            return response;
        }
        // 检查旧密码是否正确
        if(!sessionAdmin.getPassword().equals(oldPassword)) {
            response.put("status", "fail");
            response.put("message", "旧密码错误");
            return response;
        }
        // 检查两次输入的密码是否一致
        if (!password.equals(rePassword)) {
            response.put("status", "fail");
            response.put("message", "两次输入的密码不一致");
            return response;
        }
        sessionAdmin.setPassword(password);
        int isUpdated = adminService.updateAdminByName(sessionAdmin);
        if(isUpdated > 0) {
            response.put("status", "success");
            response.put("message", "密码更新成功");
        } else {
            response.put("status", "fail");
            response.put("message", "密码更新失败");
        }
        return response;
    }



    @GetMapping("/admin/adminInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(@RequestParam(required = false) String keyword, @RequestParam(required = false) int role) {

        List<Admin> admins;
        if (keyword != null && !keyword.isEmpty()) {
            admins = adminService.getAdminsByKeyword(keyword);
        } else {
            admins = adminService.getAllAdmins();
        }
        if (role != 0) {
            if (role == 1) {
                for (int i=0; i<admins.size(); i++) {
                    if (admins.get(i).getRole() != 1) {
                        admins.remove(i);
                        i--;
                    }
                }
            }
            if (role == 2) {
                for (int i=0; i<admins.size(); i++) {
                    if (admins.get(i).getRole() != 2) {
                        admins.remove(i);
                        i--;
                    }
                }
            }
            if (role == 3) {
                for (int i=0; i<admins.size(); i++) {
                    if (admins.get(i).getRole() != 3) {
                        admins.remove(i);
                        i--;
                    }
                }
            }
            if (role == 4) {
                for (int i=0; i<admins.size(); i++) {
                    if (admins.get(i).getRole() != 4) {
                        admins.remove(i);
                        i--;
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", admins);

        return result;
    }

    @GetMapping("/product/productInfo")
    @ResponseBody
    public Map<String, Object> getProductInfo(@RequestParam(required = false) String keyword, @RequestParam(required = false) int status) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.getProductsByKeyword(keyword);
        } else {
            products = productService.getAllProducts();
        }
        if (status != 0) {
            if (status == 1) {
                for (int i=0; i<products.size(); i++) {
                    if (products.get(i).getQuantity() < 50) {
                        products.remove(i);
                        i--;
                    }
                }
            }
            if (status == 2) {
                for (int i=0; i<products.size(); i++) {
                    if (products.get(i).getQuantity() >= 50 || products.get(i).getQuantity() == 0) {
                        products.remove(i);
                        i--;
                    }
                }
            }
            if (status == 3) {
                for (int i=0; i<products.size(); i++) {
                    if (products.get(i).getQuantity() != 0) {
                        products.remove(i);
                        i--;
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", products);
        return result;
    }


    @PostMapping("/product/add")
    @ResponseBody
    public Map<String, String> addProduct(Product product) {
        Map<String, String> response = new HashMap<>();
        int result = productService.addProduct(product);
        if(result > 0) {
            response.put("status", "success");
            response.put("message", "信息更新成功");
        } else {
            response.put("status", "fail");
            response.put("message", "信息更新失败");
        }
        return response;
    }


    @PostMapping("/product/addImage")
    @ResponseBody
    public Map<String, String> addProductImage(@RequestPart("file") MultipartFile file, @RequestPart("productName") String productName) {
        Map<String, String> response = new HashMap<>();
        if(file.isEmpty()) {
            response.put("status", "fail");
            response.put("message", "文件为空");
            return response;
        }
        Product product = productService.getProductByName(productName);
        // 获取文件的扩展名
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 使用商品的id作为文件名
        String fileName = product.getProductId() + extension;
        File dest = new File(productImageUploadPath + fileName);
        // 如果存在同名的文件，先删除这个文件
        if(dest.exists()) {
            dest.delete();
        }
        try {
            file.transferTo(dest);
            product.setImageUrl("image/product/" + fileName);
            productService.updateProductById(product);
            response.put("status", "success");
            response.put("message", "图片上传成功");
            response.put("imageUrl", "image/product/" + fileName);
        } catch (IOException e) {
            response.put("status", "fail");
            response.put("message", "图片上传失败: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    @GetMapping("/product/export")
    @ResponseBody
    public List<Product> exportProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }


    @DeleteMapping("/product/delete/{productId}")
    @ResponseBody
    public Map<String, Object> deleteProduct(@PathVariable int productId) {
        // 调用 productService 删除用户
        int result = productService.deleteProductById(productId);
        if (result > 0) {
            // 根据删除操作的结果，构造并返回响应
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "删除成功");
            return response;
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", "删除失败");
            return response;
        }
    }

    @GetMapping("/product/productInfo/{productId}")
    @ResponseBody
    public Product getProductInfo(@PathVariable int productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/product/update")
    @ResponseBody
    public Map<String, String> updateProduct(Product product) {
        Map<String, String> response = new HashMap<>();
        int result = productService.updateProductById(product);
        if(result > 0) {
            response.put("status", "success");
            response.put("message", "信息更新成功");
        } else {
            response.put("status", "fail");
            response.put("message", "信息更新失败");
        }
        return response;
    }

    @GetMapping("/getCurrentAdmin")
    @ResponseBody
    public Admin getCurrentAdmin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        return admin;
    }

    @PostMapping("/admin/add")
    @ResponseBody
    public Map<String, Object> addAdmin(@RequestBody Admin adminParam) {

        Admin admin = new Admin();
        admin.setAdminName(adminParam.getAdminName());
        admin.setPassword(adminParam.getPassword());
        admin.setEmail(adminParam.getEmail());
        admin.setPhone(adminParam.getPhone());
        admin.setGender(adminParam.getGender());
        admin.setRole(adminParam.getRole());

        int result = adminService.addAdmin(admin);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("status", "success");
            response.put("message", "添加成功");
        } else {
            response.put("status", "error");
            response.put("message", "添加失败");
        }
        return response;
    }



    @DeleteMapping("/admin/delete/{adminId}")
    @ResponseBody
    public Map<String, Object> deleteAdminById(@PathVariable String adminId) {
        // 转换 adminId 为 Integer 类型
        int id = Integer.parseInt(adminId);
        // 调用 adminService 删除管理员
        int result = adminService.deleteAdminById(id);
        // 根据删除操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("status", "success");
            response.put("message", "删除成功");
        } else {
            response.put("status", "error");
            response.put("message", "删除失败");
        }
        return response;
    }


    @DeleteMapping("/admin/delete")
    @ResponseBody
    public Map<String, Object> deleteAdmins(@RequestParam String id) {
        // 将 id 字符串分割为一个数组
        String[] idArr = id.split(",");
        int result = 0;
        // 遍历 id 数组，对每个 ID，调用 userService 删除对应的用户
        for (String adminIdStr : idArr) {
            int adminId = Integer.parseInt(adminIdStr);
            result = adminService.deleteAdminById(adminId);
        }
        // 根据删除操作的结果，构造并返回响应
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("status", "success");
            response.put("message", "删除成功");
        } else {
            response.put("status", "error");
            response.put("message", "删除失败");
        }
        return response;
    }

    @GetMapping("/order/orderInfo")
    @ResponseBody
    public Map<String, Object> getOrderInfo(@RequestParam(required = false) String username,
                                            @RequestParam(required = false) String OrderNumber,
                                            @RequestParam(required = false) String beginTime,
                                            @RequestParam(required = false) String endTime,
                                            @RequestParam int status) throws ParseException {
        List<Order> orders;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp time1 = null;
        Timestamp time2 = null;
        /*
        * 如果status为0，则查询时orderStatus不作为查询条件
        * 如果username为null，则查询时userId不作为查询条件
        */
        if (status == 0) {
            if (beginTime != null && !beginTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
                time1 = new Timestamp(dateFormat.parse(beginTime).getTime());
                time2 = new Timestamp(dateFormat.parse(endTime).getTime());
            }
            if (username != null && !username.isEmpty()) {
                int userId = userService.getUserIdByName(username);
                orders = orderService.findOrdersWithConditions(userId, time1, time2, null, OrderNumber);
            } else {
                orders = orderService.findOrdersWithConditions(null, time1, time2, null, OrderNumber);
            }
        }
        else {
            if (beginTime != null && !beginTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
                time1 = new Timestamp(dateFormat.parse(beginTime).getTime());
                time2 = new Timestamp(dateFormat.parse(endTime).getTime());
            }
            if (username != null && !username.isEmpty()) {
                int userId = userService.getUserIdByName(username);
                orders = orderService.findOrdersWithConditions(userId, time1, time2, status, OrderNumber);
            } else {
                orders = orderService.findOrdersWithConditions(null, time1, time2, status, OrderNumber);
            }
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTOList.add(orderDTO);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", orderDTOList);
        return result;
    }
}



