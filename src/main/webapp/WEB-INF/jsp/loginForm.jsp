<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" > 
<title>Login Form</title>
</head>
<body>
<div align="center">
<a href="${pageContext.request.contextPath}/">Home</a>
<h1>Login Form</h1>
<h3>${message}</h3>
<form action="login" method="POST">
<table>
<tr>
	<td>User Name:</td>
	<td><input type="text" name="username"></td>
</tr>
<tr>
	<td>Password:</td>
	<td><input type="password" name="password"></td>
</tr>


<tr>
	<td colspan="2" align="center"><input type="submit" value="submit" class="btn btn-primary"></td>
</tr>
</table>



</form>



</div>
</body>
</html>