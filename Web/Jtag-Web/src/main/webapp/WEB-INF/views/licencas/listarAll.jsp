<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="panel panel-success">
	<div class="panel-heading">
		Lista de Licen�as
	</div>
	
	<div class="panel-body">
		<div id="demo1" class="collapse">
			<div class="navbar-form navbar-left"
				role="Nome do cliente( ou parte dele )">
				<div class="form-group">
					<input type="text" class="form-control" id="txt-pesquisa"
						placeholder="Nome do cliente( ou parte dele )">
				</div>
				<button class="btn btn-default" type="button" id="btn-pesquisa">Pesquisar</button>
			</div>
		</div>
		
		<table class="table table-striped" id="tbl-clientes">
			<thead>
				<th>Cliente</th>
				<th>Status</th>
				<th>C�d. Maquina</th>
				<th>Produto</th>
				<th>Valor</th>
				<th>Data inicio</th>
				<th>Quant. Inicial</th>
				<th>Quant. Disponivel</th>	
				<th>Pedidos</th>		
			</thead>
			<tbody>
				<c:set var="pageListHolder" value="${licencaList}" scope="session" />
				
				<c:set var="listCliente" value="${lstClientes}" scope="session" />
				
				<c:forEach var="licenca" items="${pageListHolder.pageList}" varStatus="loop">
					<tr>
						<td>${listCliente[loop.index]}</td>
						<td>${licenca.status}</td>
						<td>${licenca.machineCode}</td>
						<td>${licenca.produto}</td>
						<td>${licenca.valor}</td>
						<td>${licenca.dataAtivacao}</td>
						<td>${licenca.qtdInicial}</td>
						<td>${licenca.qtdDisponivel}</td>	
						<th><a href="/pages/pedido/findby/${licenca.id}">
								<i class="glyphicon glyphicon-pencil" style="color: blue"></i>
						</a></th>		
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
								<a href="/pages/clientes/listar/${pageurl}/prev">
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
									href="/pages/clientes/listar/${pageurl}/${loop.index}">${loop.index+1}</a>
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
								href="/pages/clientes/listar/${pageurl}/next">Pr�ximo</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	
</div>