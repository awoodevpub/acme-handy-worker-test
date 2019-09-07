<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="isAuthenticated()">
<form:form action="${actionURL}" modelAttribute="m">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="flagSpam"/>
	<form:hidden path="sender"/>
	
	<form:label path="priority">
		<spring:message code="m.priority" />
	</form:label>
	<form:select path="priority">
		<form:option label="HIGH" value="HIGH"/>
		<form:option label="NEUTRAL" value="NEUTRAL"/>
		<form:option label="LOW" value="LOW"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	
	<jstl:if test="${broadcast != 'broadcast'}">
		<form:label path="recipients">
			<spring:message code="m.recipients" />
		</form:label>
		<form:select multiple="true" path="recipients" >
			<form:options items="${actors}" itemLabel="userAccount.username" itemValue="id"/>
		</form:select>
		<form:errors cssClass="error" path="recipients" />
		<br />
	</jstl:if>
	
	<form:label path="subject">
		<spring:message code="m.subject" />
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<spring:message code="m.body" />
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />  

	<form:label path="tags">
		<spring:message code="m.tags" />:
	</form:label>
	<form:textarea path="tags" />
	<form:errors cssClass="error" path="tags" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="m.send" />" />			
	<input type="button" name="cancel" value="<spring:message code="m.cancel" />" onclick="javascript: window.location.replace('box/list.do')" />

</form:form>
</security:authorize>
