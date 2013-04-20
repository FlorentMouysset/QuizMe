<%@ page import="questions.QVraiFaux" %>



<div class="fieldcontain ${hasErrors(bean: QVraiFauxInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="QVraiFaux.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="enonce" required="" value="${QVraiFauxInstance?.enonce}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: QVraiFauxInstance, field: 'reponses', 'error')} ">
	<label for="reponses">
		<g:message code="QVraiFaux.reponses.label" default="Reponses" />
	</label>
	
<ul class="one-to-many">
<g:each in="${QVraiFauxInstance?.reponses?}" var="r">
    <li><g:link controller="reponse" action="show" id="${r.id}">${r.answer?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
</div>

