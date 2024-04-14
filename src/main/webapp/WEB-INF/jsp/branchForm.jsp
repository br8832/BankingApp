<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BranchForm</title>
<link rel="icon" type="image/png" href="https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Bandera_de_Espa%C3%B1a.svg/640px-Bandera_de_Espa%C3%B1a.svg.png">
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

.table {
    margin-top: 20px;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background for tables */
}

.table th, .table td {
    border: none; /* Remove border */
    padding: 8px;
    color: #ffffff; /* White text color for table cells */
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

.label {
    color: #ff7f50; /* Reddish-orange label color */
    font-weight: bold;
}
.header {
color: blanchedalmond;
}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 class="header text-center">Formulario de Sucursal</h1>
    <br>Contexto: <%= request.getContextPath() %>
    <f:form action="saveBranch" method="POST" modelAttribute="branch">
        <table class="table">
            <tr>
                <td>Errores</td>
                <td colspan="2"><c:if test="${hasErrors}">
                    <c:forEach items="${errors}" var="error">
                            <span class=myCss>${error.defaultMessage}</span><br>
                    </c:forEach>
                </c:if></td>
            </tr>

            <tr>
                <td class="label">Id</td><td><f:input type="text" class="form-control" path="id" value="${b.getId()}" /></td>
                <td><f:errors path="id"  cssClass="error"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Nombre</td><td><f:input type="text" class="form-control" path="name" value="${b.getName()}" /></td>
                <td><f:errors path="name" cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Dirección Línea 1</td>
                <td><f:input type="text" class="form-control" path="address.line1" value="${b.address.line1}"/></td>
                <td><f:errors path="address.line1"  cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Dirección Línea 2</td>
                <td><f:input type="text" class="form-control" path="address.line2" value="${b.getAddress().getLine2()}"/></td>
                <td><f:errors path="address.line2"  cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Ciudad</td>
                <td><f:input type="text" class="form-control" path="address.city" value="${b.getAddress().getCity()}"/></td>
                <td><f:errors path="address.city"  cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Estado</td>
                <td><f:input type="text" class="form-control" path="address.state" value="${b.getAddress().getState()}"/></td>
                <td><f:errors path="address.state"  cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">País</td>
                <td><f:input type="text" class="form-control" path="address.country" value="${b.getAddress().getCountry()}"/></td>
                <td><f:errors path="address.country"  cssStyle="color:red;"></f:errors></td>
            </tr>

            <tr>
                <td class="label">Código Postal</td>
                <td><f:input type="text" class="form-control" path="address.zip" value="${b.getAddress().getZip()}"/></td>
                <td><f:errors path="address.zip"  cssStyle="color:red;"></f:errors></td>
            </tr>
            <tr>
                <td colspan="3" align="center"><input type="submit" class="submit-button" value="Enviar"/></td>
            </tr>
        </table>
    </f:form>

    <h1 class="header text-center">Lista de Sucursales</h1>
    <c:if test="${not empty branches}">
        <table class="table">
            <tr>
                <td>Id</td>
                <td>Nombre</td>
                <td>Dirección</td>
                <td>Acción</td>
            </tr>
            <c:forEach items="${branches}" var="b">
                <tr>
                    <td>${b.getId()}</td>
                    <td>${b.getName()}</td>
                    <td>${b.getAddress()}</td>
                    <td>
                        <a href="updateBranch?id=${b.getId()}">Actualizar</a>
                        |
                        <a href="deleteBranch?id=${b.getId()}">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>




