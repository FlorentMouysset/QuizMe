<%@ page import="quizme.Session" %>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="session.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${sessionInstance?.nom}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'etat', 'error')} required">
	<label for="Type de question">
		Type de question
	</label>
<g:select name="etat" from="${questions.QuestionType?.values()}" keys="${questions.QuestionType.values()*.name()}"  value=""/> 
</div>
