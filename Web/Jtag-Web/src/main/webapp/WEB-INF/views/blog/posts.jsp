<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    

<section>
<div class="container"  style="width: 60%; padding-bottom: 30px;">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb" style="background-color: white; padding-left: 0px;">
            <li class="breadcrumb-item active" aria-current="page">Posts</li>
        </ol>
    </nav>
	    <c:set var="lstPosts" value="${posts}" scope="session" />
	    <c:forEach var="post" items="${lstPosts}" >
	    	<div class="w3-panel w3-card-4" style="margin-top: 20px;"> 
	    		<div class="w3-container">
	    			<p class="card-subtitle mb-2 text-muted" style="font-size: 0.8rem;">
		                <span>${post.autor}</span>
		            </p>
		            <p class="card-subtitle mb-2 text-muted" style="font-size: 0.8rem;">
		                <span>${post.data}</span>
		            </p>
	    		</div>
    			<div class="w3-container">
		            <a href="${post.permalink}">
			            <h4 class="card-title" style="font-weight: bold; color: black; padding-top: 5px;">
			            	<span>${post.titulo}</span>
			            </h4>
		         	</a>
		            <p class="card-text"><span>${strings.abbreviate(post.texto,400)}</span></p>	
		        </div>	        
	    	</div>
    	</c:forEach>    
</div>
</section>