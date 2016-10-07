<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<style type="text/css">
	<c:set var="path" value="${pageContext.request.contextPath}" scope="request"/>
	@IMPORT url("${path}/resources/css/bootstrap.min.css");
	@IMPORT url("${path}/resources/css/bootstrap-theme.min.css");
	@IMPORT url("${path}/resources/css/custom.css");
	@IMPORT url("${path}/resources/css/style.css");
	@IMPORT url("${path}/resources/css/zebra.dialog.css");
	@IMPORT url("${path}/resources/css/select2.min.css");
</style>
<script src="${path}/resources/js/jquery.min.js" 		type="text/javascript"></script>
<script src="${path}/resources/js/select2.min.js" 		type="text/javascript"></script>
<meta charset="UTF-8">
<title>Cadastro de Pokeplace</title>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function() {
		$(".pokemon").select2();
		$('#cidadeId').on('change', function() {
			this.form.submit();
	  	});
	});
</script>
<div class="container">
	<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
			<a href="${path}/pokeplaces/novo" class="btn btn-info" role="button">Novo Pokeplace</a>
			<br>
			<br>
			<form class="form-horizontal">
				<label for="cidadeId">Selecione a Cidade</label> 
				<select id="cidadeId" name="cidade_id" class="js-example-diacritics form-control input-sm pokemon">
					<option value=""></option>
					<c:forEach var="cidade" items="${cidades}">
							<option value="${cidade.id}"
								${cidade.id==cidadeFiltro.id ? 'selected' : ''}>${cidade.nome}					
							</option>
					</c:forEach>				
				</select>
			</form>
   			</div>
			<c:if test="${not empty pokeplaces}">
			<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Cidade</th>
						<th>Latitude</th>
						<th>Longitude</th>
						<th>Tipo</th>
						<th width="06%">Imagem</th>
						<th width="12%">Acao</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pokeplace" items="${pokeplaces}">
						<tr id="row${pokeplace.id}">
							<td>&nbsp;${pokeplace.nome}</td>
							<td>&nbsp;${pokeplace.cidade.nome}</td>
							<td>&nbsp;${pokeplace.latitude}</td>
							<td>&nbsp;${pokeplace.longitude}</td>
							<td>&nbsp;${pokeplace.tipo.nome}</td>
							<td width="12%">
								<a href="#" class="btn btn-success btn-xs"  onclick="window.open('${pageContext.request.contextPath}/pokeplaces/imageDisplay?id=${pokeplace.id}', 'Pagina', 'STATUS=NO, TOOLBAR=NO, LOCATION=NO, DIRECTORIES=NO, RESISABLE=NO, SCROLLBARS=YES, TOP=100, LEFT=300, WIDTH=660, HEIGHT=400');" role="button">Exibir</a>								
							</td>
							<td width="12%">
								<a href="${path}/pokeplaces/edit/${pokeplace.id}" class="btn btn-warning btn-xs" role="button">Alterar</a>
								<a href="${path}/pokeplaces/delete/${pokeplace.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</c:if>
		</div>
	</div>
</div>
<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>
</body>
</html>