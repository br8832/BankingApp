<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="icon" type="image/jpeg" href="https://static-cdn.jtvnw.net/cf_vods/d2nvs31859zcd8/dca2199915974b6d40ec_illojuan_42247398840_1713892073/thumb/custom-5c1e3427-fc5e-4346-9df1-dd2d458f481f-320x180.jpeg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa; /* Light gray */
        }

        .navbar {
            background-color: #007bff; /* Blue */
        }

        .navbar-brand {
            color: white;
            font-size: 24px;
        }

        .navbar-nav {
            list-style-type: none;
            text-align: center;
        }

        .navbar-nav .nav-item {
            margin-right: 20px;
        }

        .navbar-nav .nav-link {
            color: white;
            font-size: 18px;
            transition: color 0.3s ease;
        }

        .navbar-nav .nav-link:hover {
            color: #cceeff; /* Light blue */
        }

        .welcome {
            margin-top: 50px;
            text-align: center;
        }

        .welcome h1 {
            font-size: 36px;
            color: #007bff; /* Blue */
        }

        .welcome p {
            font-size: 18px;
            color: #333; /* Dark gray */
            margin-bottom: 20px;
        }

        .login-link {
            font-size: 18px;
            color: #007bff; /* Blue */
            transition: color 0.3s ease;
        }

        .login-link:hover {
            color: #0056b3; /* Dark blue */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <sec:authorize access="hasAuthority('Admin')">
                    <li class="nav-item"><a class="nav-link" href="/account/">Account Form</a></li>
                    <li class="nav-item"><a class="nav-link" href="/branch/">Branch Form</a></li>
      
                    <li class="nav-item"><a class="nav-link" href="/role/">Role Form</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated">
                <li class="nav-item"><a class="nav-link" href="/pagedUser?pageNo=0&pageSize=5&sortedBy=username">Paged</a></li>
                <li class="nav-item"><a class="nav-link" href="/customer/">Customer Form</a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/?pageNo=0">User Form</a></li>
                    <li class="nav-item"><a class="nav-link" href="/transaction/">Transaction Form</a></li>
                </sec:authorize>
                <li class="nav-item"><a class="nav-link" href="home">Home</a></li>
                <sec:authorize access="isAuthenticated">
                    <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome">
        <h1>Welcome</h1>
        <p>
            <sec:authorize access="isAuthenticated">
                Hello <strong><sec:authentication property="principal.username"/>!
                <sec:authentication property="principal.authorities"/></strong>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated">
                Please <a href="login" class="login-link">login</a> to continue.
            </sec:authorize>
        </p>
    </div>
    <%-- <audio id="background-audio" loop controls preload="auto" style="display: none;">
        <source type="audio/mpeg" src="<c:url value="/mp3/OG's, ESTAFA PIRAMIDAL.mp3" />">
    </audio> --%>
   
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- <script>
    $(document).ready(function() {
    	var audio = document.getElementById("background-audio");
        audio.play();    
        setTimeout(()=>{
        	audio.style.display="block";
        },15000)
    });
</script>  -->
</body>
</html>
   
   


