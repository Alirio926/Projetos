<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/pages/clientes/adicionar"></c:url>

<form:form class="form-horizontal" action="${actionAdicionar}" method="post" modelAttribute="cliente">
	<c:if test="${success != null}">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>Cadastrado com sucesso!!!
		</button>
	</c:if>
	<div class="panel panel-info">
       <div class="panel-heading"> Adicionar Cliente 
			<a href="/pages/clientes/listar" >
			<i class="glyphicon glyphicon-th-list" title="Listar Clientes" style="color:#f4511e"></i>	
			</a>
		</div>
		
		<div class="panel-body">
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-1 control-label" for="nome">Cliente</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="nome"
							path="nome" placeholder="Nome do Cliente"></form:input>
					</div>
					<form:errors path="nome" cssStyle="color: red"></form:errors>
				</div>
			</div>
						
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-1 control-label" for="rg">RG/CPF</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="rg"
							path="rg" placeholder="RG/CPF do Cliente"></form:input>
					</div>
					<form:errors path="rg" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-1 control-label" for="restricao">Com restrição</label>
					<div class="col-md-2">
							<form:select path="restricao" class="form-control" id="restricao">
								<option value="0">Não</option>
								<option value="1">Sim</option>
							</form:select>
					</div>
					<form:errors path="restricao" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
            <div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-1 control-label" for="motivo">Motivo</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="motivo"
							path="motivo" placeholder="Motivo da restição"></form:input>
					</div>
					<form:errors path="motivo" cssStyle="color: red"></form:errors>
				</div>
			</div>
            
            <!-- Data -->
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-1 control-label" for="dataCadastro">Data inicio</label>
					<div class="col-md-3">
						<form:input class="form-control" type="date" id="dataCadastro"
							path="dataCadastro" ></form:input>
					</div>
					<form:errors path="dataCadastro" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
		</div>
		</div>
		<div class="panel-footer">
			<input type="submit" value="Salvar"
				class="btn btn-primary" />
		</div>
<!-- 	<input type="submit" value="Salvar" class="btn btn-default" /> -->
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$('#telefone').mask('(00) 0000-00009');
	});
</script>