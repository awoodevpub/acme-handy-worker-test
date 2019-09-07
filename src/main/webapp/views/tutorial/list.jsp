<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name = "tutorials" id = "row" 
	requestURI = "${requestURI}" pagesize = "${pagesize}" class = "displaytag">

	<display:column property = "tittle" titleKey = "tutorial.title" />
	<display:column property = "summary" titleKey = "tutorial.summary"/>
	<display:column property = "lastUpdated" titleKey = "tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<display:column>
		<input type="button" name="show"
			value="<spring:message code="tutorial.show" />"
			onclick="javascript: relativeRedir('tutorial/show.do?tutorialId=${row.id}');" />	
		</display:column>
		
	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<input type="button" name="support"
			value="<spring:message code="tutorial.support" />"
			onclick="javascript: relativeRedir('sponsorship/sponsor/create.do?tutorialId=${row.id}');" />
		</display:column>
	</security:authorize>

</display:table>