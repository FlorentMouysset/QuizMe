<%@ page import="quizme.Room" %>



<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'admin', 'error')} required">
	<label for="admin">
		<g:message code="room.admin.label" default="Admin" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="admin" name="admin.id" from="${user.Professeur.list()}" optionKey="id" required="" value="${roomInstance?.admin?.id}" class="many-to-one"/>
</div>

