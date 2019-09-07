<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="educationRecord/handyworker/edit.do?curriculumId=${curriculum.id}"
modelAttribute="educationRecord" >

	<form:hidden path="id" />
	<form:hidden path="version" />


		<form:label path="diplomaTitle">
			<spring:message code="educationRecord.diplomaTitle" />:
		</form:label>
		<form:input path="diplomaTitle" />
		<form:errors cssClass="error" path="diplomaTitle" />
		<br/>

		<!--  Fechas formularios  -->
		<fmt:formatDate var="startDateStudy" value="${startDateStudy}" pattern="dd/MM/yyyy" />
		<form:label path="startDateStudy">
			<spring:message code="educationRecord.start" />:
		</form:label>
		<form:input path="startDateStudy" />
		<form:errors cssClass="error" path="startDateStudy" />
		<br/>

		<fmt:formatDate var="endDateStudy" value="${endDateStudy}" pattern="dd/MM/yyyy" />
		<form:label path="endDateStudy">
			<spring:message code="educationRecord.end" />:
		</form:label>
		<form:input path="endDateStudy" />
		<form:errors cssClass="error" path="endDateStudy" />
		<br/>

		<form:label path="institution">
			<spring:message code="educationRecord.institution" />:
		</form:label>
		<form:input path="institution" />
		<form:errors cssClass="error" path="institution" />
		<br/>

		<form:label path="linkAttachment">
			<spring:message code="educationRecord.attachment" />:
		</form:label>
		<form:input path="linkAttachment" />
		<form:errors cssClass="error" path="linkAttachment" />
		<br/>

		<form:label path="comments">
			<spring:message code="educationRecord.comments" />:
		</form:label>
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br/>


	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save" />" />
	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('/');" />
</form:form>
</security:authorize>
