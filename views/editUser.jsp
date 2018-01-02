<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 

<html>
	<head>
		<meta charset = "UTF-8">
		<title>Modifica Utente</title>
		
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
			<h3>Modifica Utente</h3>
			<a href = "userList">Indietro</a>
		</div>
		
		<p style = "color:red;">${errorSring}</p>
		
		<c:if test = "${not empty user}">
			<form method = "POST" action = "${pageContext.request.contextPath}/editUser">
				
				<table border = "0">
					<tr>
						<td>Username</td>
						<td><input type = "text" name = "username" value = "${user.userName}" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type = "password" name = "password" value = "${user.password}" /></td>
					</tr>
					<tr>
						<td>Privilegi</td>
						<td>
							<select id = "selPriv" name = "selPriv">
								<option value = "0">Amministratore</option>
								<option value = "1">Standard</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><input type = "submit" value = "Invia" /></td>
						<td><input type = "reset" value = "Cancella" /></td>
					</tr>
				</table>
			</form>	
		</c:if>					
	
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>			
</html>