<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
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
        background-position: center center; /* Center the background image */
        color: #fff; /* White text */
        display: flex;
        justify-content: center; /* Center horizontally */
        align-items: center; /* Center vertically */
        height: 100vh; /* Full viewport height */
        }

        .container {
            padding: 50px;
        }

        h1 {
            color: #fff; /* White */
            margin-bottom: 30px;
        }

        table {
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background */
            color: #fff; /* White text */
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
            background-color: rgba(255, 255, 255, 0.1); /* Semi-transparent white background */
            color: #fff; /* White text */
        }

        input[type="submit"] {
            background-color: #007bff; /* Blue */
            color: #fff; /* White text */
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3; /* Dark blue */
        }

        a {
            color: #fff; /* White text */
            text-decoration: none;
            margin-right: 10px;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">

    <h1>Role Form</h1>

    <f:form action="saveRole" modelAttribute="role">
        <table border="1">
            <tr>
                <td>Id: </td>
                <td>
                    <f:input path="id" value="${r.getId()}" />
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

    <h1>List of Roles</h1>

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

</body>
</html>
