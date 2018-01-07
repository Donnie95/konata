<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<html>
<%--deleteAmbientError --%>
	<head>
		<meta charset = "UTF-8">
		<title>Elimina Ambiente</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<h3>Elimina Ambiente</h3>
		
		<p style = "color: red;">${errorString}</p>
		<a href = "ambientList">Lista Ambienti</a>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>