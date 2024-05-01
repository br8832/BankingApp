<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="https://cdn.donmai.us/original/0c/d8/0cd8adfa8b465ff181c8487ee28b9135.jpg">
    <meta charset="ISO-8859-1">
    <title>Role Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-image: url('https://us-tuna-sounds-images.voicemod.net/dfe00298-0efe-4ccb-a1c4-8b456326ccb2-1659033930989.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center center;
            color: aquamarine;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .nav-link {
            color: aquamarine;
        }

        .container {
            padding: 50px;
        }

        h1 {
            margin-bottom: 30px;
        }

        table {
            background-color: rgba(0, 0, 0, 0.5);
            color: #fff;
        }

        table th,
        table td {
            padding: 10px;
        }

        input[type="text"] {
            width: 100%;
            padding: 8px 10px;
            border: none;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            color: #fff;
            text-decoration: none;
            margin-right: 10px;
        }

        a:hover {
            text-decoration: underline;
        }

        /* New styles for side-by-side layout */
        .row {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .role-list,
        .role-form {
            flex: 0 0 48%; /* Each takes up 48% of the available space */
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
                        <li class="nav-item"><a class="nav-link" href="/account/">Account Form</a></li>
                        <li class="nav-item"><a class="nav-link" href="/branch/">Branch Form</a></li>
                        <li class="nav-item"><a class="nav-link" href="/customer/">Customer Form</a></li>
                        <li class="nav-item"><a class="nav-link" href="/role/">Role Form</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated">
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
    <br>

    <h1>Role Form</h1>

    <div class="row">
        <div class="role-list">
            <c:if test="${not empty ops}">
                <ul aria-label="OPERACIONES:">
                    <c:forEach items="${ops}" var="op">
                        <li>${op}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <h2>List of Roles</h2>

            <table border="1">
                <tr>
                    <th>Role Id</th>
                    <th>Role Name</th>
                    <th>Action</th>
                </tr>

                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td>${role.getId()}</td>
                        <td>${role.getName()}</td>

                        <td>
                            <a href="deleteRole?id=${role.getId()}">Delete</a>
                            |
                            <a href="updateRole?id=${role.getId()}">Update</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="role-form">
            <h2>Role Form</h2>

            <f:form action="saveRole" modelAttribute="role">
                <table border="1">
                    <tr>
                        <td>Id: </td>
                        <td>
                            <f:input path="id" readonly="true" value="${nextId}" />
                        </td>
                        <td><f:errors path="id"  cssStyle="color:purple;"></f:errors></td>
                    </tr>

                    <tr>
                        <td>Role Name:</td>
                        <td><f:input path="name" value="${r.getName()}"/></td>
                        <td><f:errors path="name"  cssStyle="color:purple;"></f:errors></td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="submit"/></td>
                    </tr>
                </table>
            </f:form>
        </div>
    </div>
</div>

</body>
</html>
