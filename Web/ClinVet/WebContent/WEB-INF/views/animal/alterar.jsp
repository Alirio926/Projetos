<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionEditar" value="/animais/alterar"></c:url>
<h2>Edição do animal "${animal.nome}"...</h2>
<br/>
<form:form action="${actionEditar}" method="post"
	modelAttribute="animal">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label>Id:</label>
				<form:input path="id" cssClass="form-control" readonly="true" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Nome do Animal:</label>
					<form:input path="nome" cssClass="form-control"
						placeholder="nome-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="nome" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Dono do animal</label>
					<form:input path="nomeDoDono" cssClass="form-control"
						placeholder="login-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="nomeDoDono" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Data de nascimento</label>
					<form:input path="dataNascimento" cssClass="form-control"
						placeholder="login-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="dataNascimento" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<input type="submit" value="Salvar" class="btn btn-default" />
</form:form>
