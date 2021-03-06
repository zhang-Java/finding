<%--
  Created by IntelliJ IDEA.
  User: sure
  Date: 2020/4/14
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Finding</title>
    <link rel="stylesheet" type="text/css" href="/css/index.css"/>

</head>
<body>
<script src="/js/jquery.js"></script>
<script src="/js/index.js" type="text/javascript" charset="utf-8"></script>

<div id="login">
    <div style="float: left;margin-top: 5px;color: white;font-size: 18px;margin-right: 10px;">登录</div>
    <div id="l_png" style="margin-right: 15px;"><img src="/img/login.png" width="22px" height="25px" style="margin: 4px;margin-left: 7px;"></div>
    <div><img src="/img/set.png" width="26px" height="26px" style="float: left;margin-top: 5px"></div>
</div>

<div id="find">

    <div id="logo">
        <div id="l_logo">
            <img src="/img/logo.png" width="38px" height="55px">
        </div>
        <div id="l_text">
            Finding
        </div>
    </div>

    <div id="f1" class="f_tag hand f1">
        快速
    </div>
    <div id="s1" class="skew f_tag hand s1"></div>
    <div id="f2" class="f_tag f2 hand">
        完整
    </div>
    <div id="s2" class="skew f_tag s2 hand"></div>
    <form action="#" method="post">
        <input type="text" name="in" id="in"/>
        <div id="f_png" class="hand">
            <img src="/img/find.png" width="30px" height="30px">
        </div>
    </form>

</div>

<div id="change">
    <div id="left" class="btn">
        <img src="/img/left.png" class="b_img hand" id="left1">
    </div>
    <div id="right" class="btn">
        <img src="/img/right.png" class="b_img hand" id="right1">
    </div>
</div>

</body>
</html>



