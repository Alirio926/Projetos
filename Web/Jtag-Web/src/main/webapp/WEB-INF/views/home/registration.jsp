<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/admin/registration"></c:url>

<div class="container">
	<form:form class="form-horizontal" action="${actionAdicionar}" method="post" modelAttribute="user">
		<div class="panel panel-info">
			
			<!-- Nome -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="name">Nome</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="name"
							path="name" placeholder="Nome do Usuário"></form:input>
					</div>
					<form:errors path="name" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Sobrenome -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="lastName">Sobrenome</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="lastName"
							path="lastName" placeholder="Sobrenome"></form:input>
					</div>
					<form:errors path="lastName" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Email -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="email">E-mail</label>
					<div class="col-md-3">
						<form:input class="form-control" type="email" id="email"
							path="email" placeholder="email"></form:input>
					</div>
					<form:errors path="email" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- User Name -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="userName">Login</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="userName"
							path="userName" placeholder="login"></form:input>
					</div>
					<form:errors path="userName" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Senha -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="userName">Senha</label>
					<div class="col-md-3">
						<form:input class="form-control" type="password" id="password"
							path="password" placeholder="Senha"></form:input>
					</div>
					<form:errors path="password" cssStyle="color: red"></form:errors>
				</div>
			</div>
					
		</div>
		<div class="panel-footer">
			<input type="submit" value="Salvar"
				class="btn btn-primary" />
		</div>
	</form:form>
</div> 
<!--container end.//-->