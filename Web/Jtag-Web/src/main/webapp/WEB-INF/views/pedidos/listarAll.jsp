<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="actionAdicionar" value="/atendimentos/adicionar"></c:url>

<style>
.bg {
  background-image: url("https://i.pinimg.com/originals/22/d1/41/22d141e8a1a0037f888a42af53752cc1.jpg");
  background-size: cover;
}
</style>

<div class="panel panel-success bg">
	<div class="panel-heading">
		Listagem de pedidos 
		</a> <a class="glyphicon glyphicon-search" data-toggle="collapse"
				data-target="#demo1" style="color: blue" title="Pesquisar por mês"></a>
	</div>	
	
	<div class="panel-body">
		<div id="demo1" class="collapse">
			<div class="navbar-form navbar-left"
				role="Nome do cliente( ou parte dele )">
				<form action="/pages/pedido/listarByMonth/" method="post">
					<input type="month" class="form-control" id="txt-month"
							placeholder="Mês" min="2021-01" name="month">
					<button class="btn btn-default" type="submit" id="btn-pesquisa" value="Submit">Pesquisar</button>
				</form>
			</div>
		</div>
		
		<table class="table table-striped" id="tbl-clientes">
			<thead>
				<th style="color:yellow">Cliente</th>
				<th style="color:yellow">Status</th>
				<th style="color:yellow">Data</th>
				<th style="color:yellow">Hora</th>
				<th style="color:yellow">Mapper</th>				
				<th style="color:yellow">Gravada</th>
				<th style="color:yellow">C/Falha</th>	
				<th style="color:yellow">Cancelada</th>
				<th style="color:yellow">Total</th>
				<!-- <th>Log</th> -->				
			</thead>
			<tbody>
				<c:set var="pageListHolder" value="${listaPedidos}" scope="session" />
				
				<c:set var="listCliente" value="${lstClientes}" scope="session" />
				
				<c:forEach var="pedido" items="${pageListHolder.pageList}" varStatus="loop">
					<tr data-toggle="collapse" data-target="#pedido_${pedido.id}" class="accordion-toggle">
						<td>${listCliente[loop.index]}</td>
						<td>${pedido.status}</td>
						<td>${pedido.data}</td>
						<td>${pedido.hora_pedido}</td>
						<td>${pedido.mappperId}</td>						
						<td>${pedido.qtdSuccess}</td>
						<td>${pedido.qtdFail}</td>
						<td>${pedido.qtdCancel}</td>
						<td>${pedido.quant}</td>						
						<!-- <td>
							<button class="btn btn-default btn-xs">
								<span class="fa fa-eye cor-icone" aria-hidden="true"></span>
							</button>
						</td>  -->																		
					</tr>
					<tr>
						<td colspan="12" class="hiddenRow">
							<div class="accordian-body collapse" id="pedido_${pedido.id}"> 
								<table class="table">
									<tbody>
									<tr class="list-group">
									<c:forEach var="lstLog" items="${listaLogs[loop.index]}" varStatus="logLoop">
										<td>
											<a href="#" class="list-group-item list-group-item-action" 
												data-toggle="modal" 
												data-target="#exampleModalCenter" 
												data-whatever="${lstLog.id}">-> Abrir Log</a>
										</td>
									</c:forEach>
									</tr>
									</tbody>
								</table>								
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="navbar-form navbar-right">
			<nav aria-label="...">
				<ul class="pagination pagination-sm justify-content-center">
					<!-- Anterior -->
					<li class="page-item"><c:choose>
							<c:when test="${pageListHolder.firstPage}">
								<li class="page-item disabled"><span class="page-link">Anterior</span></li>
							</c:when>
							<c:otherwise>
								<a href="/pages/pedido/listarAll${pageurl}/prev">
									<span class="page-link">Anterior</span>
								</a>
							</c:otherwise>
						</c:choose></li>
					<!-- Paginas -->

					<c:forEach begin="0" end="${pageListHolder.pageCount-1}"
						varStatus="loop">

						<c:choose>
							<c:when test="${loop.index == pageListHolder.page}">
								<li class="page-item active"><span class="page-link">${loop.index+1}
										<span class="sr-only">(current)</span>
								</span></li>
							</c:when>

							<c:otherwise>
								<li class="page-item"><a class="page-link"
									href="/pages/pedido/listarAll${pageurl}/${loop.index}">${loop.index+1}</a>
								</li>
							</c:otherwise>
						</c:choose>

					</c:forEach>


					<c:choose>
						<c:when test="${pageListHolder.lastPage}">
							<li class="page-item disabled"><span class="page-link">Próximo</span>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="/pages/pedido/listarAll${pageurl}/next">Próximo</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	
	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>	        
	      </div>
	      <div class="modal-body">
	         <textarea class="form-control" rows="13" id="log"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>	
</div>

<script>
	$('#exampleModalCenter').on('show.bs.modal', function (event) {
		 var button = $(event.relatedTarget) // Button that triggered the modal
		 var recipient = button.data('whatever') // Extract info from data-* attributes
	
		 $.ajax({ 
			type : 'GET', 
			url : '/pages/pedido/burnID?id=' + recipient,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				console.log(data);
				var modal = $('#exampleModalCenter')
				modal.find('.modal-title').text('Log ' + recipient)
	 			modal.find('.modal-body textarea').val(data) 
			},
			error : function (jqXHR, exception) {
		        var msg = '';
		        if (jqXHR.status === 0) {
		            msg = 'Not connect.\n Verify Network.';
		        } else if (jqXHR.status == 404) {
		            msg = 'Requested page not found. [404]';
		        } else if (jqXHR.status == 500) {
		            msg = 'Internal Server Error [500].';
		        } else if (exception === 'parsererror') {
		            msg = 'Requested JSON parse failed.';
		        } else if (exception === 'timeout') {
		            msg = 'Time out error.';
		        } else if (exception === 'abort') {
		            msg = 'Ajax request aborted.';
		        } else {
		            msg = 'Uncaught Error.\n' + jqXHR.responseText;
		        }
		        console.log(msg);
		        $('#post').html(msg);													
			},
		});
	 
	 //var modal = $(this)
	 //modal.find('.modal-title').text('New message to ' + recipient)
	 //modal.find('.modal-body input').val(recipient)
	});
</script>




