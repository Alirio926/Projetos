<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/animais/adicionar"></c:url>
<h2>Adicionar novo Animal</h2>
<br />
<form:form action="${actionAdicionar}" method="post"
	modelAttribute="animal">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Nome do Animal:</label>
					<form:input path="nome" cssClass="form-control"
						placeholder="nome-animal" aria-describedby="basic-addon1" />
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
						placeholder="nome-do-dono" aria-describedby="basic-addon1" />
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
					<input type="date" class="form-control" 
						placeholder="dd/mm/aaaa" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="dataNascimento" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<input type="submit" value="Salvar" class="btn btn-default" />
</form:form>
