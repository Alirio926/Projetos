<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="panel panel-success">
	<div class="panel-heading">
		Licenças de ${cliente} 
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
				<th>Data</th>
				<th>Status</th>
				<th>Produto</th>
				<th>Quat. Inicial</th>
				<th>Quat. Disponível</th>
				<th>Desativar</th>
			</thead>
			<tbody>
				<c:set var="pageListHolder" value="${licencaList}" scope="session" />
				<c:forEach var="licenca" items="${pageListHolder.pageList}">
					<tr>
						<td>${licenca.dataAtivacao}</td>
						<td>${licenca.status}</td>
						<td>${licenca.produto}</td>
						<td>${licenca.qtdInicial}</td>
						<td>${licenca.qtdDisponivel}</td>						
						<td>
							<a href="/pages/licencas/desativar/${licenca.id}">
								<i class="glyphicon glyphicon-remove" style="color: red"></i>
						</a>
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
									href="/admin/pages/clientes/listar/${pageurl}/${loop.index}">${loop.index+1}</a>
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
								href="/admin/pages/clientes/listar/${pageurl}/next">Próximo</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	
</div>