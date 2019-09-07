<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="date" class="java.util.Date" />

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set value="handyworker" var="role" />
</security:authorize> 

<form:form action="workplan/handyworker/edit.do" modelAttribute="phase">

	<form:hidden path="id"/>
	<form:hidden path="version" />
	<input type="hidden" name="fixUpTaskId" value="${fixUpTaskId}">
	
	
	<form:label path="title">
	  <spring:message code="phase.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br /><br />
	
	<form:label path="description">
	  <spring:message code="fixuptask.description" />
	</form:label>
	<form:textarea path="description" rows="20" cols="100"/>
	<form:errors cssClass="error" path="description" />
	<br /><br />
	
	<form:label path="startMoment">
	  <spring:message code="phase.startMoment" />
	</form:label>
	<form:input path="startMoment" placeholder="dd/MM/yyyy" />
	<form:errors cssClass="error" path="startMoment" />
	<br /><br />
	
	<form:label path="endMoment">
	  <spring:message code="phase.endMoment" />
	</form:label>
	<form:input path="endMoment" placeholder="dd/MM/yyyy" />
	<form:errors cssClass="error" path="endMoment"/>
	<br />
	<br />
	<br />
	
	
	<a href="/Acme-Handy-Worker/application/handyworker/list.do">Cancel</a>
	<br />
	<br />

	<jstl:if test="${phase.id!=0}">
	<a href="/Acme-Handy-Worker/workplan/handyworker/delete.do?phaseId=${phaseId}&fixUpTaskId=${fixUpTaskId}">Delete</a>
	</jstl:if>
	
	<input type="submit" name="save" 
	value="<spring:message code="phase.save" /> " /> 
	<br />

</form:form>
