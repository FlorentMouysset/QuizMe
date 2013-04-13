
<%@ page import="user.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list user">
			
				<g:if test="${userInstance?.prenom}">
				<li class="fieldcontain">
					<span id="prenom-label" class="property-label"><g:message code="user.prenom.label" default="Prenom" /></span>
					
						<span class="property-value" aria-labelledby="prenom-label"><g:fieldValue bean="${userInstance}" field="prenom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.nom}">
				<li class="fieldcontain">
					<span id="nom-label" class="property-label"><g:message code="user.nom.label" default="Nom" /></span>
					
						<span class="property-value" aria-labelledby="nom-label"><g:fieldValue bean="${userInstance}" field="nom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.sexe}">
				<li class="fieldcontain">
					<span id="sexe-label" class="property-label"><g:message code="user.sexe.label" default="Sexe" /></span>
					
						<span class="property-value" aria-labelledby="sexe-label"><g:fieldValue bean="${userInstance}" field="sexe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="user.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${userInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.dateDeNaissance}">
				<li class="fieldcontain">
					<span id="dateDeNaissance-label" class="property-label"><g:message code="user.dateDeNaissance.label" default="Date De Naissance" /></span>
					
						<span class="property-value" aria-labelledby="dateDeNaissance-label"><g:formatDate date="${userInstance?.dateDeNaissance}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${userInstance?.identifiants}">
				<li class="fieldcontain">
					<span id="identifiants-label" class="property-label"><g:message code="user.identifiants.label" default="Identifiants" /></span>
					
						<span class="property-value" aria-labelledby="identifiants-label"><g:link controller="userId" action="show" id="${userInstance?.identifiants?.id}">${userInstance?.identifiants?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${userInstance?.id}" />
					<g:link class="edit" action="edit" id="${userInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
