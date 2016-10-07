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
<title>Pais</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<form:form commandName="pais" class="form-horizontal" action="${path}/paises/crudPais" method="post">
		<form:input path="id" type="hidden" value="${pais.id}"/>
		
		<div class="panel panel-info">
   			<div class="panel-heading">
   				<div class="clearfix">
   					<c:if test="${not empty pais.id}">
	   					<strong>Alterar país</strong>
					</c:if>
   					<c:if test="${empty pais.id}">
	   					<strong>Novo país</strong>
					</c:if>  					
   				</div>
   			</div>
   			<br>
  			<div class="panel-body">
				<div class="form-group fs-required">
					<label for="nome" class="col-sm-2 control-label">Nome</label> 
					<div class="col-sm-8"> 
						<form:input path="nome" type="text" class="form-control input-sm"  maxlength="100" autofocus="autofocus" value="${pais.nome}"/>
 						<form:errors path="nome" cssClass="error"/>
					</div>
				</div>
				<div class="form-group fs-required">
					<label for="sigla" class="col-sm-2 control-label">Sigla</label> 
					<div class="col-sm-4"> 
						<form:input path="sigla" type="text" class="form-control input-sm"  maxlength="3"  value="${pais.sigla}"/>
   						<form:errors path="sigla" cssClass="error"/>
					</div>
				</div>			
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Salvar</button>
						<a href="${pageContext.request.contextPath}/paises" class="btn btn-default">Cancelar</a>
					</div>
				</div> 	
			</div> 	
   			<div class="panel-footing">
   				<div class="clearfix">
   					<h1 class="panel-title"></h1>
   				</div>
   			</div>			
		</div>	
		</form:form>
		<br>
	</div>
	<script src="${path}/resources/js/jquery.min.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>	
</body>
</html>