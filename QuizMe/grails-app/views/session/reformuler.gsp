<%@ page import="quizme.Session"%>
<%@ page import="questions.Question"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'session.label', default: 'Session')}" />
<title>Vous êtes dans la session ${sessionInstance?.nom} en mode
	reformulation
</title>
</head>
<body>
	<a href="#edit-session" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><g:link controller="authentification" class="home"
					action="logout">Logout</g:link></li>
			<li><g:link class="list" action="inroom" controller="room">
					<g:message code="default.list.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div id="edit-session" class="content scaffold-edit" role="main">
		<h1>
			Vous êtes dans la session
			${sessionInstance?.nom}
			en mode élaboration
		</h1>
		<g:form method="post">

			<% cpt=0 %>
			<g:each in="${sessionInstance?.questions}" var="question">


				<g:if test="${Question.isQElaboration(question.id) }">
					<%cpt ++ %>
					<div class="fieldcontain">
						<fieldset class="label">
							<label for="questions"> Enonce de la question <%=cpt %> :
							</label>
							${question.getEnonce()}
						</fieldset>

						<g:each in="${question?.reponses}" var="reponse">
						<fieldset class="checkBox">
						
							<label for="questions"> Proposition:</label>
								${reponse.answer}
								<g:checkBox name="${question.id}-${reponse.id}"/>
							</fieldset>
						</g:each>
					
					</div>
				</g:if>
			</g:each>


			<fieldset class="buttons">
				<g:actionSubmit class="save" action="finreformulation" value="Reformuler" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>
