
<%@ page import="quizme.Room"%>
<%@ page import="quizme.SessionEtat"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'room.label', default: 'Room')}" />
<title>Vous êtez dans la room ${roomInstance?.nom}</title>
</head>
<body>
	<a href="#show-room" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><g:link controller="authentification" class="home"
					action="logout">Logout</g:link></li>
			<li><g:link class="list" action="list">
					<g:message code="default.list.label" args="[entityName]" />
				</g:link></li>
			<g:if test="${userContextIsEtudiant==false}">
				<li><g:link class="create" action="create">
						<g:message code="default.new.label" args="[entityName]" />
					</g:link></li>
				<li><g:link class="create" action="create" controller="Session">
						Session
					</g:link></li>
			</g:if>
		</ul>
	</div>
	<div id="show-room" class="content scaffold-show" role="main">
		<h1>
			Composition de la room "${roomInstance?.nom}"
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>

		<table>
			<thead>
				<tr>
					<g:sortableColumn property="etat" title="Etat" />
					<g:sortableColumn property="nom"
						title="${message(code: 'session.nom.label', default: 'Nom session')}" />

					<g:sortableColumn property="nbquestion" title="Nombre de questions" />
					<th>Actions</th>

				</tr>
			</thead>
			<tbody>
				<g:each in="${roomInstance?.sessions}" var="s">
					<g:if
						test="${userContextIsEtudiant==false || s.etat == SessionEtat.ELABORATION || s.etat == SessionEtat.FIN  || s.etat == SessionEtat.EN_COURS }">
						<tr>
							<td>
								${s.etat}
							</td>
							<td>
								${s.nom}
							</td>
							<td>
								${s.questions.count }
							</td>
							<g:if test="${userContextIsEtudiant==false}">
								<td><g:link class="edit" action="edit" controller="session"
										id="${s?.id}">
										Edit session
									</g:link> <br /> 
									<g:if test="${s.etat.equals(SessionEtat.CREATION)}">
										<g:link class="edit" action="statechange" controller="session"
											id="${s?.id} ">
										Evoluer vers l'état en cours
										</g:link><br/>
										<g:link class="edit" action="stateelaboration"
											controller="session" id="${s?.id}">
										Evoluer vers l'état élaboration collective
										</g:link>

									</g:if> <g:elseif test="${ ! s.etat.equals(SessionEtat.FIN)}">
										<g:link class="edit" action="statechange" controller="session"
											id="${s?.id}">
										Evoluer l'état
										</g:link>

									</g:elseif></td>
							</g:if>
							<g:else>
								<td><g:link action="enter" id="${s?.id}"
										controller="session">
								Entrer
						</g:link></td>
							</g:else>
						</tr>
					</g:if>
				</g:each>
			</tbody>
		</table>

		<g:if test="${userContextIsEtudiant==false}">
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${roomInstance?.id}" />
					<g:link class="edit" action="edit" id="${roomInstance?.id}">
						<g:message code="default.button.edit.label" default="Edit" />
					</g:link>
					<g:actionSubmit class="delete" action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</g:if>
	</div>
</body>
</html>
