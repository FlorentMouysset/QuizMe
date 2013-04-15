<%@ page import="questions.QMultiChoix" %>



<div class="fieldcontain ${hasErrors(bean: QMultiChoixInstance, field: 'enonce', 'error')} required">
	<label for="enonce">
		<g:message code="QMultiChoix.enonce.label" default="Enonce" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="enonce" required="" value="${QMultiChoixInstance?.enonce}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: QMultiChoixInstance, field: 'professeur', 'error')} required">
	<label for="professeur">
		<g:message code="QMultiChoix.professeur.label" default="Professeur" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="professeur" name="professeur.id" from="${user.Professeur.list()}" optionKey="id" required="" value="${QMultiChoixInstance?.professeur?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: QMultiChoixInstance, field: 'reponses', 'error')} ">
	<label for="reponses">
		<g:message code="QMultiChoix.reponses.label" default="Reponses" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${QMultiChoixInstance?.reponses?}" var="r">
    <li><g:link controller="reponse" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="reponse" action="create" params="['QMultiChoix.id': QMultiChoixInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'reponse.label', default: 'Reponse')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: QMultiChoixInstance, field: 'statut', 'error')} required">
	<label for="statut">
		<g:message code="QMultiChoix.statut.label" default="Statut" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="statut" from="${questions.QStatut?.values()}" keys="${questions.QStatut.values()*.name()}" required="" value="${QMultiChoixInstance?.statut?.name()}"/>
</div>

