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
</style>
<meta charset="UTF-8">
<title>Cadastro de Paises</title>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
				<a href="${path}/paises/novo" class="btn btn-info" role="button">Novo País</a>
   			</div>
			<c:if test="${not empty paises}">
				<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th align=center>Nome</th>
						<th align=center>Sigla</th>
						<th width="12%">&nbsp;&nbsp;Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pais" items="${paises}">
						<tr id="row${pais.id}">
							<td>&nbsp;${pais.nome}</td>
							<td>&nbsp;${pais.sigla}</td>
							<td width="12%">
								<a href="${path}/paises/edit/${pais.id}" class="btn btn-warning btn-xs" role="button">Alterar</a>
								<a href="${path}/paises/delete/${pais.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</c:if>
		</div>
		</div>
	</div>
	<script src="${path}/resources/js/jquery.min.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>
</body>
</html>