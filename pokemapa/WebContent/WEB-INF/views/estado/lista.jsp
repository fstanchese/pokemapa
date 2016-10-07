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
<title>Cadastro de Estados</title>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function() {
		$(".pokemon").select2();
		$('#paisId').on('change', function() {
			this.form.submit();
	  	});
	});
	</script>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
			<a href="${path}/estados/novo" class="btn btn-info" role="button">Novo Estado</a>
			<br>
			<br>
			<form class="form-horizontal">
				<label for="paisId">Selecione o Pais</label> 
				<select id="paisId" name="pais_id" tabindex="2" class="form-control input-sm pokemon">
					<option value=""></option>
					<c:forEach items="${paises}" var="pais">
							<option value="${pais.id}"
								${pais.id==paisFiltro.id ? 'selected' : ''}>${pais.nome}							
							</option>
					</c:forEach>
				</select>
			</form>
			</div>
			<c:if test="${not empty estados}">
			<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th align=center>Nome</th>
						<th align=center>Sigla</th>
						<th align=center>País</th>
						<th width="12%">&nbsp;&nbsp;Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="estado" items="${estados}">
						<tr id="row${estado.id}">
							<td>&nbsp;${estado.nome}</td>
							<td>&nbsp;${estado.uf}</td>
							<td>&nbsp;${estado.pais.nome}</td>
							<td width="12%">
								<a href="${path}/estados/edit/${estado.id}" class="btn btn-warning btn-xs" role="button">Editar</a>
								<a href="${path}/estados/delete/${estado.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
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