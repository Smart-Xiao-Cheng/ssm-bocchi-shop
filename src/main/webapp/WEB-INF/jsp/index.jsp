<%--
  Created by IntelliJ IDEA.
  User: xiao_cheng
  Date: 2023/12/10
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小孤独小卖部</title>
    <link rel="stylesheet" href="../../../resources/static/css/bootstrap.css">
    <link rel="stylesheet" href="../../../resources/static/css/xiaocheng.css">
    <link rel="stylesheet" href="../../../resources/static/css/custom.css">
    <link rel="stylesheet" href="../../../resources/static/css/animate.css">
    <link rel="stylesheet" href="../../../resources/static/css/hover.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <script src="../../../resources/static/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<%--  导航栏--%>
<div class="container">
    <nav class="navbar navbar-expand-md border-bottom">
        <%--    logo--%>
        <a class="navbar-brand" href="index.jsp">
            <img src="../../../resources/static/image/logo.jpg" alt="logo" height="40">
            小孤独小卖部
        </a>

        <%--        搜索框--%>

        <form class="form-inline mr-auto" action="/search" method="get" style="margin-bottom: 0px">
            <div class="input-group">
                <input type="search" name="keyword" class="form-control d-none d-md-block search-bar" placeholder="请输入商品名称">
            </div>
            <div class="input-group-append search-btn-icon">
                <button class="btn" type="submit">
                    <i class="bi bi-search"></i>
                </button>
            </div>
        </form>


        <div class="collapse navbar-collapse justify-content-end" id="headerNavbar">
            <ul class="navbar-nav">
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="index.jsp">首页</a>
                </li>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href=productlist.jsp>商品列表</a>
                </li>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="cart.jsp">购物车</a>
                </li>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="myorder.jsp">我的订单</a>
                </li>
                <% Boolean isLogin = (Boolean) session.getAttribute("isLogin"); %>
                <% if (isLogin == null || !isLogin) { %>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="login.jsp">登录</a>
                </li>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="register.jsp">注册</a>
                </li>
                <% } else { %>
                <li class="nav-item font-size-lg">
                    <a class="nav-link" href="#"><%= session.getAttribute("username") %></a>
                </li>
                <% } %>
            </ul>
        </div>
    </nav>
</div>

<div class="preface d-flex justify-content-center align-items-center animate__animated">
    <p>这里是浪漫主义小卖部，只售卖人间的浪漫</p>
</div>

<%--商品推荐--%>
<div class="container">

    <%-- 每行开始时创建一个新的row --%>
    <div class="row" style="transform: scale(0.8)">
        <div class="col animate__animated animate__fadeInUp">
            <!-- 商品卡片 -->
            <div class="card shadow-small" style="width: 18rem;">
                <img src="../../../resources/static/image/avatar.jpg" class="card-img-top" alt="商品图片">
                <div class="card-body">
                    <h5 class="card-title">沉默</h5>
                    <p class="card-text">沉默是今晚的康桥</p>
                    <div class="mt-3">
                        <!-- 商品表单 -->
                        <form action="/addToCart" method="POST">
                            <!-- 隐藏的输入字段，用于存储商品的 ID -->
                            <input type="hidden" name="productId" value="1">
                            <!-- 提交按钮 -->
                            <button type="submit" class="btn btn-outline-dark btn-sm">加入购物车</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="container footer">
    <div class="row">
        <div class="col-sm-12 text-center margin-tb-large">
            <p class="footer-copyright">Copyright ©2023 - 2024 By xiaocheng</p>
        </div>

    </div>
</div>

</body>
<!-- 引入 Bootstrap JS 和 jQuery -->

<script src="../../../resources/static/js/bootstrap.js"></script>
<script src="../../../resources/static/js/background-effects.js"></script>
<script>
    $(document).ready(function() {
        $('.preface').hover(function() {
            $(this).addClass('animate__fadeOut');
        }, function() {
            $(this).removeClass('animate__fadeOut');
        });
    });
</script>


</html>
