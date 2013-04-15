<%@ page import="quizme.Session" %>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="session.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${sessionInstance?.nom}"/>
</div>

