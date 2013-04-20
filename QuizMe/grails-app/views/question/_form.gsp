<%@ page import="questions.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="question.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="enonce" required="" value="${questionInstance?.enonce}"/>
</div>