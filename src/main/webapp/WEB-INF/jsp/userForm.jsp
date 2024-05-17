<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png" href="https://e-tangata.co.nz/wp-content/uploads/2017/04/Max-Harris-002-e1528530445333.jpg">

    <meta charset="ISO-8859-1">
    <title>User Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
	    .grid-container {
	    display: grid;
	    grid-template-columns: 60% 40%;
			}
		
		.grid-item {
		    padding: 10px;
		}
    
    	.navbar-brand, .nav-link{
    		color: salmon;
    	}
        body {
            background-color: #f8f9fa; /* Light gray */
            color: #333; /* Dark gray */
            padding: 20px;
        }

        .form-container {
            margin: 0 auto;
            background-color: #fff; /* White */
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Light shadow */
        }

        .form-heading {
            color: #007bff; /* Blue */
            margin-bottom: 30px;
        }

        .form-label {
            font-weight: bold;
            color: #6610f2; /* Purple */
        }

        .form-control {
            border-color: #ced4da; /* Light gray */
            border-radius: 5px;
        }

        .form-control:focus {
            border-color: #80bdff; /* Light blue */
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25); /* Blue shadow */
        }

        .form-submit-btn {
            background-color: #28a745; /* Green */
            color: #fff; /* White */
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-submit-btn:hover {
            background-color: #218838; /* Dark green */
        }

        .list-heading {
            color: #dc3545; /* Red */
            margin-top: 50px;
        }

        .table-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff; /* White */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Light shadow */
            overflow-x: auto; /* Add horizontal scrollbar if needed */
        }

        .table-container table {
            margin: 0; /* Reset default margin */
            width: 100%; /* Ensure table fills container */
        }

        .table-container th,
        .table-container td {
            padding: 8px; /* Adjust padding as needed */
        }

        .action-links a {
            color: #007bff; /* Blue */
            text-decoration: none;
            margin-right: 10px;
        }

        .action-links a:hover {
            text-decoration: underline;
        }
        ul:before{
		    content:attr(aria-label);
		    font-size:120%;
		    font-weight:bold;
		    margin-left:-15px;
		}
    </style>
</head>
<body>
    <c:set var="authoritiesString" value="${pageContext.request.userPrincipal.principal.authorities}" />
    ${authoritiesString}
    <c:set var="isAdmin" value="false" />
<c:forEach items="${authoritiesString}" var="authority">
    <c:if test="${authority == 'Admin'}">
        You have the ADMIN authority!
        <c:set var="isAdmin" value="true" />
    </c:if>
