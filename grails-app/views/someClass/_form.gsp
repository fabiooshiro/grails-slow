<%@ page import="slow.SomeClass" %>



<div class="fieldcontain ${hasErrors(bean: someClassInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="someClass.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${someClassInstance?.name}"/>
</div>

