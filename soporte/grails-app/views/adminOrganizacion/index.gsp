<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="aplicacion.titulo"/></title>
	</head>
	<body>
	    <h1>Administración de ${params.organizacion}</h1>
	    <g:form>
            <g:hiddenField name="organizacion" value="${params.organizacion}"/>
            <g:actionSubmit value="Administrar Miembros" action="adminMiembros"/><br>
            <g:actionSubmit value="Administrar Planes" action="adminPlanes"/><br>
            <g:actionSubmit value="Administrar Aplicaciones Cliente" action="adminAplicacionesCliente"/>
        </g:form>
	</body>
</html>