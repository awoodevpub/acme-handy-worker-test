<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

		<security:authorize access="hasRole('HANDYWORKER')">
	
	<jstl:set var = "rol" value = "handyworker"/>
	
	</security:authorize>
	
	
<security:authorize access="hasRole('CUSTOMER')">

<jstl:set var = "rol" value = "customer"/>
				
</security:authorize>

	<form:form id="form" action="endorsement//${rol}/edit.do?endorsementId=${row.id}" modelAttribute="endorsement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="momentWritten" />
		
	
	<spring:message code="endorsement.name" var="name"/>
	
	
		<security:authorize access="hasRole('CUSTOMER')">
	<form:hidden path="endorserCustomer" />
	<form:hidden path="endorserHandyWorker" />
		<form:hidden path="endorsedCustomer" />
		<jstl:choose>
		<jstl:when test="${endorsement.id == 0}">
<form:label path="endorsedHandyWorker">
		<spring:message code="endorsement.endorsed"/>
	</form:label>
<form:select  path="endorsedHandyWorker">
<form:options items="${handyWorkers}" itemLabel="name" itemValue="id" />

</form:select>
	<form:errors cssClass="error" path="endorsedHandyWorker"/>
	<br/>
	</jstl:when>
	
	<jstl:otherwise>
	<form:hidden path="endorsedHandyWorker" />
	</jstl:otherwise>
	</jstl:choose>
	</security:authorize>	
	
	
		<security:authorize access="hasRole('HANDYWORKER')">
	<form:hidden path="endorserCustomer" />
	<form:hidden path="endorserHandyWorker" />
	<form:hidden path="endorsedHandyWorker" />
	<jstl:choose>
	<jstl:when test="${endorsement.id == 0}">
<form:label path="endorsedCustomer">

		<spring:message code="endorsement.endorsed"/>
	</form:label>
<form:select  path="endorsedCustomer">
<form:options items="${customers}" itemLabel="name" itemValue="id" />

</form:select>
	<form:errors cssClass="error" path="endorsedCustomer"/>
	
	<br/>
	</jstl:when>
	
	<jstl:otherwise>
	<form:hidden path="endorsedCustomer" />
	</jstl:otherwise>
	</jstl:choose>
	</security:authorize>	
	
	
	<form:label path="comments">
		<spring:message code="endorsement.comments"/>
	</form:label>
<form:textarea path="comments"/>
	<form:errors cssClass="error" path="comments"/>
	<br/>
	
	
	

	
	
	<input type="submit" name="save" value="<spring:message code="endorsement.save" />" class = "btn">
	<input type="button" name="cancel"	value="<spring:message code="endorsement.cancel" />" class = "btn" onclick ="javascript: relativeRedir('endorsement/${rol}/list.do');">
	
</form:form>
