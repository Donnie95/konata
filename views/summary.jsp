<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>  

<html>
<%--summary --%>
	<head>
		<meta charset = "UTF-8">
		<title>Sintesi</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_form.css" rel="stylesheet" type="text/css"/>
		<style>
			input[type = submit] {
				padding: 5.5px 25px;
			}
		</style>
	</head>
	<body>
		
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<h3>Sintesi</h3>
		
		<table border = "0">
			<tr>
				<td><a href = "ambientList?id = ${ambiente.id}">Indietro</a></td>
				<td>
					<form method = "POST" action = "${pageContext.request.contextPath}/summary">
						<input type = "hidden" name = "way" value = 0 />
						<input type = "submit" value = "Crea PDF" />
					</form>
				</td>
			</tr>
		</table>
		<p style = "color:red;">${errorString}</p>
		
		<table id = table>
			<tr>
				<th>Marca</th>
				<th>Modello</th>
				<th>Messaggio</th>
				<th>Descrizione</th>
				<th>Data</th>
			</tr>
			
			<c:forEach items = "${summary}" var = "summary">
				<tr>
					<td>${summary.marca}</td>
					<td>${summary.modello}</td>
					<td>${summary.messaggio}</td>
					<td>${summary.descrizione}</td>
					<td>${summary.data}</td>
				</tr>
			</c:forEach>
		</table>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>