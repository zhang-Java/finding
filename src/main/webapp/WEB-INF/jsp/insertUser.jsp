<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>插入用户页面</title>
</head>
<body>
<h1>插入用户页面222</h1>
<div>
  <img src="/img/th%20(1).jpg" alt="">
</div>
<form action="/user/doInsertUser" method="post">
  用户名: <input type="text" name="name"/><br/>
  密  码: <input type="password" name="password"/><br/>
  <input type="submit" value="提交"/>
</form>
</body>
</html>