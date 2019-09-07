<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
     
    
     <p><spring:message code="report.list"/></p>
    
    <security:authorize access="hasRole('CUSTOMER')">
    <jstl:set value="customer" var ="role" />

    <table >
	    <tr>
	    	<th><spring:message code="report.moment" /></th>
	    	<th><spring:message code="report.description" /></th>
	    	<th><spring:message code="report.linkAttachments" /></th>
	    	<th><spring:message code="report.show" /></th>
	    	
	    </tr>
	     <jstl:forEach items="${reports}" var="report">
	     <jstl:if test="${report.isFinalMode}">
	    <tr>
	    	<td><jstl:out value="${report.getMomentWritten()}" /></td>
	    	<td><jstl:out value="${report.getDescription()}" /></td>
	    	<td><jstl:out value="${report.getLinkAttachments() }" /></td>
	    	<td><input type="button" name="showReport"
					value="<spring:message code="report.show" />"
					onclick="javascript: relativeRedir
					('report/${role}/show.do?reportId=${report.getId()}')" /></td>
	    </tr>
	    </jstl:if>
    	</jstl:forEach>
    </table>
    </security:authorize>
	
    <security:authorize access="hasRole('HANDYWORKER')">
    <jstl:set value="handyworker" var ="role" />

    <table >
	    <tr>
	    	<th><spring:message code="report.moment" /></th>
	    	<th><spring:message code="report.description" /></th>
	    	<th><spring:message code="report.linkAttachments" /></th>
	    	<th><spring:message code="report.show" /></th>
	    	
	    </tr>
	     <jstl:forEach items="${reports}" var="report">
	     <jstl:if test="${report.isFinalMode}">
	    <tr>
	    	<td><jstl:out value="${report.getMomentWritten()}" /></td>
	    	<td><jstl:out value="${report.getDescription()}" /></td>
	    	<td><jstl:out value="${report.getLinkAttachments() }" /></td>
	    	<td><input type="button" name="showReport"
					value="<spring:message code="report.show" />"
					onclick="javascript: relativeRedir
					('report/${role}/show.do?reportId=${report.getId()}')" /></td>
	    </tr>
	    </jstl:if>
    	</jstl:forEach>
    </table>
    </security:authorize>
    
    <br />
    
    <security:authorize access="hasRole('REFEREE')">
		<jstl:set value="referee" var="role"/>
		
		<table>
		    <tr>
		    	<th><spring:message code="report.moment" /></th>
		    	<th><spring:message code="report.description" /></th>
		    	<th><spring:message code="report.linkAttachments" /></th>
		    	<th><spring:message code="report.finalMode" /></th>
		    	<th><spring:message code="report.show" /></th>
		    </tr>
		     <jstl:forEach items="${reports}" var="report">
		    <tr>
		    	<td><jstl:out value="${report.getMomentWritten()}" /></td>
		    	<td><jstl:out value="${report.getDescription()}" /></td>
		    	<td><jstl:out value="${report.getLinkAttachments()}" /></td>
		    	<td><jstl:out value="${report.getIsFinalMode()}" /></td>
		    	<td><input type="button" name="showReport"
					value="<spring:message code="report.show" />"
					onclick="javascript: relativeRedir
					('report/${role}/show.do?reportId=${report.getId()}')" /></td>
		    </tr>
			
	    	</jstl:forEach>
  	  </table>
		
		
		
		<br />
		<input type="button" name="createReport"
		value="<spring:message code="complaint.createReport" />"
		onclick="javascript: relativeRedir
		('report/${role}/create.do?complaintId=${param.complaintId}')" />
		

	
	</security:authorize>
    
    

    