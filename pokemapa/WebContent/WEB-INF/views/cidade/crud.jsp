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
<title>Cidades</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function() {
		$(".pokemon").select2();
	  	$("#pais").bind("change",function () {
			var paisId = $(this).val();
     	    if(paisId !== "") {
              var url = "/pokemapa/paises/selecionaEstado/"+paisId;
              $.getJSON( url, function( data ) {
          		var options = "<option value=''></option>";
                  $.each(data, function(index, valor) {
                  	if(valor.nome != undefined) {
                      	options += "<option value='" + valor.id + "'>" + valor.nome + "</option>";
                  	}
                  });
                  $("#estado").html(options).fadeIn();
              });
            }
	  	});
	});
</script>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<form:form commandName="cidade" class="form-horizontal" action="${path}/cidades/crudCidade" method="post">
		<form:input path="id" type="hidden" value="${cidade.id}"/>
		
		<div class="panel panel-info">
   			<div class="panel-heading">
   				<div class="clearfix">
   					<c:if test="${not empty cidade.id}">
	   					<strong>Alterar</strong>
					</c:if>
   					<c:if test="${empty cidade.id}">
	   					<strong>Cadastrar</strong>
					</c:if>  					
   				</div>
   			</div>
   			<br>
  			<div class="panel-body">
				<div class="form-group fs-required">
					<label for="nome" class="col-sm-2 control-label">Nome</label> 
					<div class="col-sm-8"> 
						<form:input path="nome" type="text" class="form-control input-sm"  maxlength="100" autofocus="autofocus" value="${estado.nome}"/>
 						<form:errors path="nome" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label for="pais" class="col-sm-2 control-label">Pais</label> 
					<div class="col-sm-3">
					<select id="pais" name="pais" class="form-control input-sm pokemon">
						<option value=""></option>
						<c:forEach var="pais" items="${paises}">
							<option value="${pais.id}"
								${pais.id==cidade.estado.pais.id ? 'selected' : ''}>${pais.nome}							
							</option>
						</c:forEach>
					</select>	
					</div>
				</div>				
				<div class="form-group fs-required">
					<label for="estado" class="col-sm-2 control-label">Estado</label> 
					<div class="col-sm-3">
					<form:select id="estado" path="estado" class="form-control input-sm pokemon">
						<option value=""></option>
						<c:forEach var="estado" items="${estados}">
							<option value="${estado.id}"
								${estado.id==cidade.estado.id ? 'selected' : ''}>${estado.nome}							
							</option>
						</c:forEach>
					</form:select>	
 					<form:errors path="estado" cssClass="error"/>
					</div>
				</div>				
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Salvar</button>
						<a href="${pageContext.request.contextPath}/cidades" class="btn btn-default">Cancelar</a>
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
	<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>	
</body>
</html>