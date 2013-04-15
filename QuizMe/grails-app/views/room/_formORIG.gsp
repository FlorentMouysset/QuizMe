<%@ page import="quizme.Room" %>



<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'admin', 'error')} required">
	<label for="admin">
		<g:message code="room.admin.label" default="Admin" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="admin" name="admin.id" from="${user.Professeur.list()}" optionKey="id" required="" value="${roomInstance?.admin?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'mdp', 'error')} ">
	<label for="mdp">
		<g:message code="room.mdp.label" default="Mdp" />
		
	</label>
	<g:textField name="mdp" value="${roomInstance?.mdp}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="room.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${roomInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'sessions', 'error')} ">
	<label for="sessions">
		<g:message code="room.sessions.label" default="Sessions" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${roomInstance?.sessions?}" var="s">
    <li><g:link controller="session" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="session" action="create" params="['room.id': roomInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'session.label', default: 'Session')])}</g:link>
</li>
</ul>

</div>

