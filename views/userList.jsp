<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>  

<html>
	<head>
		<meta charset = "UTF-8">
		<title>Lista Utenti</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link_button.css" rel="stylesheet" type="text/css"/>
	
	</head>
	<body>
		
		<jsp:include page = "_header.jsp"></jsp:include>

		
		<h3>Lista Utenti</h3>
		<a href = "ambientList">Indietro</a>
		<a href = "createUser">Crea Utente</a>
		
		<p style = "color:red;">${errorString}</p>

		<table id="table">
			<tr>
				<th>Nome Utente</th>
				<th>Privilegi</th>
				<th>Aggiungi Ambiente</th>
				<th>Modifica</th>
				<th>Elimina</th>	
			</tr>
			
			<c:forEach items = "${userList}" var = "utente">
				<tr>
					<td>${utente.userName}</td>
					<td>${utente.getPrivilegiStr()}</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/userList">
							<input type = hidden name = "way" value = 2 />
							<input type = hidden name = "username" value = "${utente.userName}" />
							<input type = submit value = "Aggiungi Ambiente" />
						</form>
					</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/userList">
							<input type = hidden name = "way" value = 0 />
							<input type = hidden name = "username" value = "${utente.userName}" />
							<input type = submit value = "Modifica" />
						</form>
					</td>
					<td>
						<form method = "POST" action = "${pageContext.request.contextPath}/userList">
							<input type = hidden name = "way" value = 1 />
							<input type = hidden name = "username" value = "${utente.userName}" />
							<input type = submit value = "Elimina" />
						</form>
					</td>
				</tr>
				
			</c:forEach>
		</table>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>