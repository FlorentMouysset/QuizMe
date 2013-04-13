<%@ page import="user.Etudiant" %>



<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'prenom', 'error')} required">
	<label for="prenom">
		<g:message code="etudiant.prenom.label" default="Prenom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="prenom" required="" value="${etudiantInstance?.prenom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'nom', 'error')} required">
	<label for="nom">
		<g:message code="etudiant.nom.label" default="Nom" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nom" required="" value="${etudiantInstance?.nom}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'sexe', 'error')} ">
	<label for="sexe">
		<g:message code="etudiant.sexe.label" default="Sexe" />
		
	</label>
	<g:select name="sexe" from="${etudiantInstance.constraints.sexe.inList}" value="${etudiantInstance?.sexe}" valueMessagePrefix="etudiant.sexe" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="etudiant.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${etudiantInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'dateDeNaissance', 'error')} required">
	<label for="dateDeNaissance">
		<g:message code="etudiant.dateDeNaissance.label" default="Date De Naissance" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateDeNaissance" precision="day"  value="${etudiantInstance?.dateDeNaissance}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'identifiants', 'error')} required">
	<label for="identifiants">
		<g:message code="etudiant.identifiants.label" default="Identifiants" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="identifiants" name="identifiants.id" from="${user.UserId.list()}" optionKey="id" required="" value="${etudiantInstance?.identifiants?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: etudiantInstance, field: 'numEtudiant', 'error')} ">
	<label for="numEtudiant">
		<g:message code="etudiant.numEtudiant.label" default="Num Etudiant" />
		
	</label>
	<g:textField name="numEtudiant" value="${etudiantInstance?.numEtudiant}"/>
</div>

