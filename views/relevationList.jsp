<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>  

<html>
	<head>
		<meta charset = "UTF-8">
		<title>Lista Rilevazioni</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
	 
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<div>
		
			<h3>Lista Rivelazioni</h3>
			<a href = "sensorList?id = ${sensori.id}">Indietro</a>
		
		</div>
		
		<p style = "color: red;">${errorString}</p>
		
		<table id = table>
			<tr>
				<th>Messaggio</th>
				<th>Descrizione</th>
				<th>Data</th>
			</tr>
			<c:forEach items = "${relevationList}" var = "relevation" >
				<tr>
					<td>${relevation.messaggio}</td>
					<td>${relevation.descrizione}</td>
					<td>${relevation.data}</td>
				</tr>
			</c:forEach>	
		</table>

		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>