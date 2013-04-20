
<%@ page import="questions.QLibre" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'QLibre.label', default: 'QLibre')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-QLibre" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link controller="authentification" class="home" action="logout" >Logout</g:link></li>
				<li><g:link class="list" action="inroom" controller="room" ><g:message code="Session List"/></g:link></li>
				<%--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="list-QLibre" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="enonce" title="${message(code: 'QLibre.enonce.label', default: 'Enonce')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${QLibreInstanceList}" status="i" var="QLibreInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${QLibreInstance.id}">${fieldValue(bean: QLibreInstance, field: "enonce")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${QLibreInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
