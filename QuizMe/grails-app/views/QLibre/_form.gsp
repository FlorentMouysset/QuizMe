<%@ page import="questions.QLibre" %>

<div class="fieldcontain ${hasErrors(bean: QLibreInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="QLibre.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="enonce" required="" value="${QLibreInstance?.enonce}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: QLibreInstance, field: 'reponses', 'error')} ">
	<label for="reponses">
		<g:message code="QLibre.reponses.label" default="Reponses" />
	</label>
	
<ul class="one-to-many">
<g:each in="${QLibreInstance?.reponses?}" var="r">
    <li><g:link controller="reponse" action="show" id="${r.id}">${r.answer?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
</div>