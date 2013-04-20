<%@ page import="questions.Question" %>
<%@ page import="questions.QMultiChoix" %>
<%@ page import="questions.QElaboration" %>
<%@ page import="questions.QVraiFaux" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
		<title><g:message code="Choix du type de question" args="[entityName]" /></title>
	</head>
	<body>
			<a href="#list-question" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		
		<div id="type-question" class="content scaffold-type" role="main">
			<h1><g:message code="Choix du type de question" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
		</div>
		<!– TODO : Moche, Rajouter import question pour la faire apparaitre –!>
		<table>
			<g:if test="${QMultiChoix}">
			<tr>
				<td><g:link controller="QMultiChoix" action="create">Choix multiples</g:link></td>
			</tr>
			</g:if>
			<g:if test="${QVraiFaux}">
			<tr>
				<td><g:link controller="QVraiFaux" action="create">True/False</g:link></td>
			</tr>
			</g:if>
			<g:if test="${QElaboration}">
			<tr>
				<td><g:link controller="QElaboration" action="create">Elaboration</g:link></td>
			</tr>
			</g:if>
			<g:if test="${QLibre}">
			<tr>
				<td><g:link controller="QLibre" action="create">Libre</g:link></td>
			</tr>
			</g:if>

		</table>
		
	</body>
</html>