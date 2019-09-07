<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<security:authorize access="hasRole('REFEREE')">
	<jstl:set value="referee" var="role"/>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set value="handyworker" var="role"/>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set value="customer" var="role"/>
</security:authorize>



	
	
	<form:form id="form" action="note/${role}/edit.do?reportId=${param.reportId}"
	modelAttribute="note">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="mommentWritten" />

	
	
	
	<security:authorize access="hasRole('REFEREE')">
		 <jstl:set value="referee" var="role"/>
	
	<form:hidden path="customerComments" />
	<form:hidden path="handyWorkerComments" />
	
	<form:label path="refereeComments">
	<spring:message code="note.refereeComments" />
	</form:label>
	
	<form:textarea path="refereeComments" placeholder="Write comments...."/>
		<form:errors cssClass="error" path="refereeComments" />
	
	</security:authorize>
	<br />
	
	<security:authorize access="hasRole('HANDYWORKER')">
		 <jstl:set value="handyworker" var="role"/>
		 
	<form:hidden path="customerComments" />
	<form:hidden path="refereeComments" />
		 
	<form:label path="handyWorkerComments">
	<spring:message code="note.handyWorkerComments" />
	</form:label> 
		 
		<form:textarea path="handyWorkerComments" placeholder="Write a new one"/>
		<form:errors cssClass="error" path="handyWorkerComments" />
	
	</security:authorize>
	
	
		<security:authorize access="hasRole('CUSTOMER')">
		<jstl:set value="customer" var="role"/>
		 
		<form:hidden path="handyWorkerComments" />
		<form:hidden path="refereeComments" />
		 
		<form:label path="customerComments">
		<spring:message code="note.customerComments" />
		</form:label>  
		<form:textarea path="customerComments" placeholder="Write a new one"/>
		<form:errors cssClass="error" path="customerComments" />
	
	</security:authorize>
	
	
	
	
	<br />	
	
	
	<input type="submit" name="save"
	class = "btn"
		value="<spring:message code="note.save" />">

	<br />
	
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		class = "btn"
		onclick="javascript: relativeRedir('note/${role}/list.do?reportId=${param.reportId}')">
</form:form>
	