</c:forEach>
${isAdmin}
<div class="container form-container">
	<nav class="navbar navbar-expand-lg navbar-dark">
	    <div class="container">
	        <a class="navbar-brand" href="/">Home</a>
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="navbarNav">
	            <ul class="navbar-nav ms-auto">
	                <sec:authorize access="hasAuthority('Admin')">
	                   <li class="nav-item"><a class="nav-link" href="/branch/">Branch Form</a></li>
	                   <li class="nav-item"><a class="nav-link" href="/role/">Role Form</a></li>
	               </sec:authorize>
	               <sec:authorize access="isAuthenticated">
	               	<li class="nav-item"><a class="nav-link" href="/account/">Account Form</a></li>
	               	<li class="nav-item"><a class="nav-link" href="/customer/">Customer Form</a></li>
	                   <li class="nav-item"><a class="nav-link" href="/user/">User Form</a></li>
	                   <li class="nav-item"><a class="nav-link" href="/transaction/">Transaction Form</a></li>
	               </sec:authorize>
	               <sec:authorize access="isAuthenticated">
	                   <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
	               </sec:authorize>
	            </ul>
	        </div>
	    </div>
	</nav>
	
	<div class="grid-container">
	        <!-- Left column: User List -->
	       <div class="grid-item">
	   <h1 class="text-center list-heading">List of Users</h1>
	   <table class="table">
	       <thead>
	           <tr>
	            <th><a href="findAll?sortBy=id">User id</a></th> 
				<th><a href="findAll?sortBy=username">Name</a></th> 
				<th><a href="findAll?sortBy=password">Password</a></th> 
				<th><a href="findAll?sortBy=email">Email</a></th> 
	               <th>Roles</th>
	               <sec:authorize access="hasAuthority('Admin')"><th>Action</th></sec:authorize>
	           </tr>
	       </thead>
	       <tbody>
	           <c:forEach items="${users}" var="user">
	               <tr>
	                   <td>${user.getId()}</td>
	                   <td>${user.getUsername()}</td>
	                   <td>${user.getPassword()}</td>
	                   <td>${user.getEmail()}</td>
	                   <td>
	                       <c:forEach items="${user.getRoles()}" var="role">
	                           ${role.getName()}
	                       </c:forEach>
	                   </td>
	                   <sec:authorize access="isAuthenticated">
	                   <td class="action-links">
	                   	<sec:authorize access="hasAuthority('Admin')">
	                       <a href="deleteUser?id=${user.getId()}">Delete</a>
	                       </sec:authorize>
	                       <a href="updateUser?id=${user.getId()}&sortedBy=${sortedBy}">Update</a>
	                   </td>
	                   </sec:authorize>
	               </tr>
	           </c:forEach>
	       </tbody>
	   </table>
	<c:set var="totalPages" value="${totalPages}"></c:set>
	<c:set var="sortedBy" value="${sortedBy}"></c:set>
	<c:set var="pageSize" value="${pageSize}"></c:set>
	
	<p>"${totalPages}" "${sortedBy}" "${pageSize}" </p>
	<div class="text-center form-heading">
	<%
	try{
		
	for(int i=0; i< (int)pageContext.getAttribute("totalPages"); i++){
		out.println("<a href=\"form?pageNo="+i
		+"&pageSize="+pageContext.getAttribute("pageSize")
		+"&sortedBy="+pageContext.getAttribute("sortedBy")
		+"\">"
		+i
		+"</a>");
	}
	}
	catch(NullPointerException np){
		System.err.println(np);
	}
	%>
	</div>
	     
	        </div>
	        <!-- Right column: Form -->
	       <div class="grid-item">
	           <h1 class="text-center form-heading">User Form</h1>
	           <f:form action="saveUser" modelAttribute="user">
	               <c:if test="${not empty ops}">
	               <ul aria-label="OPERACIONES:">
	                   <c:forEach items="${ops}" var="op">
	                       <li>${op}</li>
	                   </c:forEach>
	               </ul>
	           </c:if>
	   <f:form action="saveUser" modelAttribute="user">
	       <table class="table">
	           <tr>
	               <td><label for="id" class="form-label">User Id:</label></td>
	               <td><f:input path="id" readonly="true" value="${nextId}" class="form-control"/></td>
	               <td><f:errors path="id" cssStyle="color:purple;"></f:errors></td>
	           </tr>
	           <tr>
	               <td><label for="username" class="form-label">Name:</label></td>
	               <td><f:input path="username" value="${u.getUsername()}" class="form-control"/></td>
	               <td><f:errors path="username" cssStyle="color:purple;"></f:errors></td>
	           </tr>
	           <tr>
	               <td><label for="password" class="form-label">Password:</label></td>
	               <td><f:input path="password" value="${u.getPassword()}" class="form-control"/></td>
	               <td><f:errors path="password" cssStyle="color:purple;"></f:errors></td>
	           </tr>
	           <tr>
	               <td><label for="email" class="form-label">Email:</label></td>
	               <td><f:input path="email" value="${u.getEmail()}" class="form-control"/></td>
	               <td><f:errors path="email" cssStyle="color:purple;"></f:errors></td>
	           </tr>
	           <tr>
	               <td><label class="form-label">Roles:</label></td>
	               <td>
	                   <c:forEach items="${roles}" var="role">
	                       <f:checkbox checked="${u.getRoles().contains(role)?'checked':null}" 
	                      disabled="${fn:contains(pageContext.request.userPrincipal.authorities, 'Admin')}"
	                       path="roles" label="${role.getName()}" value="${role.getId()}" class="form-check-input"/>
	                   </c:forEach>
	               </td>
	               <td><f:errors path="roles" cssStyle="color:purple;"></f:errors></td>
	           </tr>
	           <tr>
	               <td colspan="3" align="center"><input type="submit" value="Submit" class="form-submit-btn"/></td>
	           </tr>
	       </table>
	   </f:form>
	
	           </f:form>
	       </div>
	   </div>

</div>
</body>
</html>