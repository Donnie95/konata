<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>  

<html>
<%--sensorListAdmin --%>
	<head>
		<meta charset = "UTF-8">
		<title>Lista Sensori</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
	 
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<div>
		
		<h3>Lista Sensori</h3>
		<a href = "ambientList">Indietro</a>
		<a href = "createSensor" >Crea Sensore</a>
		
		</div>
		
		<p style = "color: red;">${errorString}</p>
			
		<table id = table>
			<tr>
				<th>Modello</th>
				<th>Marca</th>
				<th>Tipologia</th>
				<th>Anno</th>
				<th>Rilevazioni</th>
				<th>Modifica</th>
				<th>Elimina</th>
			</tr>
			
			<c:forEach items = "${sensorList}" var = "sensor" >
				<tr>
					<td>${sensor.modello}</td>
					<td>${sensor.marca}</td>
					<td>${sensor.tipo}</td>
					<td>${sensor.anno}</td>
					
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/sensorList">
							<input type = "hidden" name = "way" value = "0" /> 
							<input type = "hidden" name = "sensID" value = "${sensor.id}" />
							<input type = "submit" value = "Rilevazioni" />
						</form>
					</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/sensorList">
							<input type = "hidden" name = "way" value = "1" />
							<input type = "hidden" name = "sensID" value = "${sensor.id}" />
							<input type = "submit" value = "Modifica" />
						</form>
					</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/sensorList">
							<input type = "hidden" name = "way" value = "2" />
							<input type = "hidden" name = "sensID" value = "${sensor.id}" />
							<input type = "submit" value = "Elimina" />
						</form>
					</td>
				</tr>
			</c:forEach>	
		</table>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>