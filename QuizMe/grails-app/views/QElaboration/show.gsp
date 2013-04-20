<%@ page import="questions.QElaboration" %>
<%@ page import="user.Etudiant" %>
<%@ page import="user.User" %>
<%@ page import="user.Professeur" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'QElaboration.label', default: 'QElaboration')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-QElaboration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link controller="authentification" class="home" action="logout" >Logout</g:link></li>
				<li><g:link class="list" action="inroom" controller="room" ><g:message code="Session List"/></g:link></li>
				<%--<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="show-QElaboration" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list QElaboration">
			
				<g:if test="${QElaborationInstance?.enonce}">
				<li class="fieldcontain">
					<span id="enonce-label" class="property-label"><g:message code="QElaboration.enonce.label" default="Enonce" /></span>
					
						<span class="property-value" aria-labelledby="enonce-label"><g:fieldValue bean="${QElaborationInstance}" field="enonce"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${QElaborationInstance?.reponses}">
				<li class="fieldcontain">
					<span id="reponses-label" class="property-label"><g:message code="QElaboration.reponses.label" default="Reponses" /></span>
					
						<g:each in="${QElaborationInstance.reponses}" var="r">
						<span class="property-value" aria-labelledby="reponses-label"><g:link controller="reponse" action="show" id="${r.id}">${r.answer?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<%--<g:if test="${QElaborationInstance?.statut}">
				<li class="fieldcontain">
					<span id="statut-label" class="property-label"><g:message code="QElaboration.statut.label" default="Statut" /></span>
					
						<span class="property-value" aria-labelledby="statut-label"><g:fieldValue bean="${QElaborationInstance}" field="statut"/></span>
					
				</li>
				</g:if>--%>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${QElaborationInstance?.id}" />
					<g:if test="${Professeur.estProfesseur(User.findById(session["user.id"])) }">
						<g:link class="edit" action="edit" id="${QElaborationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					</g:if>
					<g:if test="${Etudiant.estEtudiant(User.findById(session["user.id"])) }">
						<g:link controller="reponse" class="create" action="propose" id="${QElaborationInstance?.id}"><g:message code="Proposer une autre reponse" default="Proposer une autre reponse" /></g:link>
					</g:if>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
