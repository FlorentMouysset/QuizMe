
<%@ page import="questions.QMultiChoix" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'QMultiChoix.label', default: 'QMultiChoix')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-QMultiChoix" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-QMultiChoix" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list QMultiChoix">
			
				<g:if test="${QMultiChoixInstance?.enonce}">
				<li class="fieldcontain">
					<span id="enonce-label" class="property-label"><g:message code="QMultiChoix.enonce.label" default="Enonce" /></span>
					
						<span class="property-value" aria-labelledby="enonce-label"><g:fieldValue bean="${QMultiChoixInstance}" field="enonce"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${QMultiChoixInstance?.reponses}">
				<li class="fieldcontain">
					<span id="reponses-label" class="property-label"><g:message code="QMultiChoix.reponses.label" default="Reponses" /></span>
					
						<g:each in="${QMultiChoixInstance.reponses}" var="r">
						<span class="property-value" aria-labelledby="reponses-label"><g:link controller="reponse" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${QMultiChoixInstance?.statut}">
				<li class="fieldcontain">
					<span id="statut-label" class="property-label"><g:message code="QMultiChoix.statut.label" default="Statut" /></span>
					
						<span class="property-value" aria-labelledby="statut-label"><g:fieldValue bean="${QMultiChoixInstance}" field="statut"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${QMultiChoixInstance?.id}" />
					<g:link class="edit" action="edit" id="${QMultiChoixInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
