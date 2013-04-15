<%@ page import="questions.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="question.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="enonce" required="" value="${questionInstance?.enonce}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'professeur', 'error')} required">
	<label for="professeur">
		<g:message code="question.professeur.label" default="Professeur" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="professeur" name="professeur.id" from="${user.Professeur.list()}" optionKey="id" required="" value="${questionInstance?.professeur?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'statut', 'error')} required">
	<label for="statut">
		<g:message code="question.statut.label" default="Statut" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="statut" from="${questions.QStatut?.values()}" keys="${questions.QStatut.values()*.name()}" required="" value="${questionInstance?.statut?.name()}"/>
</div>

