<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('REFEREE')">
	<jstl:set value="referee" var="role"/>

	
	
	<form:form id="form" action="report/referee/edit.do"
	modelAttribute="report">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="momentWritten" />
	<form:hidden path="notes" />
	<form:hidden path="referee" />
	<form:hidden path="complaint" />
	

	
		<form:label path="description">
		<spring:message code="report.description" />
	</form:label>
		<form:textarea path="description" placeholder="Write Description...."/>
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="LinkAttachments">
		<spring:message code="report.linkAttachments" />
	</form:label>
	<form:textarea path="linkAttachments" placeholder="Write a new one"/>
	<form:errors cssClass="error" path="linkAttachments" />
	<br />	
	
	<form:label path="isFinalMode">
		<spring:message code="report.finalMode" />
	<form:checkbox path="isFinalMode" value="true" />
	
	</form:label>
	
	<br />
	
	<input type="submit" name="save"
	class = "btn"
		value="<spring:message code="report.save" />">

	<br />
	
	<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		class = "btn"
		onclick="javascript: relativeRedir('report/referee/list.do?complaintId=${report.getComplaint().getId()}')">
</form:form>
	
</security:authorize>