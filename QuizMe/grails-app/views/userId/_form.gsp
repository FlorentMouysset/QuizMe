<%@ page import="user.UserId" %>



<div class="fieldcontain ${hasErrors(bean: userIdInstance, field: 'login', 'error')} required">
	<label for="login">
		<g:message code="userId.login.label" default="Login" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="login" maxlength="15" required="" value="${userIdInstance?.login}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userIdInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="userId.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="password" maxlength="15" required="" value="${userIdInstance?.password}"/>
</div>

