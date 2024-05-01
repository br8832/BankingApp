<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario de Sucursal</title>
    <link rel="icon" type="image/png" href="https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Bandera_de_Espa%C3%B1a.svg/640px-Bandera_de_Espa%C3%B1a.svg.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-image: url('https://miro.medium.com/v2/resize:fit:1400/1*j8XIu7b8DiD9ZoGj47odZA.png');
            background-size: cover;
            background-position: center;
            color: #333333; /* Dark gray text color */
        }

        .container {
            margin-top: 50px;
        }

        h1 {
            color: #ffce00; /* Yellow heading */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .form-control {
            background-color: rgba(255, 255, 255, 0.2); /* Semi-transparent white background */
            border: none; /* Remove border */
            border-bottom: 1px solid #ffffff; /* White bottom border */
            color: #333333; /* Dark gray text color */
        }

        .submit-button {
            background-color: #ffce00; /* Yellow button background */
            color: #333333; /* Dark gray text color */
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }

        .submit-button:hover {
            background-color: #ffbf00; /* Darker yellow background on hover */
        }

        .label{
            color: #ff7f50; /* Reddish-orange label color */
            font-weight: bold;
        }

        .form-check-input[type="checkbox"] {
            width: 1.25rem; /* Set width of the checkbox */
            height: 1.25rem; /* Set height of the checkbox */
            margin-top: 0.25rem; /* Adjust margin top */
            margin-right: 0.5rem; /* Adjust margin right */
        }

        .form-check-label {
            margin-top: 0.25rem; /* Adjust margin top */
            color: aliceblue
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
<c:if test="${not empty ops}">
                <ul aria-label="OPERACIONES:">
                    <c:forEach items="${ops}" var="op">
                        <li>${op}</li>
                    </c:forEach>
                </ul>
            </c:if>
<br>
    <h1 class="text-center">Formulario de Sucursal</h1>
    <br>Contexto: <%= request.getContextPath() %>
    <f:form action="saveBranch" method="POST" modelAttribute="branch">
        <div class="mb-3">
            <label for="id" class="form-label label">Id</label>
            <f:input type="text" readonly="true" class="form-control" id="id" path="id" value="${siguiente}" />
            <f:errors path="id" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="name" class="form-label label">Nombre</label>
            <f:input type="text" class="form-control" id="name" path="name" value="${s.getName()}" />
            <f:errors path="name" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="line1" class="form-label label">Dirección Línea 1</label>
            <f:input type="text" class="form-control" id="line1" path="address.line1" value="${s.address.line1}" />
            <f:errors path="address.line1" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="line2" class="form-label label">Dirección Línea 2</label>
            <f:input type="text" class="form-control" id="line2" path="address.line2" value="${s.getAddress().getLine2()}" />
            <f:errors path="address.line2" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="city" class="form-label label">Ciudad</label>
            <f:input type="text" class="form-control" id="city" path="address.city" value="${s.getAddress().getCity()}" />
            <f:errors path="address.city" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="state" class="form-label label">Estado</label>
            <f:input type="text" class="form-control" id="state" path="address.state" value="${s.getAddress().getState()}" />
            <f:errors path="address.state" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="country" class="form-label label">País</label>
            <f:input type="text" class="form-control" id="country" path="address.country" value="${s.getAddress().getCountry()}" />
            <f:errors path="address.country" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label for="zip" class="form-label label">Código Postal</label>
            <f:input type="text" class="form-control" id="zip" path="address.zip" value="${s.getAddress().getZip()}" />
            <f:errors path="address.zip" cssClass="error"></f:errors>
        </div>

        <div class="mb-3">
            <label class="form-label label">Cuentas</label>
            <div>
                <c:forEach items="${cuentas}" var="c">
                    <div class="form-check form-check-inline">
                        <f:checkbox class="form-check-input" path="accounts" value="${c.getId()}" checked="${c.getBranch().getId().equals(s.getId())?'checked':''}"/>
                        <label class="form-check-label">${c.identifier()}</label>
                    </div>
                </c:forEach>
                <f:errors path="accounts" cssClass="error"></f:errors>
            </div>
        </div>

        <div class="mb-3">
            <input type="submit" class="submit-button" value="Enviar"/>
        </div>
    </f:form>

    <h1 class="text-center">Lista de Sucursales</h1>
    <c:if test="${not empty sucursales}">
        <div class="mb-3">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre</th>
                        <th>Dirección</th>
                        <th>Cuentas</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${sucursales}" var="s">
                        <tr>
                            <td>${s.getId()}</td>
                            <td>${s.getName()}</td>
                            <td>${s.getAddress()}</td>
                            <td>${s.getAccounts()}</td>
                            <td>
                                <a href="updateBranch?id=${s.getId()}">Actualizar</a> |
                                <a href="deleteBranch?id=${s.getId()}">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>




