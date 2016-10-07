<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
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
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<meta name="robots" content="noindex, nofollow">
<meta name="googlebot" content="noindex, nofollow">
<title>Pokemapa</title>
</head>
<body>
<div class="container">
	<c:import url="..\cabecalho.jsp" />
	<div class="row">
	<div class="col-md-4">
		<div class="panel panel-group">
	   		<div class="panel panel-primary">
	   			<div class="panel-heading">
				<form class="form-horizontal">
					<label for="cidadeId">Selecione a Cidade : </label> 
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
						<th>Tipo</th>
						<th width="06%">Pokeplace</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pokeplace" items="${pokeplaces}">
						<tr>
							<td>&nbsp;${pokeplace.nome}</td>
							<td>&nbsp;${pokeplace.tipo.nome}</td>
							<td width="12%">
								<a href="javascript:google.maps.event.trigger(gmarkers['Local ${pokeplace.id}'],'click');" class="btn btn-success btn-xs" role="button">Localizar</a>								
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
				</c:if>	   			
	 		</div>
		</div>
	</div>
	<div class="col-md-8">
		<div id="map" style="width:100%; min-height:520px"></div>
	</div>
	</div>
</div>
<script>
$(document).ready(function() {
	
	var locations = [];
	<c:if test="${not empty pokeplaces}">
		var locations = [
		<c:forEach var="pokeplace" items="${pokeplaces}">
			['Local ${pokeplace.id}','${pokeplace.nome}',${pokeplace.latitude},${pokeplace.longitude},'/pokemapa/pokeplaces/imageDisplay?id=${pokeplace.id}'],
		</c:forEach>
		];
	</c:if>

	gmarkers = [];

	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 14,
		<c:if test="${not empty pokeplaces}">
			center: {lat: ${pokeplace.latitude}, lng: ${pokeplace.longitude}},
		</c:if>
		<c:if test="${empty pokeplaces}">
			center: {lat: -23.551641, lng: -46.597417},
		</c:if>	                 
		mapTypeId: google.maps.MapTypeId.ROADMAP
	});

	var infowindow = new google.maps.InfoWindow();

	function createMarker( latlng, img, local ) {
		var marker = new google.maps.Marker({
			position: latlng,
			map: map
	});

	google.maps.event.addListener(marker, 'click', function() 
		{
			infowindow.setContent('<img width="100" src="'+img+'" /><br>'+local); 
			infowindow.open(map, marker);
		});
		return marker;
	}

	for (var i = 0; i < locations.length; i++) {
		gmarkers[locations[i][0]] =
		createMarker(new google.maps.LatLng(locations[i][2], locations[i][3]), locations[i][4], locations[i][1] );
	}

	$(".pokemon").select2(); 
	$('#cidadeId').on('change', function() {
			this.form.submit();
	});
});	
	
	</script>
      <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyBwV2PGdvd6xprPTks_qnY3UbP-Jb4lstw&signed_in=true"></script>
	</body>
</html>