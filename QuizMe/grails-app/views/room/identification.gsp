
<%@ page import="quizme.Room" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'room.label', default: 'Room')}" />
		<title>Mots de passe pour la room name ERREUR !!!${roomInstance?.nom}</title>
	</head>
	<body>
		<a href="#show-room" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link controller="authentification" class="home" action="logout" >Logout</g:link></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
			<g:form action="enterRequest" id="${roomId}" >
			Mot de passe : 
		<g:passwordField name="textMdp" value="" /> <br/>
		
		<g:submitButton name="valBoutonIdentification" value="entrer dans la room"  />
	</g:form>
	</body>
</html>
