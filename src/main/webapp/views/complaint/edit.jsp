<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set value="customer" var="role"/>
	
	<form:form id="form" action="complaint/customer/edit.do?fixUpTaskId=${param.fixUpTaskId}"
	modelAttribute="complaint">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="reports" />
	<form:hidden path="ticker" />
	
		<form:label path="description">
		<spring:message code="complaint.description" />
	</form:label>
		<form:textarea path="description" placeholder="Write Description...."/>
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="complaint.attachments" />
	</form:label>
	<form:textarea path="attachments" placeholder="Write a new one"/>
	<form:errors cssClass="error" path="attachments" />
	<br />	
	
	<input type = "submit" class = "btn" name = "save"  value = "<spring:message code = "complaint.save"/>" />
	<input type = "button" class = "btn" name = "cancel" value = "<spring:message code = "complaint.cancel" />" 
			onclick="javascript: relativeRedir('complaint/${role}/list.do');"/>
	
</form:form>
	
</security:authorize>