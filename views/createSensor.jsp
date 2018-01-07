<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<html>
<%--createSensor --%>
	<head>
		<meta charset = "UTF-8">
		<title>Creazione Sensore</title>
		
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
			<h3>Creazione Sensore</h3><a href = "sensorList">Indietro</a>
		</div>
		
		<p style = "color: red;">${errorString}</p>
		
		<form method = "POST" action = "${pageContext.request.contextPath}/createSensor">
			<table border = "0">
				<tr>
					<td>Marca</td>
					<td><input type = "text" name = "marca" value = "${ambiente.nome}" /></td>
				</tr>
				<tr>
					<td>Modello</td>
					<td><input type = "text" name = "modello" value = "${ambiente.ubicazione}" /></td>
				</tr>
				<tr>
					<td>Tipo</td>
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
					<td><input type = "date" name = "anno" value = "${ambiente.numeroSensori}" /></td>
				</tr>
				<tr>
					<td><input type = "submit" value = "Invia" /></td>
					<td><input type = "reset" value = "Cancella" /></td>
				</tr>
			</table>
		</form>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>