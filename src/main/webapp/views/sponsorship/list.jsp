<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('SPONSOR')">
<display:table name = "sponsorships" id = "row" 
	requestURI = "${requestURI}" pagesize = "${pagesize}" class = "displaytag">

	<display:column property = "bannerUrl" titleKey = "sponsorship.bannerUrl" />
	<display:column property = "targetPageLink" titleKey = "sponsorship.targetPageLink"/>
	<display:column property = "creditCard.number" titleKey = "sponsorship.creditCard"/>
	
	<display:column>
	<input type="button" name="edit"
		value="<spring:message code="sponsorship.edit" />"
		onclick="javascript: relativeRedir('sponsorship/sponsor/edit.do?sponsorshipId=${row.id}');" />	
	</display:column>

</display:table>

	<input type="button" name="create"
		value="<spring:message code="sponsorship.create" />"
		onclick="javascript: relativeRedir('tutorial/list.do');" />
</security:authorize>