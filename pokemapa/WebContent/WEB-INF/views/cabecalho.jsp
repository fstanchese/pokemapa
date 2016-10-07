<!-- Barra superior com os menus de navegação -->
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container"> 
		<button class="navbar-toggle" type="button" data-toggle="collapse"
			data-target=".bs-navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
			<ul class="nav navbar-nav navbar-left">
				<li>
					<form action="${pageContext.request.contextPath}//logout" method="post">
						<button id="btn-sair" type="submit" class="btn btn-default">Sair</button>
					</form>
				</li>
			</ul>			
			<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/mapas">Mapas</a></li>
					<li><a href="${pageContext.request.contextPath}/pokeplaces">Pokeplace</a></li>
					<li><a href="${pageContext.request.contextPath}/cidades">Cidades</a></li>
					<li><a href="${pageContext.request.contextPath}/estados">Estados</a></li>
					<li><a href="${pageContext.request.contextPath}/paises">Paises</a></li>
			</ul>
		</nav>
	</div>
</nav>