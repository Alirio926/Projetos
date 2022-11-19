<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<c:url var="actionAdicionar" value="/login"></c:url>		
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<sec:authorize access="isAuthenticated()">
				<a class="navbar-brand cor-icone" href="/pages/home">Squallsoft</a>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<a class="navbar-brand cor-icone" href="/login">Squallsoft</a>
			</sec:authorize>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
			
		<div class="collapse navbar-collapse"id="bs-example-navbar-collapse-1">
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="principal" />
				<!-- Licença -->
				
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Licença <span class="caret"></span></a>
						<ul class="dropdown-menu cor-icone">													
							<sec:authorize access="hasRole('ROLE_ADMIN')">	
								<li><a href="/pages/licencas/listarAll">Listar</a></li>	
							</sec:authorize>	
							<sec:authorize access="hasRole('ROLE_CLIENTE')">	
								<li><a href="/pages/licencas/listarByCliente">Listar</a></li>	
							</sec:authorize>										
						</ul>
					</li>
				</ul>					
				
				<!-- Clientes -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">		
					<ul class="nav navbar-nav">							
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Clientes <span class="caret"></span></a>
							<ul class="dropdown-menu">			
								<li><a href="/pages/clientes/adicionar">Adicionar</a></li>
								<li><a href="/pages/clientes/listar">Listar</a></li>	
							</ul>
						</li>						
					</ul>
				</sec:authorize>
				<!-- Pedidos -->
				<ul class="nav navbar-nav">
					<sec:authorize access="hasRole('ROLE_ADMIN')">	
						<li><a href="/pages/pedido/listarAll">Pedidos</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_CLIENTE')">	
						<li><a href="/pages/pedido/listarByCliente">Pedidos</a></li>
					</sec:authorize>
				</ul>	
							
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Bem-vindo, ${principal.username} 
						<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/logout">Sair</a></li>
						</ul>
					</li>
				</ul>
				
				<ul class="nav navbar-nav navbar-right">
			      <li><a href="/myapp/download/install.zip"><span class="fa fa-download cor-icone" aria-hidden="true"></span> Download</a></li>
			    </ul>
			</sec:authorize>
			
			<sec:authorize access="!isAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
		        <li><p class="navbar-text">Possui uma conta?</p></li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
					<ul id="login-dp" class="dropdown-menu">
						<li>
							 <div class="row">
									<div class="col-md-12">
										Login
										 <form class="form" role="form" method="post" action="/login" accept-charset="UTF-8" id="login-nav">
												<div class="form-group">
													 <label class="sr-only" for="user_name">Usuario</label>
													 <input type="text" class="form-control" id="user_name" name="user_name" placeholder="usuario" required>
												</div>
												<div class="form-group">
													 <label class="sr-only" for="password">Password</label>
													 <input type="password" class="form-control" id="password" name="password" placeholder="Senha" required>
												</div>
												<div class="form-group">
													 <button type="submit" class="btn btn-primary btn-block">Entrar</button>
												</div>												
										 </form>
									</div>
							 </div>
						</li>
					</ul>
		        </li>
		      </ul>
			</sec:authorize>
		</div>	
		
		<!-- /.navbar-collapse -->
	</div>
	
	<!-- /.container-fluid -->
</nav>


