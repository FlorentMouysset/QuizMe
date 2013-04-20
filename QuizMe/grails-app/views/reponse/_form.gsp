<%@ page import="questions.Reponse" %>

<div class="fieldcontain ${hasErrors(bean: reponseInstance, field: 'answer', 'error')} required">
	<label for="answer">
		<g:message code="reponse.answer.label" default="Answer" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="answer" required="" value="${reponseInstance?.answer}"/>
</div>

<div class="fieldcontain">
	<label for="commentaire">
		<g:message code="reponse.commentaire.label" default="Commentaire" />
	</label>
	<g:textField name="commentaire" value="${reponseInstance?.commentaire}"/>
</div>
