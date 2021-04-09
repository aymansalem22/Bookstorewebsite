<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Manage Users  - Evergreeen Bookstore Adminstration</title>
</head>
<body>
<jsp:directive.include file="header.jsp"/>

<div align="center">
<h2>Users Managment</h2>
<h3><a href="">Create new Users</a></h3>

</div>



<div align="center">
   <table border="1">
   <tr>
   <th>Index</th>
   <th>ID</th>
   <th>Email</th>
   <th>Full Name</th>
   <th>Actions</th>
   </tr>
   <c:forEach var="user" items="${listUsers}" varStatus="status">
   <tr>
   <td>${status.index}</td>
   <td></td>
  
   </tr>
   </c:forEach>
   </table>
</div>



<div align="center">
</div>


<jsp:directive.include file="footer.jsp"/>
</body>
</html>