<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png" href="https://i.redd.it/895o36c5f3m91.jpg">
<meta charset="UTF-8">
<title>TransactionForm</title>
<style>
/* Overidding the defualt boostrap body to change the color */
body.form-body {
    background-image: url('https://staticdelivery.nexusmods.com/mods/2823/images/headers/224_1639524154.jpg');
    background-size: cover;
    background-position: center;
    color: wheat; /* Wheat text color */
    margin: 0;
    font-family: var(--bs-body-font-family);
    font-size: var(--bs-body-font-size);
    font-weight: var(--bs-body-font-weight);
    line-height: var(--bs-body-line-height);
    text-align: var(--bs-body-text-align);
    -webkit-text-size-adjust: 100%;
    -webkit-tap-highlight-color: transparent;
}

.form-container {
    max-width: 600px;
    margin: 0 auto;
    background-color: rgba(255, 255, 255, 0.1); /* Semi-transparent white background */
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.error-message {
    color: red;
}

.form-label {
    font-weight: bold;
    font-size: 1.2rem;
    color: #ff69b4; /* Pink label color */
}

.form-control {
    background-color: rgba(255, 255, 255, 0.2); /* Semi-transparent white background */
    border: 1px solid #ff69b4; /* Pink border */
    color: #333333; /* Dark gray text color */
    border-radius: 5px; /* Rounded corners */
}

.btn-primary {
    background-color: #ff69b4; /* Pink button background */
    border-color: #ff69b4; /* Pink button border */
}

.btn-primary:hover {
    background-color: #ff1493; /* Darker pink background on hover */
    border-color: #ff1493; /* Darker pink border on hover */
}

.table th,
.table td {
    color: #333333; /* Dark gray text color */
}

</style>
<!-- Link Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="form-body">
<h1>Request Information</h1>
<div class="row">
    <div class="col-md-6">
        <ul>
            <li>Response Content Type: <%= (pageContext.getResponse().getContentType()) %></li>
            <li>Request Method: <%= ((HttpServletRequest) pageContext.getRequest()).getMethod() %></li>
            <li>Request ServletPath: <%= ((HttpServletRequest) pageContext.getRequest()).getServletPath() %></li>
            <li>Request AuthType: <%= ((HttpServletRequest) pageContext.getRequest()).getAuthType() %></li>
            <li>Request QueryString: <%= ((HttpServletRequest) pageContext.getRequest()).getQueryString() %></li>
            <li>Request PathInfo: <%= ((HttpServletRequest) pageContext.getRequest()).getPathInfo() %></li>
        </ul>
    </div>
    <div class="col-md-6">
        <ul>
        	<li>Response Headers: <%= ((HttpServletResponse) pageContext.getResponse()).getHeaderNames() %></li>
            <li>Status Code: <%= ((HttpServletResponse) pageContext.getResponse()).getStatus() %></li>
            <li>Request URI: <%= ((HttpServletRequest) pageContext.getRequest()).getRequestURI() %></li>
            <li>Request URL: <%= ((HttpServletRequest) pageContext.getRequest()).getRequestURL() %></li>
            <li>Remote Address: <%= ((HttpServletRequest) pageContext.getRequest()).getRemoteAddr() %></li>
            <!-- Add more request properties/methods here -->
        </ul>
    </div>
</div>

<div class="container form-container">
    <p>Context Path: ${pageContext.request}</p>
    <h1 class="text-center">
    ${t.getType()} Transaction Form</h1>
    <f:form action="saveTransaction" method="POST" modelAttribute="transaction">
        <div class="mb-3">
            <label for="id" class="form-label">ID</label>
            <f:input type="text" readonly="true" class="form-control" id="id" path="id" value="${nextId}" />
            <f:errors path="id" cssClass="error-message" />
        </div>
		
		<div style="display:${t.getType().toString().equals('DEPOSIT')?'none':'inherit'}" class="mb-3">
            <label for="fromAccount" class="form-label">From Account</label>
            <f:select path="fromAccount" class="form-select">
                <c:forEach items="${accounts}" var="a">
                    <f:option selected="${a.getId().equals(t.getFromAccount())}" value="${a.getId()}">${a.identifier()}</f:option>
                </c:forEach>
            </f:select>
            <f:errors path="fromAccount" cssClass="error-message" />
        </div>
        
        <div style="display:${t.getType().toString().equals('WITHDRAW')?'none':'inherit'}" class="mb-3">
            <label for="toAccount" class="form-label">To Account</label>
            <f:select path="toAccount" class="form-select">
                <c:forEach items="${accounts}" var="a">
                    <f:option selected="${a.getId().equals(t.getToAccount())}" value="${a.getId()}">${a.identifier()}</f:option>
                </c:forEach>
            </f:select>
            <f:errors path="toAccount" cssClass="error-message" />
        </div>

        <div class="mb-3">
            <label for="amount" class="form-label">Amount</label>
            <f:input type="text" class="form-control" id="amount" path="amount" value="${t.getAmount()}" />
            <f:errors path="amount" cssClass="error-message" />
        </div>

        <div class="mb-3">
            <label for="type" class="form-label">Transaction Type</label>
            <div>
                <c:forEach items="${transactionTypes}" var="type">
		<f:radiobutton path="type"  label="${type}" value="${type}" checked="${t.getType() == type ? 'checked' : ''}" />
                </c:forEach>
            </div>
            <f:errors path="type" cssClass="error-message" />
        </div>
		<div style="display:${t==null?'none':'inherit'}" class="mb-3">
    		<label for="date" class="form-label">Date</label>
    		<f:input type="datetime-local" class="form-control" id="date" path="date" 
    		value="${t.getDate()}" readonly="true" />
    		<f:errors path="date" cssClass="error-message" />
		</div>

        <div class="mb-3">
            <label for="comments" class="form-label">Comments</label>
            <f:input type="text" class="form-control" id="comments" path="comments" value="${t.getComments()}" />
            <f:errors path="comments" cssClass="error-message" />
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </f:form>
<h1 class="text-center mt-5">List of Transactions</h1>
<h1>List of Transactions</h1>

<!-- Date Filter Form -->
    <form action="filterTransactions" method="get">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate">
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate">
        <input type="submit" value="Filter">
    </form>

    <c:if test="${not empty transactions}">
        <table class="table mt-3">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>From Account</th>
                    <th>To Account</th>
                    <th>Amount</th>
                    <th>Transaction Type</th>
                    <th>Date</th>
                    <th>Comments</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transactions}" var="t">
                    <tr>
                        <td>${t.getId()}</td>
                        <c:set var="from" value="${accounts.stream().filter(a -> a.id == t.fromAccount).findFirst().orElse(null)}" />
                         <td>${from != null ? from.identifier() : ''}</td>
                        <c:set var="to" value="${accounts.stream().filter(a -> a.id == t.toAccount).findFirst().orElse(null)}" />
                         <td>${to != null ? to.identifier() : ''}</td>
                        <td>${t.getAmount()}</td>
                        <td>${t.getType()}</td>
                        <td>${t.getDate()}</td>
                        <td>${t.getComments()}</td>
                        <td>
                            <a href="updateTransaction?id=${t.getId()}&type=${t.getType()}" class="btn btn-sm btn-primary">Update</a>
                            <a style="pointer-events:none" href="deleteTransaction?id=${t.getId()}" class="btn btn-sm btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>