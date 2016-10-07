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
    .top-buffer { margin-top:15px; }
</style>
</head>
<body>
<script type="text/javascript">
	idPais = 0;
	idEstado = 0;
	idCidade = 0;
	$(document).ready(function() {
		//$(".pokemon").select2();
	  	$("#pais").bind("change",function () {
            $("#estado").html("<option value=''></option>").fadeIn();
            $("#cidade").html("<option value=''></option>").fadeIn();
			var paisId = $(this).val();
     	    if(paisId !== "") {
              var url = "${path}/paises/selecionaEstado/"+paisId;
              $.getJSON( url, function( data ) {
          		  var options = "<option value=''></option>";
                  $.each(data, function(index, valor) {
                  	if(valor.nome != undefined) {
            			options += "<option value='" + valor.id + "' "+(valor.id==idEstado ? ' selected' : '')+">" + valor.nome + "</option>";
                  	}
                  });
                  $("#estado").html(options).fadeIn();
              });
            }
	  	});
	  	$("#estado").bind("change",function () {
            $("#cidade").html("<option value=''></option>").fadeIn();
            if (parseInt(idEstado) > 0)
			   var estadoId = parseInt(idEstado);
            if (parseInt($(this).val()) > 0)
            	var estadoId = $(this).val();
   	   	 	if(estadoId !== "") {
              var url = "${path}/estados/selecionaCidade/"+estadoId;
              $.getJSON( url, function( data ) {
          		  var options = "<option value=''></option>";
                  $.each(data, function(index, valor) {
                  	if(valor.nome != undefined) {
            			options += "<option value='" + valor.id + "' "+(valor.id==idCidade ? ' selected' : '')+">" + valor.nome + "</option>";
                  	}
                  });
                  $("#cidade").html(options).fadeIn();
              });
            }
	  	});	
	  	$( "#logradouro" ).blur(function() {
     		latitude = 0;
     		longitude = 0;
			var logradouro = $(this).val();
			logradouro = logradouro.replace("Av. ","Avenida ");
			logradouro = logradouro.replace("R. ", "Rua ");
  	    	if(logradouro !== "") {
                var url = "${path}/pokeplaces/updatePokeplace/"+logradouro;
                $.getJSON( url, function( pokeplace ) {
                  	$("#latitude").val(pokeplace.latitude);
                 	$("#longitude").val(pokeplace.longitude);
                 	$("#logradouro").val(pokeplace.logradouro); 
                	idPais = pokeplace.cidade.estado.pais.id;
                	idEstado = pokeplace.cidade.estado.id;
                	idCidade = pokeplace.cidade.id;
        			$('#pais option[value='+pokeplace.cidade.estado.pais.id+']').attr('selected', true);
                 	$('#pais').change();
                 	$('#estado option[value='+pokeplace.cidade.estado.id+']').attr('selected', true);
                 	$('#estado').change();
             	 	$('#cidade option[value='+pokeplace.cidade.id+']').attr('selected', true);
					console.log(pokeplace);
                });
     	    }
	  	});	
	  	
	  	$("#endereco").bind("change",function () {
	  		var logradouro = $(this).val();
	  		$("#logradouro").val(logradouro);
  	    	if($("#logradouro").val() !== "") {
	  			$("#logradouro").focus();
  	    	} else {
	  			$("#nome").focus();
  	    	}
	  	});	
	});
