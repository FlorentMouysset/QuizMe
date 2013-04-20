<%@ page import="quizme.Session"%>



<div
	class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'nom', 'error')} ">
	<label for="nom"> <g:message code="session.nom.label"
			default="Nom" />

	</label>
	<g:textField name="sessionNewName" value="${sessionInstance?.nom}" />

</div>

<div class="fieldcontain">
	<label> Ajouter des questions </label>
	<g:javascript> g= document.forms['createForm'].elements['sessionNewName'].value  </g:javascript>

	<g:link class="create" action="createQuestion" id="${g }"
		params="${sessionInstance?.nom}">Créé des questions</g:link>
</div>

<div
	class="fieldcontain ${hasErrors(bean: sessionInstance, field: 'questions', 'error')} ">
	<label for="questions"> <g:message
			code="session.questions.label" default="Questions" />

	</label>
	<ul>
		<g:each in="${listQuestion}" var="nomQ">
			<li><g:link action="show" controller="question" id="${nomQ.id}">
					${nomQ.getEnonce()}
				</g:link></li>
		</g:each>

	</ul>
</div>
