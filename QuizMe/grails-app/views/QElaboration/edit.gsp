<%@ page import="questions.QElaboration" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'QElaboration.label', default: 'QElaboration')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-QElaboration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				
				<li><g:link controller="authentification" class="home" action="logout" >Logout</g:link></li>
				<li><g:link class="list" action="inroom" controller="room" ><g:message code="Session List"/></g:link></li>
				<%--<li><g:link class="list" action="create" controller="session"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="edit-QElaboration" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${QElaborationInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${QElaborationInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" controller="QElaboration" action="saveOrUpdate">
				<g:hiddenField name="id" value="${QElaborationInstance?.id}" />
				<g:hiddenField name="version" value="${QElaborationInstance?.version}" />
				<div class="fieldcontain">
				<label for="choix"> <g:message
						code="QElaboration.enonce.label" default="Choix action" /> <span
					class="required-indicator">*</span>
				</label>
				
				<g:radioGroup name="myAction" labels="['Add reponse','Save']"
					values="[1,2]" value="1">
					<p>${it.label} ${it.radio}</p>
				</g:radioGroup>
				</div>
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="submitButton" value="Submit"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

