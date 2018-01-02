
<html>
	<head>
		<meta charset = "UFT-8">
		<title>Elimina Sensore</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<h3>Elimina Sensore</h3>
		
		<p style = "color: red;">${errorString}</p>
		<a href = "sensorList">Lista Sensori</a>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>