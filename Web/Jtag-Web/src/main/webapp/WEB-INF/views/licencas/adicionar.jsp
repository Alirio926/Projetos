<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/pages/licencas/adicionar"></c:url>

<form:form class="form-horizontal" action="${actionAdicionar}" method="post" modelAttribute="licenca">
	<c:if test="${success != null}">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>Cadastrado com sucesso!!!
		</button>
	</c:if>
	<div class="panel panel-info">
       <div class="panel-heading"> Adicionar Licença para ${nomeCliente}
			<a href="/pages/licencas/findby/${clienteId}" >
			<i class="glyphicon glyphicon-th-list" title="Listar Licença" style="color:#f4511e"></i>	
			</a>
		</div>
		
		<div class="panel-body">
		
			<!-- Cliente info -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="clienteId">Cliente Id</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="clienteId"
							path="clienteId" placeholder="Id do cliente" readonly="true"></form:input>
					</div>
					<form:errors path="clienteId" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Codigo da Licença -->
			<div class="col-md-12">
				<div class="form-group" align="left">

					<c:if test="${error != null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>

					<label class="col-md-2 control-label" for="license">Licença Code</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="license"
							path="license" placeholder="Codigo da licença"></form:input>
					</div>
					<form:errors path="license" cssStyle="color: red"></form:errors>
				</div>
			</div>
						
			<!-- Quant. Inicial -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="qtdInicial">Quant. Inicial</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="qtdInicial"
							path="qtdInicial" placeholder="Quant. Inicial"></form:input>
					</div>
					<form:errors path="qtdInicial" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Quant. disponível -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="qtdDisponivel">Quant. Dispinivel</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="qtdDisponivel"
							path="qtdDisponivel" placeholder="Quant. Dispinivel"></form:input>
					</div>
					<form:errors path="qtdDisponivel" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Status da licença -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="status">Status</label>
					<div class="col-md-2">
							<form:select path="status" class="form-control" id="status">
								<option value="OPEN">Aberta</option>
								<option value="CLOSE">Fechada</option>
								<option value="BLOQUED">Bloqueada</option>
							</form:select>
					</div>
					<form:errors path="status" cssStyle="color: red"></form:errors>
				</div>
			</div>			
			
			<!-- Quant. disponível -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="machineCode">Código da Maquina</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="machineCode"
							path="machineCode" placeholder="Código da Maquina"></form:input>
					</div>
					<form:errors path="machineCode" cssStyle="color: red"></form:errors>
				</div>
			</div>
			
			<!-- Produto -->	
			<div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="produto">Produto</label>
					<div class="col-md-2">
							<form:select path="produto" class="form-control" id="produto">
								<option value="SMS">Master System</option>
								<option value="NES">Nintendinho</option>
								<option value="MEGA">Mega Drive</option>
							</form:select>
					</div>
					<form:errors path="produto" cssStyle="color: red"></form:errors>
				</div>
			</div>	
			
			<!-- Valor da licença -->
            <div class="col-md-12">
				<div class="form-group" align="left">
					<label class="col-md-2 control-label" for="valor">Valor</label>
					<div class="col-md-3">
						<form:input class="form-control" type="text" id="valor"
							path="valor" placeholder="Valor da licença"></form:input>
					</div>
					<form:errors path="valor" cssStyle="color: red"></form:errors>
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