<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="panel panel-success">
	<div class="panel-heading">
		Listagem de clientes <a
			href="/pages/clientes/adicionar"> <i
			class="glyphicon glyphicon-user" title="Adicionar Clientes"
			style="color: #f4511e"></i>
		</a> <a class="glyphicon glyphicon-search" data-toggle="collapse"
			data-target="#demo1" style="color: blue" title="Pesquisar por nome"></a>

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
				<th>Nome</th>
				<th>Cliente desde</th>
				<th>Restrição</th>
				<th>Licença</th>
				<th>Ações</th>
			</thead>
			<tbody>
				<c:set var="pageListHolder" value="${clienteList}" scope="session" />
				<c:forEach var="cliente" items="${pageListHolder.pageList}">
					<tr>
						<td>${cliente.nome}</td>
						<td>${cliente.dataCadastro}</td>
						<td>${cliente.restricao}</td>
						<td>
						<a
							href="/pages/licencas/adicionar/${cliente.id}">
								<i class="glyphicon glyphicon-plus" style="color: blue"></i>
						</a> |
						<a
							href="/pages/licencas/findby/${cliente.id}">
								<i class="glyphicon glyphicon-search" style="color: green"></i>
						</a>
						</td>
						<td><a
							href="/pages/clientes/alterar/${cliente.id}">
								<i class="glyphicon glyphicon-pencil" style="color: blue"></i>
							</a> | <a 
								href="/pages/clientes/excluir/${cliente.id}">
									<i class="glyphicon glyphicon-remove" style="color: red"></i>
							</a>
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
								<a href="/pages/clientes/listar${pageurl}/prev">
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
									href="/pages/clientes/listar${pageurl}/${loop.index}">${loop.index+1}</a>
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
								href="/pages/clientes/listar${pageurl}/next">Próximo</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</div>
	
</div>