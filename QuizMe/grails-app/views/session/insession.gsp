<%@ page import="quizme.Session"%>
<%@ page import="questions.Question"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'session.label', default: 'Session')}" />
<title>Vous êtez dans la session ${sessionInstance?.nom}</title>
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
			Vous êtez dans la session
			${sessionInstance?.nom}
		</h1>
		<g:form method="post">

			<% cpt=0 %>
			<g:each in="${sessionInstance?.questions}" var="question">
				<%cpt ++ %>


				<div class="fieldcontain">
					<fieldset class="label">
						<label for="questions"> Enonce de la question <%=cpt %> :
						</label>
						${question.getEnonce()}
					</fieldset>

					<g:if test="${Question.isMultiChoix(question.id) }">
						<g:each in="${question?.reponses}" var="reponse">
							<fieldset class="checkBox">
								${reponse.answer}
								<g:checkBox name="${question.id}-${reponse.id}"
									value="${false} " />
							</fieldset>
						</g:each>
					</g:if>


					<g:elseif test="${Question.isTrueFalse(question.id) }">
						<g:radioGroup name="${question.id}" labels="['Oui','Non']"
							values="[1,0]" value="1" >
							<p>
								${it.label}
								${it.radio}
							</p>
						</g:radioGroup>
					</g:elseif>

					<g:elseif test="${Question.isQLibre(question.id) }">
						<g:textField name="${question.id}"/>
					</g:elseif>

				</div>
			</g:each>


			<fieldset class="buttons">
				<g:actionSubmit class="save" action="validation" value="Terminez" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>
