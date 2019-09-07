<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<p><spring:message code="profile.name" />: <jstl:out value="${handyworker.name}"/></p>
	<p><spring:message code="profile.middleName" />: <jstl:out value="${handyworker.middleName}"/></p>
	<p><spring:message code="profile.surname" />: <jstl:out value="${handyworker.surname}" /></p>
	<p><spring:message code="profile.picture" />: <img src="${handyworker.photo}" /></p>
	<p><spring:message code="profile.email" />: <jstl:out value="${handyworker.email}"/></p>
	<p><spring:message code="profile.phone" />: <jstl:out value="${handyworker.phoneNumber}"/></p>
	<p><spring:message code="profile.address" />: <jstl:out value="${handyworker.address}"/></p>
	<p><spring:message code="handyworker.make" />: <jstl:out value="${handyworker.make}"/></p>
	<p><spring:message code="profile.score" />: <jstl:out value="${personalScore}" /></p>





	<display:table name="tutorials" id="row" requestURI="${requestURI}" class="displaytag">
		<display:column property = "tittle" titleKey = "tutorial.title" />
		<display:column property = "summary" titleKey = "tutorial.summary"/>
		<display:column property = "lastUpdated" titleKey = "tutorial.lastUpdate" format="{0,date,dd/MM/yyyy HH:mm}" />
		<display:column>
		<input type="button" name="show"
			value="<spring:message code="tutorial.show" />"
			onclick="javascript: relativeRedir('tutorial/show.do?tutorialId=${row.id}');" />
		</display:column>
	</display:table>

		<input type="button" name="back"
		value="<spring:message code="profile.back" />"
		onclick="javascript: relativeRedir('tutorial/show.do?tutorialId=' + ${tutorialId});" />
