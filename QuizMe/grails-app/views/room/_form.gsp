<%@ page import="quizme.Room" %>






<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="room.nom.label" default="Nom de la room" />
		
	</label>
	<g:textField name="nom" value="${roomInstance?.nom}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: roomInstance, field: 'mdp', 'error')} ">
	<label for="mdp">
		<g:message code="room.mdp.label" default="Mot de passe de la room" />
		
	</label>
	<g:textField name="mdp" value="${roomInstance?.mdp}"/>
</div>