</script>
<div class="container">
	<c:import url="..\cabecalho.jsp" />
	<form:form commandName="pokeplace" class="form-horizontal" action="${path}/pokeplaces/crudPokeplace" method="post" enctype="multipart/form-data">
	<form:input path="id" type="hidden" />
	<form:input path="imagem" type="hidden"  />
	<form:input path="nomeImagem" type="hidden"  />
	
	<div class="panel panel-info">
		<div class="panel-heading">
			<div class="clearfix">
				<c:if test="${not empty pokeplace.id}">
 					<strong>Alterar</strong>
				</c:if>
				<c:if test="${empty pokeplace.id}">
 					<strong>Cadastrar</strong>
				</c:if>  					
			</div>
		</div>
  		<br>
  		<div class="panel-body">
  		
			<div class="row">
			<div class="col-sm-12">
				<label for="nome">Nome</label> 
				<form:input id="nome" path="nome" type="text" class="form-control input-sm"  maxlength="100" autofocus="autofocus" value="${pokeplace.nome}"/>
				<form:errors path="nome" cssClass="error"/>
			</div>
			</div>

			<div class="row top-buffer">
			<div class="col-sm-12">
				<label for="nome">Logradouro</label> 
				<input id="logradouro" name="logradouro" type="text" class="form-control input-sm"  maxlength="150" value="${pokeplace.logradouro}"/>
			</div>
			</div>
			
			<div class="row top-buffer">
			<div class="col-sm-4">
				<label for="latitude">Latitude</label> 
				<form:input id="latitude" path="latitude" type="number" class="form-control input-sm" step="0.00000001" value="${pokeplace.latitude}"/>
				<form:errors path="latitude" cssClass="error"/>
			</div>
			<div class="col-sm-4">
				<label for="longitude">Longitude</label> 
				<form:input id="longitude" path="longitude" type="number" class="form-control input-sm"  step="0.00000001" value="${pokeplace.longitude}"/>
				<form:errors path="longitude" cssClass="error"/>
			</div>
			<div class="col-sm-4">
				<label for="tipo">Tipo</label> 
				<form:select id="tipo" path="tipo" class="form-control input-sm">
					<option value=""></option>
					<c:forEach items="${tipos}" var="tipo">
						<option value="${tipo.id}"
							${tipo.id==pokeplace.tipo.id ? 'selected' : ''}>${tipo.nome}							
						</option>
					</c:forEach>
				</form:select>	
				<form:errors path="tipo" cssClass="error"/>
			</div>
			</div>

			<div class="row top-buffer">
			<div class="col-sm-4">
				<label for="pais">Pais</label> 
				<select id="pais" name="pais" class="form-control input-sm pokemon">
					<option value=""></option>
					<c:forEach items="${paises}" var="pais">
						<option value="${pais.id}"
							${pais.id==pokeplace.cidade.estado.pais.id ? 'selected' : ''}>${pais.nome}							
						</option>
					</c:forEach>
				</select>	
			</div>				
			<div class="col-sm-4">
				<label for="estado">Estado</label> 
				<select id="estado" name="estado"  class="form-control input-sm pokemon">
					<option value=""></option>
					<c:forEach items="${estados}" var="estado">
						<option value="${estado.id}"
							${estado.id==pokeplace.cidade.estado.id ? 'selected' : ''}>${estado.nome}							
						</option>
					</c:forEach>
				</select>	
			</div>	
			<div class="col-sm-4">
				<label for="cidade">Cidade</label> 
				<form:select id="cidade" path="cidade"  class="form-control input-sm pokemon">
					<option value=""></option>
					<c:forEach items="${cidades}" var="cidade">
						<option value="${cidade.id}"
							${cidade.id==pokeplace.cidade.id ? 'selected' : ''}>${cidade.nome}							
						</option>
					</c:forEach>
				</form:select>	
					<form:errors path="cidade" cssClass="error"/>
			</div>
			</div>	
			
			<div class="row top-buffer">
			<div class="col-sm-5">
				<label for="fileName">Imagem</label> 
				<input name="fileName" type="file" class="form-control input-sm"/>
			</div>
			</div>

			<div class="row top-buffer">
			<div class="col-sm-12">
				<label for="endereco">Endereços Google Maps da Latitude e Longitude</label> 
				<select id="endereco" name="endereco" class="form-control input-sm">
					<option value=""></option>
					<c:forEach items="${enderecos}" var="endereco">
						<option value="${endereco}">${endereco}							
						</option>
					</c:forEach>
				</select>	
			</div>
			</div>
						
			<div class="row top-buffer">
			<div class="col-sm-12">
					<button type="submit" class="btn btn-primary">Salvar</button>
					<a href="${path}/pokeplaces" class="btn btn-default">Cancelar</a>
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