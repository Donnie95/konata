
<html>
	<head>
		<meta charset = "UTF-8">
		<title>Create PDF</title>
		
		<link href="${pageContext.request.contextPath}/css/style_list.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/style_link.css" rel="stylesheet" type="text/css"/>
	</head>
	
	<body>
		<jsp:include page = "_header.jsp"></jsp:include>
		
		<h3>Create PDF</h3>
		
		<p style = "color: red;">${errorString}</p>
		<a href = "summery">Sintesi</a>
		
		<jsp:include page = "_footer.jsp"></jsp:include>
	</body>
</html>