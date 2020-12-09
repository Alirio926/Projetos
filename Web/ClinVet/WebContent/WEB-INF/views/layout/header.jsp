<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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
			<a class="navbar-brand" href="/squallsoft-clinvet/home/bemvindo">Clínica Veterinária</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="principal" />
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<!-- Menu Veterinarios -->
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Veterinários <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/squallsoft-clinvet/veterinarios/listar">Listar</a></li>
						</ul></li>
				</ul>
				<!-- Menu Animais -->
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Animais <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/squallsoft-clinvet/animais/adicionar">Adicionar</a></li>
							<li><a href="/squallsoft-clinvet/animais/listar">Listar</a></li>
						</ul></li>
				</ul>
				<!-- Menu Prontuarios -->
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Prontuários <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/squallsoft-clinvet/atendimentos/adicionar">Adicionar</a></li>
							<li><a href="/squallsoft-clinvet/atendimentos/listar">Listar</a></li>
						</ul></li>
				</ul>
				<!-- Menu Admin -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">Admin <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/squallsoft-clinvet/usuarios/adicionar">Adicionar Usuário</a></li>
								<li><a href="/squallsoft-clinvet/veterinarios/adicionar">Adicionar Veterinário</a></li>
							</ul></li>
					</ul>
				</sec:authorize>
				<!-- Identificação do usuario logado -->
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Bem-vindo, ${principal.username} <span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/squallsoft-clinvet/logout">Sair</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</sec:authorize>
	</div>
	<!-- /.container-fluid -->
</nav>