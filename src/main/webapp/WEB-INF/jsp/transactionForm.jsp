<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
ul:before{
    content:attr(aria-label);
    font-size:120%;
    font-weight:bold;
    margin-left:-15px;
}

</style>
<!-- Link Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="form-body">
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
                        <li class="nav-item"><a class="nav-link" href="pagedUser?pageNo=1&pageSize=5&sortedBy=username">Paged-User Form</a></li>
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
    <p>Context Path: ${pageContext.request.userPrincipal}</p>
    <h1 class="text-center">
    ${t.getType()} Transaction Form</h1>
    <c:if test="${not empty ops}">
                <ul aria-label="OPERACIONES:">
                    <c:forEach items="${ops}" var="op">
                        <li>${op}</li>
                    </c:forEach>
                </ul>
            </c:if>
    <f:form action="saveTransaction" method="POST" modelAttribute="transaction">
        <div class="mb-3">
            <label for="id" class="form-label">ID</label>
            <f:input type="text" readonly="true" class="form-control" id="id" path="id" value="${transaction.getId() != null ? t.getId() : nextId }" />
            <f:errors path="id" cssClass="error-message" />
        </div>
		
		<div id="fromAccountDiv" style="display:${not empty transaction  && transaction.getType()=='WITHDRAW' || transaction.getType()=='TRANSFER'? 'block' : 'none'}" class="mb-3">
            <label for="fromAccount" class="form-label">From Account</label>
            <f:select path="fromAccount" class="form-select">
                <c:forEach items="${fromAccounts}" var="a">
                    <f:option selected="${a.getId().equals(t.getFromAccount())}" value="${a.getId()}">${a.identifier()}</f:option>
                </c:forEach>
            </f:select>
            <f:errors path="fromAccount" cssClass="error-message" />
        </div>
        
        <div id="toAccountDiv" style="display:${not empty transaction && transaction.getType()=='DEPOSIT' || transaction.getType()=='TRANSFER' ? 'block' : 'none'}" class="mb-3">
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
		<f:radiobutton path="type"  onchange="toggleAccountFields('${type}')" label="${type}" value="${type}" checked="${t.getType() == type ? 'checked' : ''}" />
                </c:forEach>
            </div>
            <f:errors path="type" cssClass="error-message" />
            <script>
    function toggleAccountFields(type) {
        var fromAccountDiv = document.getElementById("fromAccountDiv");
        var toAccountDiv = document.getElementById("toAccountDiv");

        if (type === "DEPOSIT") {
            fromAccountDiv.style.display = "none";
            toAccountDiv.style.display = "inherit";
        } else if (type === "WITHDRAW") {
            fromAccountDiv.style.display = "inherit";
            toAccountDiv.style.display = "none";
        } else {
            fromAccountDiv.style.display = "inherit";
            toAccountDiv.style.display = "inherit";
        }
    }
</script>
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
        <input type="date" value="${startDate}" id="startDate" name="startDate">
        <label for="endDate">End Date:</label>
        <input type="date" value="${endDate}" id="endDate" name="endDate">
        <input type="submit" value="Filter">
    </form>

    <c:if test="${not empty transactions}">
        <table class="table mt-3">
            <thead>
                <tr>
                    <th><a href="findAll?sortBy=id">ID</a></th>
                    <th>From Account</th>
                    <th>To Account</th>
                    <th><a href="findAll?sortBy=amount">Amount</a></th>
                    <th>Transaction Type</th>
                    <th><a href="findAll?sortBy=date">Date</a></th>
                    <th>Comments</th>
                    <sec:authorize access="hasAuthority('Admin')"><th>Action</th></sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transactions}" var="t">
                    <tr>
                        <td>${t.getId()}</td>
                        <c:set var="from" value="${accounts.stream().filter(a -> a.id == t.fromAccount).findFirst().orElse(null)}" />
    					<td>
    					<c:if test="${from != null}">
    					${from.identifier()} ${String.valueOf(from.getBalance())} 
    					</c:if>
    					</td>
                        <c:set var="to" value="${accounts.stream().filter(a -> a.id == t.toAccount).findFirst().orElse(null)}" />
                         	<td>${to != null ? to.identifier() : ''}</td>
                        <td>${t.getAmount()}</td>
                        <td>${t.getType()}</td>
                        <td>${t.getDate()}</td>
                        <td>${t.getComments()}</td>
                        <td>
                        	<sec:authorize access="hasAuthority('Admin')">
                            <a href="updateTransaction?id=${t.getId()}&type=${t.getType()}" class="btn btn-sm btn-primary">Update</a>
                            <a style="pointer-events:none" href="deleteTransaction?id=${t.getId()}" class="btn btn-sm btn-danger">Delete</a>
                            </sec:authorize>
                        </td>
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
    </c:if>
</div>
</body>
</html>