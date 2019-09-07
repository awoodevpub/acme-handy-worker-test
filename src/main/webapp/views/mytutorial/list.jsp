<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
<display:table name = "tutorials" id = "row" 
	requestURI = "${requestURI}" pagesize = "${pagesize}" class = "displaytag">

	<display:column property = "tittle" titleKey = "tutorial.title" />
	<display:column property = "summary" titleKey = "tutorial.summary"/>
	<display:column property = "lastUpdated" titleKey = "tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<display:column>
	<input type="button" name="show"
		value="<spring:message code="tutorial.show" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/show.do?tutorialId=${row.id}');" />
	<jstl:if test="${not empty row.sections}">
	<input type="button" name="edit"
		value="<spring:message code="tutorial.edit" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/edit.do?tutorialId=${row.id}');" />
	</jstl:if>
	</display:column>

</display:table>

	<input type="button" name="create"
		value="<spring:message code="tutorial.create" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/create.do');" />
</security:authorize>