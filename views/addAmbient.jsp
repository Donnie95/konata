<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<html>
<%--addAmbient --%>
	<head>
		<meta charset = "UTF-8">
		<title>Aggiungi Ambiente</title>
		
		<link href="${pageContext.request.contextPath}/css/style_form.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
		
		<style>
			select {
				display: block;
				padding: 4px 45px;
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
			<h3>Aggiungi Ambiente</h3><a href = "userList">Indietro</a>
		</div>
		
		<p style = "color: red;">${errorString}</p>
		
		<form method = "POST" action = "${pageContext.request.contextPath}/addAmbient">
		
			<table border = "0">
				<tr>
					<td>Seleziona l'ambiente da aggiungere</td>
					<td>
						<select id = "selAmb" name = "selAmb">
							<c:forEach items = "${ambientList}" var = "ambiente">
								<option value = "${ambiente.id}">${ambiente.nome}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan = "2">
						<input type = "submit" value = "Invia" />
						<input type = "reset" value = "Cancella" />
					</td>
				</tr>
			</table>
		</form>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>