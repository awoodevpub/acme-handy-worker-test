<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

 
	
	 <p><spring:message code="complaint.message"/></p>
	 
	 
	 <!-- La idea es que "complaint" sea la variable que contiene el valor de la complaint a la que se
	 quiere hacer show.Intuyo que eso se hará en el controlador y que dicha variable complaint deberá definirse ahí,
	 sino ni puta idea de como coger el dato de una complaint concreta a la que supuestamente estoy apuntando xD.--> 
	 
	  <spring:message code = "complaint.ticker"/>
			<jstl:out value="${complaint.getTicker()}" />
	<br />
	  <spring:message code = "complaint.moment"/>
			<jstl:out value="${complaint.getMoment()}" />
	<br />
		  <spring:message code = "complaint.description"/>
			<jstl:out value="${complaint.getDescription()}" />
	<br />
		  <spring:message code = "complaint.attachments"/>
			<jstl:out value="${complaint.getAttachments()}" />
	<br />
	
	
	
	<br />
	
	<security:authorize access="hasRole('CUSTOMER')">
		 <jstl:set value="customer" var="role"/>
		
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:set value="handyworker" var="role"/>
	</security:authorize>
	
	<security:authorize access="hasRole('REFEREE')">
		<jstl:set value="referee" var="role"/>
	</security:authorize>
	 
    
    
   
		<input type="button" name="listReports"
		value="<spring:message code="complaint.reports" />"
		onclick="javascript: relativeRedir
		('report/${role}/list.do?complaintId=${complaint.getId()}')" />
    
    
    
  
	<a href="<%=request.getHeader("Referer")%>">
	<spring:message code = "complaint.back"/></a>
	
	