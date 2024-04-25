<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" > 
<title>Home</title>
</head>
<body>

<div  align="center">
<table>
<tr>
<sec:authorize access="isAuthenticated">
<td><a href="logout">Logout</a></td>
</sec:authorize>
</tr>
</table>
</div>

<div align="center">
<sec:authorize access="isAuthenticated">
<br>Welcome <sec:authentication property="principal.username"/>
<sec:authentication property="principal.authorities"/>
</sec:authorize>
<sec:authorize access="!isAuthenticated">
	<br> <a href="login">login</a>
</sec:authorize>
<br>
<h1>***Home***</h1>

 </div>
</body>
</html>