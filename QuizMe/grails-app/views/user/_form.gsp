<%@ page import="user.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'prenom', 'error')} required">
	<label for="prenom">
		<g:message code="user.prenom.label" default="Prenom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="prenom" required="" value="${userInstance?.prenom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'nom', 'error')} required">
	<label for="nom">
		<g:message code="user.nom.label" default="Nom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nom" required="" value="${userInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'sexe', 'error')} ">
	<label for="sexe">
		<g:message code="user.sexe.label" default="Sexe" />
		
	</label>
	<g:select name="sexe" from="${userInstance.constraints.sexe.inList}" value="${userInstance?.sexe}" valueMessagePrefix="user.sexe" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="user.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${userInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'dateDeNaissance', 'error')} required">
	<label for="dateDeNaissance">
		<g:message code="user.dateDeNaissance.label" default="Date De Naissance" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateDeNaissance" precision="day"  value="${userInstance?.dateDeNaissance}"  />
</div>

