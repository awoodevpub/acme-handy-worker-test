<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="curriculum/handyworker/edit.do" modelAttribute="curriculum" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="handyWorker" />
	<form:hidden path="personalRecord" />
	<form:hidden path="miscellaneousRecords" />
	<form:hidden path="professionalRecords" />
	<form:hidden path="endorserRecords" />
	<form:hidden path="educationRecords" />

	<div>
	<b><spring:message code="curriculum.personalRecord"></spring:message>:</b>
	<br />
	<br />
	<form:form modelAttribute="personalRecord">
			<form:hidden path="id" />
			<form:hidden path="version" />

		<form:label path="fullName">
			<spring:message code="personalRecord.fullName" />:
		</form:label>
		<form:input path="fullName" />
		<form:errors cssClass="error" path="fullName" />
		<br/>
		<br/>

		<form:label path="photo">
			<spring:message code="personalRecord.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br/>
		<br/>

		<form:label path="email">
			<spring:message code="personalRecord.email" />:
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br/>
		<br/>

		<form:label path="phoneNumber">
			<spring:message code="personalRecord.phoneNumber" />:
		</form:label>
		<form:input id="phone" path="phoneNumber" />
		<form:errors cssClass="error" path="phoneNumber" />
		<br/>
		<br/>

		<form:label path="urlLinkedIn">
			<spring:message code="personalRecord.urlLinkedIn" />:
		</form:label>
		<form:input path="urlLinkedIn" />
		<form:errors cssClass="error" path="urlLinkedIn" />
		<br/>
		<br/>

	</form:form>

	<input type="submit" name="save"
		value="<spring:message code="curriculum.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="curriculum.cancel" />"
		onclick="javascript: relativeRedir('/');" />
</div>
</form:form>
</security:authorize>
