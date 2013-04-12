
<%@ page import="user.Professeur" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'professeur.label', default: 'Professeur')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-professeur" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-professeur" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list professeur">
			
				<g:if test="${professeurInstance?.prenom}">
				<li class="fieldcontain">
					<span id="prenom-label" class="property-label"><g:message code="professeur.prenom.label" default="Prenom" /></span>
					
						<span class="property-value" aria-labelledby="prenom-label"><g:fieldValue bean="${professeurInstance}" field="prenom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${professeurInstance?.nom}">
				<li class="fieldcontain">
					<span id="nom-label" class="property-label"><g:message code="professeur.nom.label" default="Nom" /></span>
					
						<span class="property-value" aria-labelledby="nom-label"><g:fieldValue bean="${professeurInstance}" field="nom"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${professeurInstance?.sexe}">
				<li class="fieldcontain">
					<span id="sexe-label" class="property-label"><g:message code="professeur.sexe.label" default="Sexe" /></span>
					
						<span class="property-value" aria-labelledby="sexe-label"><g:fieldValue bean="${professeurInstance}" field="sexe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${professeurInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="professeur.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${professeurInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${professeurInstance?.dateDeNaissance}">
				<li class="fieldcontain">
					<span id="dateDeNaissance-label" class="property-label"><g:message code="professeur.dateDeNaissance.label" default="Date De Naissance" /></span>
					
						<span class="property-value" aria-labelledby="dateDeNaissance-label"><g:formatDate date="${professeurInstance?.dateDeNaissance}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${professeurInstance?.id}" />
					<g:link class="edit" action="edit" id="${professeurInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
