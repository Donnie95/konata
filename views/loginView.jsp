<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
 
<html>
<%--loginView --%>
	<head>
		<meta charset = "UTF-8">
		<title>Login</title>

		<link href="${pageContext.request.contextPath}/css/style_form.css" rel="stylesheet" type="text/css"/>
		
		<style>
			#rm {
				display: block;
			}
		</style>
	</head>
	
	

	<body>
	
		<jsp:include page = "_header.jsp"></jsp:include>
		

		<h3>Login</h3>
		
		<p style = "color: red;">${errorString}</p>

		<form method = "POST" action = "${pageContext.request.contextPath}/login">
		
			<label for = "user">Username</label>
			<input type = "text" id = "user" name = "userName" value = "${user.userName}" />
							
			<label for = "pwd">Password</label>
			<input type = "password" id = "pwd" name = "password" value = "${user.password}" />
				
			<label for = "rm">Remember Me</label>
			<input type = "checkbox" id = "rm" name = "rememberMe" value = "Y" />

			<input type = "submit" value = "Invia" />
			<input type = "reset" value="Cancella">
		</form>
		
		<jsp:include page = "_footer.jsp"></jsp:include>

	</body>
</html>	

