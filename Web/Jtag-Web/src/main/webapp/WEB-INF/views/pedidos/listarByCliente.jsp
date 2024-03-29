<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

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
				data-target="#demo1" style="color: blue" title="Pesquisar por nome"></a>
	</div>		
	
	<div class="panel-body">
	
		<div id="demo1" class="collapse">
			<div class="navbar-form navbar-left"
				role="Nome do cliente( ou parte dele )">
				<div class="form-group">
					<input type="month" class="form-control" id="txt-pesquisa"
						placeholder="M�s" min="2021-01">
				</div>
				<button class="btn btn-default" type="button" id="btn-pesquisa">Pesquisar</button>
			</div>
		</div>
		
		<table class="table table-striped" id="tbl-Pedidos">
			<thead>				
				<th style="color:yellow">Status</th>
				<th style="color:yellow">Data</th>
				<th style="color:yellow">Hora</th>
				<th style="color:yellow">Mapper</th>
				<th style="color:yellow">Total</th>
				<th style="color:yellow">Gravada</th>
				<th style="color:yellow">C/Falha</th>	
				<th style="color:yellow">Cancelada</th>
			</thead>
			<tbody>
				<c:set var="pageListHolder" value="${listaPedidos}" scope="session" />								
				<c:forEach var="pedido" items="${pageListHolder.pageList}" varStatus="loop">
					<tr>
						<td>${pedido.status}</td>
						<td>${pedido.data}</td>
						<td>${pedido.hora_pedido}</td>
						<td>${pedido.mappperId}</td>
						<td>${pedido.quant}</td>
						<td>${pedido.qtdSuccess}</td>
						<td>${pedido.qtdFail}</td>
						<td>${pedido.qtdCancel}</td>													
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
								<a href="/admin/pages/pedido/listarByCliente/${pageurl}/prev">
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
									href="/admin/pages/pedido/listarByCliente${pageurl}/${loop.index}">${loop.index+1}</a>
								</li>
							</c:otherwise>
						</c:choose>

					</c:forEach>


					<c:choose>
						<c:when test="${pageListHolder.lastPage}">
							<li class="page-item disabled"><span class="page-link">Pr�ximo</span>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="/admin/pages/pedido/listarByCliente${pageurl}/next">Pr�ximo</a>
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
	         <input type="text" class="form-control" id="log">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>	
</div>

<script src="/js/listPedidosByMonth.js"></script>

<script>
	$('#exampleModalCenter').on('show.bs.modal', function (event) {
	 var button = $(event.relatedTarget) // Button that triggered the modal
	 var recipient = button.data('whatever') // Extract info from data-* attributes
	 // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	 // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	 var modal = $(this)
	 modal.find('.modal-title').text('New message to ' + recipient)
	 modal.find('.modal-body input').val(recipient)
	})
</script>