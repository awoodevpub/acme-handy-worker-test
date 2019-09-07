<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="personalRecord/handyworker/edit.do?curriculumId=${curriculum.id}" modelAttribute="personalRecord" >

	<form:hidden path="id" />
	<form:hidden path="version" />

		<form:label path="fullName">
			<spring:message code="personalRecord.fullName" />:
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName" />
		<br/>

		<form:label path="photo">
			<spring:message code="personalRecord.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br/>

		<form:label path="email">
			<spring:message code="personalRecord.email" />:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br/>

		<form:label path="phoneNumber">
			<spring:message code="personalRecord.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber" />
		<br/>

		<form:label path="urlLinkedIn">
			<spring:message code="personalRecord.urlLinkedIn" />:
		</form:label>
		<form:input path="urlLinkedIn" />
		<form:errors cssClass="error" path="urlLinkedIn" />
		<br/>


	<input type="submit" name="save"
		value="<spring:message code="personalRecord.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="personalRecord.cancel" />"
		onclick="javascript: relativeRedir('/');" />
</form:form>
</security:authorize>
