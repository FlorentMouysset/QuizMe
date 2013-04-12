<%@ page import="user.Professeur" %>



<div class="fieldcontain ${hasErrors(bean: professeurInstance, field: 'prenom', 'error')} required">
	<label for="prenom">
		<g:message code="professeur.prenom.label" default="Prenom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="prenom" required="" value="${professeurInstance?.prenom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: professeurInstance, field: 'nom', 'error')} required">
	<label for="nom">
		<g:message code="professeur.nom.label" default="Nom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nom" required="" value="${professeurInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: professeurInstance, field: 'sexe', 'error')} ">
	<label for="sexe">
		<g:message code="professeur.sexe.label" default="Sexe" />
		
	</label>
	<g:select name="sexe" from="${professeurInstance.constraints.sexe.inList}" value="${professeurInstance?.sexe}" valueMessagePrefix="professeur.sexe" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: professeurInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="professeur.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${professeurInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: professeurInstance, field: 'dateDeNaissance', 'error')} required">
	<label for="dateDeNaissance">
		<g:message code="professeur.dateDeNaissance.label" default="Date De Naissance" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateDeNaissance" precision="day"  value="${professeurInstance?.dateDeNaissance}"  />
</div>

