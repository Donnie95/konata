<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<head>
	<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
</head>
<div style = "background: #4caf50; padding: 5px;">   
	<div style = "float: left; font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">
		<h1>Sensor Manager</h1>
	</div>
	
	<div style = "float: right; padding: 10px; text-align: right;">
	
		<!-- User store in session with attribute: loginUser -->
		Ciao <b>${loginedUser.userName}</b>
		<a href = "logout">Logout</a>
		<br/>
	
	</div>
</div>