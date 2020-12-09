<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/atendimentos/adicionar"></c:url>
<h2>Adicionar Prontuario de atendimento</h2>
<br />
<form:form action="${actionAdicionar}" method="post"
	modelAttribute="prontuario">	
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label>Veterinário:</label>
					<form:select path="veterinario.id" cssClass="form-control">
						<form:options items="${veterinarios}" itemLabel="nome_vet" itemValue="id" />
					</form:select>
				</div>				
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label>Animal:</label>
					<form:select path="animal.id" cssClass="form-control">
						<form:options items="${animais}" itemLabel="nome" itemValue="id" />
					</form:select>
				</div>				
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Data de atendimento:</label>
					<form:input path="dataAtendimento" cssClass="form-control"
						placeholder="data-de-atendimento" aria-describedby="basic-addon1" />
				</div>
				<form:errors path="dataAtendimento" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<div class="input-group">
					<label class="control-label " for="name">Observação:</label>
					<form:textarea path="observacao" cssClass="form-control"
						placeholder="observações-atendimento"
						aria-describedby="basic-addon1" />
				</div>
				<form:errors path="observacao" cssStyle="color: red"></form:errors>
			</div>
		</div>
	</div>

	<input type="submit" value="Salvar" class="btn btn-default" />
</form:form>
