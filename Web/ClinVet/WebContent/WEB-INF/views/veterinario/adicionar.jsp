<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/veterinarios/adicionar"></c:url>
<h2>Adicionar novo Veterinário</h2>
<br />
<form:form action="${actionAdicionar}" method="post"
	modelAttribute="veterinario">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Veterinário:</label>
					<form:input path="nome_vet" cssClass="form-control"
						placeholder="nome-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="nome_vet" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Login nome:</label>
					<form:input path="username" cssClass="form-control"
						placeholder="login-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="username" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon2">Senha:</span>
					<form:password path="password" cssClass="form-control"
						placeholder="password" aria-describedby="basic-addon2" />
				</div>
				<form:errors path="password" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label>Perfil:</label> <select name="role" class="form-control">
					<option value="ROLE_USER">Veterinário</option>
				</select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Nº CRV:</label>
					<form:input path="crv" cssClass="form-control"
						placeholder="crv-veterinário" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="crv" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Especialidades:</label>
					<form:textarea path="especialidade" cssClass="form-control"
						placeholder="especialidade-veterinário"
						aria-describedby="basic-addon1" />
				</div>
				<form:errors path="especialidade" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<input type="submit" value="Salvar" class="btn btn-default" />
</form:form>
