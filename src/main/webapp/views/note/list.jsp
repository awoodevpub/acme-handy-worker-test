<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
     
    
     <p><spring:message code="note.list"/></p>
    
    <security:authorize access="hasRole('CUSTOMER')">
    <jstl:set value="customer" var ="role" />
    </security:authorize>
    
    <security:authorize access="hasRole('HANDYWORKER')">
    <jstl:set value="handyworker" var ="role" />
    </security:authorize>
    
    <security:authorize access="hasRole('REFEREE')">
    <jstl:set value="referee" var ="role" />
    </security:authorize>

    <table >
	    <tr>
	    	<th><spring:message code="note.moment" /></th>
	    	<th><spring:message code="note.refereeComments" /></th>
	    	<th><spring:message code="note.customerComments" /></th>
	    	<th><spring:message code="note.handyWorkerComments" /></th>
	    	<th><spring:message code="note.addComment" /></th>
	    	
	    </tr>
	     <jstl:forEach items="${notes}" var="note">
	    <tr>
	    	<td><jstl:out value="${note.mommentWritten}" /></td>
	    	<td><jstl:out value="${note.refereeComments}" /></td>
	    	<td><jstl:out value="${note.customerComments}" /></td>
	    	<td><jstl:out value="${note.handyWorkerComments}" /></td>
	    	<td><input type="button" name="create"
						value="<spring:message code="note.create" />"
						onclick="javascript: relativeRedir('note/${role }/edit.do?reportId=${param.reportId}&noteId=${note.getId()}');" /></td>
	    </tr>
    	</jstl:forEach>
    </table>
	
    

	<spring:message code = "report.createNote"/>
  	<input type="button" name="createNote"
		value="<spring:message code="note.create" />"
		onclick="javascript: relativeRedir
		('note/${role}/create.do?reportId=${param.reportId}')" />

    