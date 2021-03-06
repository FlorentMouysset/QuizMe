<%@ page import="questions.Reponse" %>
<%@ page import="user.User" %>
<%@ page import="user.Professeur" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reponse.label', default: 'Reponse')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reponse" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link controller="authentification" class="home" action="logout" >Logout</g:link></li>
				<li><g:link class="list" action="inroom" controller="room" ><g:message code="Session List"/></g:link></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reponse" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reponse">
			
				<g:if test="${reponseInstance?.answer}">
				<li class="fieldcontain">
					<span id="answer-label" class="property-label"><g:message code="reponse.answer.label" default="Answer" /></span>
					
						<span class="property-value" aria-labelledby="answer-label"><g:fieldValue bean="${reponseInstance}" field="answer"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reponseInstance?.question}">
				<li class="fieldcontain">
					<span id="question-label" class="property-label"><g:message code="reponse.question.label" default="Question" /></span>
					
						<span class="property-value" aria-labelledby="question-label"><g:link controller="${reponseInstance.question.getClass().getName().replaceFirst("questions.", "")}" action="show" id="${reponseInstance?.question?.id}">${reponseInstance?.question?.enonce?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				
				<g:if test="${reponseInstance?.commentaire}">
				<li class="fieldcontain">
					<span id="answer-label" class="property-label"><g:message code="reponse.commentaire.label" default="Commentaire" /></span>
					
						<span class="property-value" aria-labelledby="answer-label"><g:fieldValue bean="${reponseInstance}" field="commentaire"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reponseInstance?.id}" />
					<g:if test="${Professeur.estProfesseur(User.findById(session["user.id"])) }">
						<g:link class="edit" action="edit" id="${reponseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</g:if>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
