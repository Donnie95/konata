<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>  

<html>
	<head>
		<meta charset = "UTF-8">
		<title>Lista Ambienti</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
	
	</head>
	<body>
		
		<jsp:include page = "_header.jsp"></jsp:include>

		
		<h3>Lista Ambienti</h3>
		
		<p style = "color:red;">${errorString}</p>

		<table id="table">
			<tr>
				<th>Nome</th>
				<th>Tipologia</th>
				<th>Ubicazione</th>
				<th>NumeroSensori</th>
				<th>Sensori</th>
				<th>Sintesi</th>
			</tr>
			
			<c:forEach items = "${ambientList}" var = "ambiente">
				<tr>
					<td>${ambiente.nome}</td>
					<td>${ambiente.tipo}</td>
					<td>${ambiente.ubicazione}</td>
					<td>${ambiente.numeroSensori}</td>
					<td>
						<form id = sens method = "POST" action = "${pageContext.request.contextPath}/ambientList">
							<input type = hidden name = "way" value = 0 />
							<input type = hidden name = "ambID" value = "${ambiente.id}" />
							<input type = submit value = "Sensori" />
						</form>
					</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/ambientList">
							<input type = hidden name = "way" value = 1 />
							<input type = hidden name = "ambID" value = "${ambiente.id}" />
							<input type = submit value = "Sintesi" />
						</form>
					</td>
				</tr>
				
			</c:forEach>
		</table>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>