<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

    
     <p><spring:message code="oblemic.list"/></p>
    
    <security:authorize access="hasRole('CUSTOMER')">
    <jstl:set value="customer" var ="role" />
    
	    <display:table name = "oblemics" id ="row" requestURI ="${requestURI}" class="displaytag">>
		<jstl:set var="calculote" value="${row.status}" />
		<jstl:set var = "gustro" value = "GUSTRO" />
		<jstl:set var = "rentol" value = "RENTOL" />
		
		<jstl:choose>
			<jstl:when test="${calculote eq gustro}">
				<jstl:set var="color" value="LightSteelBlue"></jstl:set>
			</jstl:when>
			<jstl:when test="${calculote eq rentol}">
				<jstl:set var="color" value="Crimson"></jstl:set>
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="color" value="Gray"></jstl:set>
			</jstl:otherwise>
		</jstl:choose>
		
		    <spring:message code = "oblemic.keylet" var = "keyletHeader"/>
			<display:column property = "keylet" titleKey = "oblemic.keylet" style="background-color: ${color}; color: white;" />/>
			
			<spring:message code = "oblemic.status" var = "statusHeader"/>
			<display:column property = "status" titleKey = "oblemic.status" style="background-color: ${color}; color: white;" />/>
	    
		    <display:column titleKey = "oblemic.show" style="background-color: ${color}; color: white;">
			    <input type="button" name="show"
					value="<spring:message code="oblemic.show" />"
					onclick="javascript: relativeRedir
					('oblemic/${role}/show.do?oblemicId=${row.getId()}')" />
			</display:column>
		</display:table>
	</security:authorize>
	
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set value="handyworker" var ="role" />
   <display:table name = "oblemics" id = "row" 
		requestURI = "${requestURI}" pagesize = "${pagesize}" class = "displaytag">
		<jstl:set var="calculote" value="${row.status}" />
		<jstl:set var = "gustro" value = "GUSTRO" />
		<jstl:set var = "rentol" value = "RENTOL" />
		
		<jstl:choose>
			<jstl:when test="${calculote eq gustro}">
				<jstl:set var="color" value="LightSteelBlue"></jstl:set>
			</jstl:when>
			<jstl:when test="${calculote eq rentol}">
				<jstl:set var="color" value="Crimson"></jstl:set>
			</jstl:when>
			<jstl:otherwise>
				<jstl:set var="color" value="Gray"></jstl:set>
			</jstl:otherwise>
		</jstl:choose>
			
		    <spring:message code = "oblemic.keylet" var = "keyletHeader"/>
			<display:column property = "keylet" titleKey = "oblemic.keylet" style="background-color: ${color}; color: white;" />/>
			
			 <spring:message code = "oblemic.body" var = "bodyHeader"/>
			<display:column property = "body" titleKey = "oblemic.body" style="background-color: ${color}; color: white;" />/>
			
			 <spring:message code = "oblemic.picture" var = "pictureHeader"/>
			<display:column property = "picture" titleKey = "oblemic.picture" style="background-color: ${color}; color: white;" />/>
			
			<spring:message code = "oblemic.status" var = "statusHeader"/>
			<display:column property = "status" titleKey = "oblemic.status" style="background-color: ${color}; color: white;" />/>
	    
		</display:table>
		</security:authorize>
		
		<br />
		
<security:authorize access="hasRole('CUSTOMER')">


  <a href="oblemic/customer/create.do?fixUpTaskId=${param.fixUpTaskId}"><spring:message code="fixuptask.create"/></a>

</security:authorize>