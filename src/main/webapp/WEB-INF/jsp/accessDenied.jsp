<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" > 
<title>Access Denied</title>
</head>
<body>
<p><strong>Hi ${pageContext.request.userPrincipal.name}</strong>
<p>You are not authorized for this action. Please click on <a href="${pageContext.request.contextPath}/home">home</a> to return to home page.
</body>
</html>