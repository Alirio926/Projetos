<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<h2>Listagem de veterinários</h2>
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
<table class="table" id="tbl-vet">
	<thead>
		<th>ID</th>
		<th>Nome do veterinário</th>
		<th>CRV</th>
		<th>Especialidade</th>
		<th>Ações</th>
	</thead>
	<tbody>
		<c:if test="${!empty veterinario }">
			<c:forEach items="${veterinario}" var ="veterinario">
				<tr>
					<td>${veterinario.id}</td>
					<td>${veterinario.nome_vet}</td>
					<td>${veterinario.crv}</td>
					<td>${veterinario.especialidade}</td>
					<td>
						<a href="/squallsoft-clinvet/veterinarios/alterar//${veterinario.id}">Alterar</a> |
						<a href="/squallsoft-clinvet/veterinarios/excluir/${veterinario.id}">Excluir</a>
					</td>
				</tr>	
			</c:forEach>
		</c:if>			
	</tbody>
</table>
<br/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<a href="/squallsoft-clinvet/veterinarios/adicionar" class="btn btn-default">Adicionar novo veterinário</a>
</sec:authorize>
<script type="text/javascript">
	$(document).ready(function(){
		$('#btn-pesquisa').click(function(){
			var nomeAlbum = escape($('#txt-pesquisa').val());
			$.ajax({
				method: 'GET',
				url: '/squallsoft-clinvet/veterinarios/porNome?nome_vet=' + nomeAlbum,
				success: function(data){
					$('#tbl-vet tbody > tr').remove();										
					$.each(data, function(index, veterinario){
						$('#tbl-vet tbody').append(
							'<tr>'+
							'	<td>'+veterinario.id+'</td>'+
							'	<td>'+veterinario.nome_vet+'</td>'+
							'	<td>'+veterinario.crv+'</td>'+
							'	<td>'+veterinario.especialidade+'</td>'+
							'	<td>'+ 
							'		<a href="/squallsoft-clinvet/veterinarios/alterar/'+ veterinario.id +'">Alterar</a> |' +
							'		<a href="/squallsoft-clinvet/veterinarios/excluir/'+ veterinario.id +'">Excluir</a>' +
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