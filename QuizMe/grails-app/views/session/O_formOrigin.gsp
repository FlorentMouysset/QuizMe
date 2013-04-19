<%@ page import="quizme.Session" %>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'etat', 'error')} required">
	<label for="etat">
		<g:message code="session.etat.label" default="Etat" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="etat" from="${quizme.SessionEtat?.values()}" keys="${quizme.SessionEtat.values()*.name()}" required="" value="${sessionInstance?.etat?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="session.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${sessionInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'questions', 'error')} ">
	<label for="questions">
		<g:message code="session.questions.label" default="Questions" />
		
	</label>
	<g:select name="questions" from="${questions.Question.list()}" multiple="multiple" optionKey="id" size="5" value="${sessionInstance?.questions*.id}" class="many-to-many"/>
</div>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'etat', 'error')} required">
	<label for="etat">
		<g:message code="session.etat.label" default="Etat" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="etat" from="${quizme.QuestionType?.values()}" keys="${quizme.QuestionType.values()*.name()}" required="" value=""/>
</div>



