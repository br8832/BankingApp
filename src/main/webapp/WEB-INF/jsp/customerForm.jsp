<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<title>Customer Form</title>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="https://i.pinimg.com/736x/7a/91/2f/7a912ffad65f68916e118edda61e4494.jpg">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
body {
    background-image: url('https://bouqs.com/blog/wp-content/uploads/2022/07/cherry-blossom-tree-1225186_1280-1080x720.jpg');
    background-size: cover;
    background-position: center;
    color: #333333; /* Dark gray text color */
}

.container {
    margin-top: 50px;
}

h1 {
    color: #ff69b4; /* Pink heading */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.form-control {
    background-color: rgba(255, 255, 255, 0.2); /* Semi-transparent white background */
    border: 1px solid #ff69b4; /* Pink border */
    color: #333333; /* Dark gray text color */
    border-radius: 5px; /* Rounded corners */
}

.table {
    margin-top: 20px;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background for tables */
}

.table th, .table td {
    border: none; /* Remove border */
    padding: 8px;
    color: darkmagenta; /* White text color for table cells */
}

.submit-button {
    background-color: #ff69b4; /* Pink button background */
    color: #333333; /* Dark gray text color */
    border: none;
    padding: 10px 20px;
    cursor: pointer;
}

.submit-button:hover {
    background-color: #ff1493; /* Darker pink background on hover */
}

.label {
    color: #ff1493 !important; /* Pink label color */
    font-weight: bold;
}
.address-line {
    overflow: hidden;
    text-overflow: ellipsis;
}
.nav-link{
	color: blue;
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
<div class="container">
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
	<sec:authorize access="isAuthenticated">
<br>Welcome <sec:authentication property="principal.username"/>
<sec:authentication property="principal.authorities"/>
</sec:authorize>
<sec:authorize access="!isAuthenticated">
	<br> <a href="login">login</a>
</sec:authorize>
 <c:if test="${not empty ops}">
                <ul aria-label="OPERACIONES:">
                    <c:forEach items="${ops}" var="op">
                        <li>${op}</li>
                    </c:forEach>
                </ul>
            </c:if>
<br>
    <h1 class="text-center">Customer Form</h1>
    <br>Context Path: <%= request.getContextPath() %>
    <f:form action="saveCustomer" modelAttribute="customer">
        <table class="table">
            <tr>
                <td class="label">Customer ID</td>
                <td><f:input type="text" class="form-control" readonly="true" path="id" value="${nextId}" /></td>
                <td><f:errors path="id"  cssClass="error"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Name</td>
                <td><f:input type="text" class="form-control" path="name" value="${c.getName()}" /></td>
                <td><f:errors path="name" cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Gender</td>
                <td>
                    <c:forEach items="${genders}" var="g">
                    	<f:radiobutton path="gender"  label="${g}" 
                    	value="${g}" checked="${c.getGender().equals(g)?'checked':''}" />
                       
                    </c:forEach>
                </td>
                <td><f:errors path="gender"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
            <td class="label">DOB</td><td><f:input type="date" class="form-control" path="dob" value="${c.getDob()}"/></td>
                <td><f:errors path="dob"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">Mobile</td><td><f:input type="text" class="form-control" path="mobile" value="${c.getMobile()}" /></td>
                <td><f:errors path="mobile" cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">Address Line1</td>
                <td><f:input type="text" class="form-control" path="address.line1" value="${c.address.line1}"/></td>
                <td><f:errors path="address.line1"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">Address Line2</td>
                <td><f:input type="text" class="form-control" path="address.line2" value="${c.getAddress().getLine2()}"/></td>
                <td><f:errors path="address.line2"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">City</td>
                <td><f:input type="text" class="form-control" path="address.city" value="${c.getAddress().getCity()}"/></td>
                <td><f:errors path="address.city"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">State</td>
                <td><f:input type="text" class="form-control" path="address.state" value="${c.getAddress().getState()}"/></td>
                <td><f:errors path="address.state"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">Country</td>
                <td><f:input type="text" class="form-control" path="address.country" value="${c.getAddress().getCountry()}"/></td>
                <td><f:errors path="address.country"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">ZIP</td>
                <td><f:input type="text" class="form-control" path="address.zip" value="${c.getAddress().getZip()}"/></td>
                <td><f:errors path="address.zip"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td class="label">SSN</td><td><f:input type="text" class="form-control" path="SSN" value="${c.getSSN()}"/></td>
                <td><f:errors path="SSN"  cssStyle="color:red;"></f:errors></td>
            </tr>
<tr>
    <td class="label">User</td>
    <td>
        <c:choose>
            <c:when test="${not empty users}">
                <c:forEach items="${users}" var="u">
                   	<f:radiobutton path="user" label="${u.username}" value="${u.id}" 
                     checked="${ u.getId()==c.getUser().getId() ? 'checked':'' }" />
                </c:forEach>
            </c:when>
            <c:otherwise>
                Wait for administration to create your user
            </c:otherwise>
        </c:choose>
    </td>
    <td><f:errors path="user" cssStyle="color:red;"></f:errors></td>
</tr>
<tr>            
<td colspan="3" align="center"><input type="submit" class="submit-button" value="Submit"/></td>
            </tr>
        </table>
    </f:form>

    <h1 class="text-center">List of Customers</h1>
    <c:if test="${not empty customers}">
        <table class="table">
            <tr>
                <td>Id</td><td>Name</td><td>Gender</td>
                <td>DOB</td><td>Mobile</td><td>Address</td>
                <td>SSN</td><td>Action</td>
            </tr>
            <c:forEach items="${customers}" var="cust">
                <tr>
                    <td>${cust.getId()}</td>
                    <td>${cust.getName()}</td>
                    <td>${cust.getGender()}</td>
                    <td>${cust.getDob()}</td>
                    <td>${cust.getMobile()}</td>
                    <td class="address-line">${cust.getAddress().readable()}</td>
                    <td>${cust.getSSN()}</td>
                    <td>
                        <a href="updateCustomer?id=${cust.getId()}">Update</a>
                        <sec:authorize access="hasAuthority('Admin')">
                        |<a href="deleteCustomer?id=${cust.getId()}">Delete</a>
                        </sec:authorize>
                    </td>
                    <td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>



