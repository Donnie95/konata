<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<html>
	<head>
		<meta charset = "UTF-8">
		<title>Modifica Sensore</title>
		
		<link href="${pageContext.request.contextPath}/css/style_form.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
		
		<style>
			select {
				display: block;
				padding: 4px 40px;
				margin: 8px 0;
				border: 1px solid #ccc;
				border-radius: 4px;
				box-sizing: border-box;
			}
		</style>	
	</head>
	
	<body>
		
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<div>
			<h3>Modifica Sensore</h3><a href = "sensorList">Indietro</a>
		</div>
		
		<p style = "color:red;">${errorSring}</p>
		
		<c:if test = "${not empty sensor}">
			<form method = "POST" action = "${pageContext.request.contextPath}/editSensor">
				
				<table border = "0">
					<tr>
						<td>Marca</td>
						<td><input type = "text" name = "marca" value = "${sensor.marca}" /></td>
					</tr>
					<tr>
						<td>Modello</td>
						<td><input type = "text" name = "modello" value = "${sensor.modello}" /></td>
					</tr>
					<tr>
						<td>Tipologia</td>
						<td>
							<select id = "selSens" name = "selSens">
								<option value = "1">Movimento</option>
								<option value = "2">Pressione</option>
								<option value = "3">Umidità</option>
								<option value = "4">Temperatura</option>
								<option value = "5">Luminosità</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Anno</td>
						<td><input type = "date" name = "anno" value = "${sensor.anno}" /></td>
					</tr>
					<tr>
						<td colspan = "2">
							<input type = "submit" value = "Invia" />
							<input type = "reset" value = "Cancella" />
						</td>
					</tr>
				</table>			
			</form>	
		</c:if>					
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>			
</html>