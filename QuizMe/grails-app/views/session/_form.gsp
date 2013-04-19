<%@ page import="quizme.Session" %>



<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'nom', 'error')} ">
	<label for="nom">
		<g:message code="session.nom.label" default="Nom" />
		
	</label>
	<g:textField name="nom" value="${sessionInstance?.nom}"/>
</div>

<div class="fieldcontain">
 <label>
 	Ajouter des questions
 </label>
<g:link controller="question" class="create" action="create" >Créé des questions</g:link>

</div>

<div class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'questions', 'error')} ">
	<label for="questions">
		<g:message code="session.questions.label" default="Questions" />
		
	</label>
	<g:each in="${listNomNewQuestion}" var="nomQ">
		${nomQ}
	</g:each>
</div>
