<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<title>User Form</title>
<style>
.nav-item, .nav-link, .navbar-brand{
  color:blue;
  font-style:italic;
}
</style>

</head>
<body>

<div align="center">
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="/">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <sec:authorize access="hasAuthority('Admin')">
                    <li class="nav-item"><a class="nav-link" href="/branch/">Paged Branch</a></li>
                    <li class="nav-item"><a class="nav-link" href="/role/">Paged Role Form</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated">
                <li class="nav-item"><a class="nav-link" href="/account/">Paged Account Form</a></li>
                <li class="nav-item"><a class="nav-link" href="/customer/">Paged Customer Form</a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/">Paged User Form</a></li>
                    <li class="nav-item"><a class="nav-link" href="/transaction/">Paged Transaction Form</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated">
                    <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
<sec:authorize access="isAuthenticated">
	<br>Welcome <br>principal.username: <sec:authentication property="principal.username"/>
<br>principal.authorities: <sec:authentication property="principal.authorities"/>
</sec:authorize>
<sec:authorize access="!isAuthenticated">
	<br> <a href="login">login</a>
</sec:authorize>

<c:if test="${not empty users}">
<h1>List of Employees</h1>
<table border="1">
<tr>
<th>User id</th><th>Name</th><th>Password</th><th>Email</th><th>Roles</th><th>Action</th>
</tr>

<tr>
<c:forEach items="${users}" var="user">
<td>${user.getId()}</td>
<td>${user.getUsername()}</td>
<td>${user.getPassword()}</td>
<td>${user.getEmail()}</td>
<td>
<c:forEach items="${user.getRoles()}" var="role">
${role.getName()}
</c:forEach>
</td>
<td>
<a href="deleteUser?userId=${user.getId()}">Delete</a>
<a href="updateUser?userId=${user.getId()}">Update</a>
</td>
</tr>
</c:forEach>
</table>
<p/>
<p/>
<p>Total Pages: ${totalPages} </p>

<c:set var="totalPages" value="${totalPages}"></c:set>
<c:set var="sortedBy" value="${sortedBy}"></c:set>
<c:set var="pageSize" value="${pageSize}"></c:set>

<%
for(int i=0; i< (int)pageContext.getAttribute("totalPages"); i++){
	out.println("<a href=\"pagedUser?pageNo="+i
	+"&pageSize="+request.getParameter("pageSize")
	+"&sortedBy="+request.getParameter("sortedBy")
	+"\">"
	+i
	+"</a>");
}
%>
</c:if>
</div>
</body>
</html>