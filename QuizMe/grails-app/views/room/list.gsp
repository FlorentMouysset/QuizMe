
<%@ page import="quizme.Room"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'room.label', default: 'Room')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	Bonjour
	${username}
	choisissez une room
	<g:if test="${userContextIsEtudiant==false}">
		<br />Ou creez une nouvelle room		
		</g:if>
	<a href="#list-room" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><g:link controller="authentification" class="home"
					action="logout">Logout</g:link></li>
			<g:if test="${userContextIsEtudiant==false}">
				<li><g:link class="create" action="create">
						<g:message code="default.new.label" args="[entityName]" />
					</g:link></li>
			</g:if>
		</ul>
	</div>
	<div id="list-room" class="content scaffold-list" role="main">
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>

					<g:sortableColumn property="nom"
						title="${message(code: 'room.nom.label', default: 'Nom')}" />

					<g:sortableColumn property="nom" title="Administrateur" />

					<g:if test="${userContextIsEtudiant==false}">
							<th>Action administrateur</th>
					</g:if>

					<th> Action</th>					
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${roomInstanceList}" status="i" var="roomInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">


						<td>
							${fieldValue(bean: roomInstance, field: "nom")}
						</td>
						<td>
							${roomInstance?.admin?.nom}
						</td>
						<g:if test="${userContextIsEtudiant==false}">
							<g:if test="${roomInstance?.admin?.id==userid}">
								<td><g:link class="edit" action="edit"
										id="${roomInstance?.id}">
										Edit ma room
									</g:link></td>
							</g:if>
							<g:else>
								<td></td>
							</g:else>
						</g:if>
						<td><g:link action="enter" 
								id="${roomInstance?.id}">
								Entrer
						</g:link></td>




					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${roomInstanceTotal}" />
		</div>
	</div>
</body>
</html>
