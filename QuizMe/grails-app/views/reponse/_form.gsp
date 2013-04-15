<%@ page import="questions.Reponse" %>



<div class="fieldcontain ${hasErrors(bean: reponseInstance, field: 'answer', 'error')} required">
	<label for="answer">
		<g:message code="reponse.answer.label" default="Answer" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="answer" required="" value="${reponseInstance?.answer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reponseInstance, field: 'question', 'error')} required">
	<label for="question">
		<g:message code="reponse.question.label" default="Question" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="question" name="question.id" from="${questions.Question.list()}" optionKey="id" required="" value="${reponseInstance?.question?.id}" class="many-to-one"/>
</div>

