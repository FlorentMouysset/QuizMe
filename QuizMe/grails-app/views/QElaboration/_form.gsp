<%@ page import="questions.QElaboration" %>

	
	
<div class="fieldcontain ${hasErrors(bean: QElaborationInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="QElaboration.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	
	
	<g:textField name="enonce" required=""  value="${QElaborationInstance?.enonce}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: QElaborationInstance, field: 'reponses', 'error')} ">
	<label for="reponses">
		<g:message code="QElaboration.reponses.label" default="Reponses" />
	</label>
	
<ul class="one-to-many">
<g:each in="${QElaborationInstance?.reponses?}" var="r">
    <li><g:link controller="reponse" action="show" id="${r.id}">${r.answer?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
	
</li>
</ul>
</div>