<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<h2>Listagem de animais</h2>
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
<table class="table" id="tbl-animais">
	<thead>
		<th>ID</th>
		<th>Nome do animal</th>
		<th>Nome do dono</th>
		<th>Data de nascimento</th>		
		<th>Ações</th>
	</thead>
	<tbody>
		<c:if test="${!empty animais }">
			<c:forEach items="${animais}" var ="animal">
				<tr>
					<td>${animal.id}</td>
					<td>${animal.nome}</td>
					<td>${animal.nomeDoDono}</td>
					<td><fmt:formatDate value="${animal.dataNascimento}" pattern="dd/MM/yyyy" timeZone="UTC"/></td>
					<td>
						<a href="/squallsoft-clinvet/animais/alterar/${animal.id}">Alterar</a> |
						<a href="/squallsoft-clinvet/animais/excluir/${animal.id}">Excluir</a>
					</td>
				</tr>	
			</c:forEach>
		</c:if>			
	</tbody>
</table>
<br/>
<sec:authorize access="hasRole('ROLE_USER')">
<a href="/squallsoft-clinvet/animais/adicionar" class="btn btn-default">Adicionar novo animal</a>
</sec:authorize>

<script type="text/javascript">
	$(document).ready(function(){
		$('#btn-pesquisa').click(function(){
			var nomeAlbum = escape($('#txt-pesquisa').val());
			$.ajax({
				method: 'GET',
				url: '/squallsoft-clinvet/animais/porNome?nome=' + nomeAlbum,
				success: function(data){
					$('#tbl-animais tbody > tr').remove();										
					$.each(data, function(index, animal){
						$('#tbl-animais tbody').append(
							'<tr>'+
							'	<td>'+animal.id+'</td>'+
							'	<td>'+animal.nome+'</td>'+
							'	<td>'+animal.nomeDoDono+'</td>'+
							'	<td>'+animal.dataNascimento+'</td>'+
							'	<td>'+ 
							'		<a href="/squallsoft-clinvet/animais/alterar/'+ animal.id +'">Alterar</a> |' +
							'		<a href="/squallsoft-clinvet/animais/excluir/'+ animal.id +'">Excluir</a>' +
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