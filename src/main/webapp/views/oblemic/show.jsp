<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

 <fmt:formatDate var = "time" type = "both" dateStyle = "long" timeStyle = "short" value = "${ubera.publicationMoment}" />
	<jstl:set var="es" value="es"></jstl:set>
	
	 <p><spring:message code="oblemic.message"/></p>
	<br />
	
	<security:authorize access="hasRole('CUSTOMER')">
		 <jstl:set value="customer" var="role"/>
		
		
		<spring:message code = "oblemic.keylet"/>
		:
		<jstl:out value="${oblemic.getKeylet()}" />
	<br />
	
		<spring:message code = "oblemic.body"/>
		:
		<jstl:out value="${oblemic.getBody()}" />
	<br />
		<img alt="<spring:message code = "oblemic.picture"/>" src="${oblemic.getPicture()}" style="width: 400px; height: 300px;">
	<br />
		
	<jstl:if test="${!oblemic.getIsFinalMode()}">	
		
		<input type="button" name="edit"
		value="<spring:message code="oblemic.edit" />"
		onclick="javascript: relativeRedir
		('oblemic/${role}/edit.do?oblemicId=${oblemic.getId()}&fixUpTaskId=${taskId}')" />
		<br />
		
		
		
	</jstl:if>
		
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:set value="handyworker" var="role"/>
		
	<spring:message code = "oblemic.keylet"/>
		:
		<jstl:out value="${oblemic.getKeylet()}" />
	<br />
	
		
		<spring:message code = "oblemic.body"/>
		:
		<jstl:out value="${oblemic.getBody()}" />
	<br />
		<img alt="<spring:message code = "oblemic.picture"/>" src="${oblemic.getPicture()}" style="width: 400px; height: 300px;">
	<br />
	
	</security:authorize>
    
  
	<a href="<%=request.getHeader("Referer")%>">
	<spring:message code = "oblemic.back"/></a>
	
	