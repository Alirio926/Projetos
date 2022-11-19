
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<spring:url value="/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/css/bootstrap.min.css" var="bootstrapCSS" />	
	<spring:url value="/js/bootstrap.min.js" var="bootstrapJS" />
	
	<!--Import Google Icon Font-->
	<!--     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"/> -->
		
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title> <tiles:insertAttribute name="title" ignore="true"/> </title>
	
	<script type="text/javascript" src="${ jqueryJS }" ></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script type="text/javascript" src="${bootstrapJS }" ></script>
	
	<style type="text/css">
		
		.hiddenRow {
		    padding: 0 !important;
		}
		html, body {
			background-image: url("https://p0.pxfuel.com/preview/443/99/669/christmas-snow-winter-christmas-time.jpg");
  			background-size: cover;
			height: 96%;
			margin: 0.5%;
		}
		
		.wrapper {
			min-height: 100%;
			/* Equal to height of footer */
			/* But also accounting for potential margin-bottom of last child */
			margin-bottom: -50px;
		}
		
		.footer, .push {
			height: 50px;
		}
		
		#mainFooter {
			font-family: Verdana, sans-serif;
			color: grey;
			font-size: 10px;
			font-weight: bolder;
		}
		.logo-small {
		    color: #f4511e;
		    font-size: 50px;
		}
		.cor-icone {
		    color:#ff922b !important;
		}
		
		
		#login-dp{
		    min-width: 250px;
		    padding: 14px 14px 0;
		    overflow:hidden;
		    background-color:rgba(255,255,255,.8);
		}
		#login-dp .help-block{
		    font-size:12px    
		}
		#login-dp .bottom{
		    background-color:rgba(255,255,255,.8);
		    border-top:1px solid #ddd;
		    clear:both;
		    padding:14px;
		}
		#login-dp .social-buttons{
		    margin:12px 0    
		}
		#login-dp .social-buttons a{
		    width: 49%;
		}
		#login-dp .form-group {
		    margin-bottom: 10px;
		}
		.btn-fb{
		    color: #fff;
		    background-color:#3b5998;
		}
		.btn-fb:hover{
		    color: #fff;
		    background-color:#496ebc 
		}
		.btn-tw{
		    color: #fff;
		    background-color:#55acee;
		}
		.btn-tw:hover{
		    color: #fff;
		    background-color:#59b5fa;
		}
		@media(max-width:768px){
		    #login-dp{
		        background-color: inherit;
		        color: #fff;
		    }
		    #login-dp .bottom{
		        background-color: inherit;
		        border-top:0 none;
		    }
		}
	</style>
</head>

<body>
	<div class="wrapper">
		<div class="container"">
			<tiles:insertAttribute name="header" />

			<tiles:insertAttribute name="body" />
			<div class="push"></div>
		</div>
	</div>

	<tiles:insertAttribute name="footer" />
</body>
</html>