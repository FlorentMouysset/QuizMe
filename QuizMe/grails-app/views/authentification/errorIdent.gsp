
<%@ page import="quizme.Authentification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'authentification.label', default: 'Authentification')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		Connection impossible ! <br/>
		Cause :<br/>
		${params["cause"]}		
		<br/>
		<a class="home" href="${createLink(uri: '/')}">essayer à nouveau</a>
		
	</body>
</html>
