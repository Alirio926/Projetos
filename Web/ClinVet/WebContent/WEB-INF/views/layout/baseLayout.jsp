<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>   
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> <tiles:insertAttribute name="title" ignore="true"/> </title>

<script type="text/javascript" src="${ jqueryJS }" ></script>
<link href="${bootstrapCSS}" rel="stylesheet" />
<script type="text/javascript" src="${bootstrapJS }" ></script>

</head>
<body>
	<div style="width: 100%">
		<tiles:insertAttribute name="header" ignore="false" />
	</div>
	<div style="width: 100%; margin: 1%" >
		<tiles:insertAttribute name="body" ignore="false" />
	</div>
	<div style="width: 100%">
		<tiles:insertAttribute name="footer" ignore="false" />
	</div>
</body>
</html>