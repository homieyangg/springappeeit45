<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>"/>
    <title>第一個</title>
</head>
<body>
<img src="<c:url value='/images/BookStore.gif'/>">
<div align="center">
    <font color="blue">${helloMessage}</font>
    <font color="red">${INSERT_SUCCESS}</font>
    <hr>
    <a href="<c:url value='/insertMemberForm' />">新增會員</a>
    <a href="<c:url value='/queryMembers' />">查詢會員</a>
</div>
</body>
</html>
