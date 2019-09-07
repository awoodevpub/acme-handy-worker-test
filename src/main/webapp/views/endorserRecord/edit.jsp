<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="endorserRecord/handyworker/edit.do?curriculumId=${curriculum.id}" modelAttribute="endorserRecord" >

	<form:hidden path="id" />
	<form:hidden path="version" />


		<form:label path="fullName">
			<spring:message code="endorserRecord.fullName" />:
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName" />
		<br/>

		<form:label path="email">
			<spring:message code="endorserRecord.email" />:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br/>

		<form:label path="phoneNumber">
			<spring:message code="endorserRecord.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber" />
		<br/>

		<form:label path="linkLinkedIn">
			<spring:message code="endorserRecord.linkLinkedIn" />:
		</form:label>
		<form:input path="linkLinkedIn" />
		<form:errors cssClass="error" path="linkLinkedIn" />
		<br/>

		<form:label path="comments">
			<spring:message code="endorserRecord.comments" />:
		</form:label>
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br/>

	<input type="submit" name="save"
		value="<spring:message code="endorserRecord.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="endorserRecord.cancel" />"
		onclick="javascript: relativeRedir('/');" />
</form:form>
</security:authorize>
