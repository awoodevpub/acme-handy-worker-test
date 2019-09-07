<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


    <p><spring:message code="report.message"/></p>


 <security:authorize access="hasRole('CUSTOMER')">
	<jstl:set value="customer" var="role"/>
	
	<jstl:if test="${report.getIsFinalMode()}">
		
		<spring:message code = "report.moment"/>
		<jstl:out value="${report.getMomentWritten()}"  />
		<br />
		<spring:message code = "report.description"/>
		<jstl:out value="${report.getDescription()}"  />
		<br />
		<spring:message code = "report.linkAttachments"/>
		<jstl:out value="${report.getLinkAttachments()}"  />

	
	</jstl:if>
	
	</security:authorize>
    
    
    <security:authorize access="hasRole('REFEREE')" >
    <jstl:set value="referee" var="role"/>

	 <spring:message code = "report.moment"/>
	<jstl:out value="${report.getMomentWritten()}"  />
	<br />
	<spring:message code = "report.description"/>
	<jstl:out value="${report.getDescription()}"  />
	<br />
	<spring:message code = "report.linkAttachments"/>
	<jstl:out value="${report.getLinkAttachments()}"  />
	
	
	<br />
	
	  <jstl:if test="${!report.getIsFinalMode()}">
	<input type="button" name="edit"
		value="<spring:message code="report.edit" />"
		onclick="javascript: relativeRedir
		('report/${role}/edit.do?reportId=${report.getId()}')" />
    
	</jstl:if>
    </security:authorize>
  
  
  
  
  <security:authorize access="hasRole('HANDYWORKER')" >
  <jstl:set value="handyworker" var="role"/>
  
   		<spring:message code = "report.moment"/>
		<jstl:out value="${report.getMomentWritten()}"  />
		<br />
		<spring:message code = "report.description"/>
		<jstl:out value="${report.getDescription()}"  />
		<br />
		<spring:message code = "report.linkAttachments"/>
		<jstl:out value="${report.getLinkAttachments()}"  />
  		
  
  </security:authorize>
  <br />
  <jstl:if test="${report.getIsFinalMode()}" >
	<spring:message code = "note.list"/>
  	<input type="button" name="listNotes"
		value="<spring:message code="note.list" />"
		onclick="javascript: relativeRedir
		('note/${role}/list.do?reportId=${report.getId()}')" />
	</jstl:if>
  	<br />
  	<div>
	<a href="report/${role}/list.do?complaintId=${report.getComplaint().getId()}">
	<spring:message code = "report.back"/>
	</a>
	</div>