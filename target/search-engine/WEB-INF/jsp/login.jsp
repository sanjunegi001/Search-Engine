<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="css/app.css" />" rel="stylesheet"
	type="text/css">
<link href="<c:url value="css/core/bootstrap.min.css" />" rel="stylesheet"
	type="text/css">
<title>AuthBridge Login</title>
</head>
<body >
	
	
	<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to continue to AuthBridge</h1>
            <div class="account-wall">
               <div class="logo"></div>
               <form action="./login" method="post" class="form-signin">
                <input type="text" class="form-control" name="username" placeholder="UserName" required autofocus>
                <input type="password" class="form-control" name="password" placeholder="Password" required>
                <input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Sign in</button>
                
               <span class="clearfix"></span>
                </form>
                <c:if test="${param.error ne null}">
				<div class="alert-danger">Invalid Username or password.</div>
				</c:if>
				<c:if test="${param.logout ne null}">
					<div class="alert-normal">You have been logged out.</div>
				</c:if>
            </div>
           
        </div>
    </div>
</div>

</body>
</html>