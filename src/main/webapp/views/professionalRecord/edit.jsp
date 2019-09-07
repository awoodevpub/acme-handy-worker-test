<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="professionalRecord/handyworker/edit.do?curriculumId=${curriculum.id}"
modelAttribute="professionalRecord" >

	<form:hidden path="id" />
	<form:hidden path="version" />


		<form:label path="companyName">
			<spring:message code="professionalRecord.company" />:
		</form:label>
		<form:input path="companyName" />
		<form:errors cssClass="error" path="companyName" />
		<br/>

		<!--  Fechas formularios  -->
		<fmt:formatDate var="startDateWork" value="${startDateWork}" pattern="dd/MM/yyyy" />
		<form:label path="startDateWork">
			<spring:message code="professionalRecord.start" />:
		</form:label>
		<form:input path="startDateWork" />
		<form:errors cssClass="error" path="startDateWork" />
		<br/>

		<fmt:formatDate var="endDateWork" value="${endDateWork}" pattern="dd/MM/yyyy" />
		<form:label path="endDateWork">
			<spring:message code="professionalRecord.end" />:
		</form:label>
		<form:input path="endDateWork" />
		<form:errors cssClass="error" path="endDateWork" />
		<br/>

		<form:label path="rolePlayed">
			<spring:message code="professionalRecord.role" />:
		</form:label>
		<form:input path="rolePlayed" />
		<form:errors cssClass="error" path="rolePlayed" />
		<br/>

		<form:label path="linkAttachment">
			<spring:message code="professionalRecord.attachment" />:
		</form:label>
		<form:input path="linkAttachment" />
		<form:errors cssClass="error" path="linkAttachment" />
		<br/>

		<form:label path="comments">
			<spring:message code="professionalRecord.comments" />:
		</form:label>
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br/>

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript: relativeRedir('/');" />

	</form:form>

</security:authorize>
