<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png" href="https://wizarddojo.files.wordpress.com/2016/01/conkers-bad-fur-day-money.jpg">
<meta charset="UTF-8">
<title>AccountForm</title>
<style>
    body {
        background-color: #f8f9fa; /* Light gray background */
        font-family: Arial, sans-serif; /* Default font */
    }

    .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background-color: #fff; /* White background */
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Light shadow */
    }

    h1 {
        color: #007bff; /* Bank blue */
        text-align: center;
        margin-bottom: 20px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    table, th, td {
        border: 1px solid #dee2e6; /* Light gray border */
    }

    th, td {
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #007bff; /* Bank blue */
        color: #fff; /* White text */
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-label {
        font-weight: bold;
        color: #007bff; /* Bank blue */
    }

    .form-control {
        width: 100%;
        padding: 10px;
        border: 1px solid #ced4da; /* Light gray border */
        border-radius: 5px;
        background-color: #f8f9fa; /* Light gray background */
    }

    .form-control:focus {
        outline: none;
        border-color: #007bff; /* Bank blue */
    }

    .btn-primary {
        background-color: #007bff; /* Bank blue */
        color: #fff; /* White text */
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .btn-primary:hover {
        background-color: #0056b3; /* Darker blue */
    }

    .error {
        color: red; /* Red text for error messages */
    }
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>Account Form</h1>
    <f:form action="saveAccount" method="POST" modelAttribute="account">
        <div class="form-group">
            <label class="form-label" for="id">Id:</label>
            <f:input type="text" class="form-control" path="id" value="${acc.getId()}" />
            <f:errors path="id" cssClass="error" />
        </div>

       <div class="form-group">
    <label class="form-label">Branch:</label>
    <div>
        <c:forEach items="${branches}" var="branch">
            <c:if test="${acc.getBranch().getId().equals(branch.getId())}">
                <f:radiobutton path="branch" label="${branch.getName()}" value="${branch.getId()}" checked="checked" />
            </c:if>
            <c:if test="${!acc.getBranch().getId().equals(branch.getId())}">
                <f:radiobutton path="branch" label="${branch.getName()}" value="${branch.getId()}" />
            </c:if>
        </c:forEach>
    </div>
    <f:errors path="branch" cssClass="error-message" />
</div>

       <div class="form-group">
    <label class="form-label">Account Type:</label>
    <div>
        <c:forEach items="${accountTypes}" var="type">
            <c:if test="${acc.getAccountType().getId().equals(type.getId())}">
                <f:radiobutton path="accountType" label="${type}" value="${type}" checked="checked" />
            </c:if>
            <c:if test="${!acc.getAccountType().getId().equals(type.getId())}">
                <f:radiobutton path="accountType" label="${type}" value="${type}" />
            </c:if>
        </c:forEach>
    </div>
    <f:errors path="accountType" cssClass="error-message" />
</div>
        <div class="form-group">
            <label class="form-label" for="holder">Holder:</label>
            <f:input type="text" class="form-control" path="holder" value="${acc.getHolder()}" />
            <f:errors path="holder" cssClass="error" />
        </div>

        <div class="form-group">
            <label class="form-label" for="dateOpened">Date Opened:</label>
            <f:input type="date" class="form-control" path="dateOpened" value="${acc.getDateOpened()}" />
            <f:errors path="dateOpened" cssClass="error" />
        </div>

        <div class="form-group">
            <label class="form-label" for="balance">Balance:</label>
            <f:input type="text" class="form-control" path="balance" value="${acc.getBalance()}" />
            <f:errors path="balance" cssClass="error" />
        </div>

        <div class="form-group">
    <label class="form-label">Customer:</label>
    <div>
        <c:forEach items="${customers}" var="customer">
            <c:if test="${acc.getCustomer().getId().equals(customer.getId())}">
                <f:radiobutton path="customer" label="${customer.getName()}" value="${customer.getId()}" checked="checked" />
            </c:if>
            <c:if test="${!acc.getCustomer().getId().equals(customer.getId())}">
                <f:radiobutton path="customer" label="${customer.getName()}" value="${customer.getId()}" />
            </c:if>
        </c:forEach>
    </div>
    <f:errors path="customer" cssClass="error-message" />
</div>

        <div class="form-group" align="center">
            <input type="submit" class="btn-primary" value="Submit" />
        </div>
    </f:form>

    <h1>List of Accounts</h1>
    <c:if test="${not empty accounts}">
        <table>
            <tr>
                <th>Id</th>
                <th>Branch</th>
                <th>Account Type</th>
                <th>Holder</th>
                <th>Date Opened</th>
                <th>Balance</th>
                <th>Customer</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${accounts}" var="acc">
                <tr>
                    <td>${acc.getId()}</td>
                    <td>${acc.getBranch().getName()}</td>
                    <td>${acc.getAccountType()}</td>
                    <td>${acc.getHolder()}</td>
                    <td>${acc.getDateOpened()}</td>
                    <td>${acc.getBalance()}</td>
                    <td>${acc.getCustomer().getName()}</td>
                    <td>
                        <a href="updateAccount?id=${acc.getId()}">Update</a>
                        |
                        <a href="deleteAccount?id=${acc.getId()}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>