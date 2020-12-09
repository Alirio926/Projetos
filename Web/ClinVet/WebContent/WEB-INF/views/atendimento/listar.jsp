<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<h2>Listagem de prontuarios</h2>
<br/>
<div class="row">
	<div class="col-md-2">
		<div class="form-group">
			<label>Animal a ser pesquisado:</label>
			<input type="text" id="txt-pesquisa" class="form-control" >
		</div>
		<button class="btn btn-default" id="btn-pesquisa">Pesquisar</button>
	</div>
</div>
<table class="table" id="tbl-Pront">
	<thead>
		<th>ID</th>
		<th>Nome do animal</th>
		<th>Nome do veterinario</th>
		<th>Data de atendimento</th>
		<th>Observações</th>	
		<th>Ações</th>	
	</thead>
	<tbody>
		<c:if test="${!empty prontuarios }">
			<c:forEach items="${prontuarios}" var ="prontuario">
				<tr>
					<td>${prontuario.id}</td>
					<td>${prontuario.animal.nome}</td>
					<td>${prontuario.veterinario.nome_vet}</td>
					<td><fmt:formatDate value="${prontuario.dataAtendimento}" pattern="dd/MM/yyyy" timeZone="UTC"/></td>
					<td>${prontuario.observacao}</td>
					<td>
						<a href="/squallsoft-clinvet/atendimentos/alterar/${prontuario.id}">Alterar</a> |
						<a href="/squallsoft-clinvet/atendimentos/excluir/${prontuario.id}">Excluir</a>
					</td>
				</tr>	
			</c:forEach>
		</c:if>			
	</tbody>
</table>
<br/>
<sec:authorize access="hasRole('ROLE_USER')">
<a href="/squallsoft-clinvet/prontuarios/adicionar" class="btn btn-default">Adicionar novo prontuario</a>
</sec:authorize>
<script type="text/javascript">
	$(document).ready(function(){
		$('#btn-pesquisa').click(function(){
			var observacao = escape($('#txt-pesquisa').val());
			$.ajax({
				method: 'GET',
				url: '/squallsoft-clinvet/atendimentos/porNome?observacao=' + observacao,
				success: function(data){
					$('#tbl-Pront tbody > tr').remove();	
					console.log(data);
					$.each(data, function(index, prontuarios){
						$('#tbl-Pront tbody').append(
							'<tr>'+
							'	<td>'+prontuarios.id+'</td>'+
							'	<td>'+prontuarios.animal.nome+'</td>'+
							'	<td>'+prontuarios.veterinario.nome_vet+'</td>'+
							'	<td>'+prontuarios.dataAtendimento+'</td>'+
							'	<td>'+prontuarios.observacao+'</td>'+
							'	<td>'+
							'		<a href="/squallsoft-clinvet/atendimentos/alterar/'+ prontuarios.id +'">Alterar</a> |' +
							'		<a href="/squallsoft-clinvet/atendimentos/excluir/'+ prontuarios.id +'">Excluir</a>' +
							' 	</td>'+
							'</tr>'
						);
					});
				},
				error: function(){
					alert("Houve um erro na requisição.\n"+request.responseText);
				}
			});
		});
	});
</script>