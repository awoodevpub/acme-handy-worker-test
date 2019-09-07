<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<display:table name="actor" id="row"
	requestURI="actor/suspiciousList.do" class="displaytag">

	<h1>
		<spring:message code="actor.name" />
	</h1>
	<display:column property="name" titleKey="actor.name" />
	<display:column property="surname" titleKey="actor.surname" />
	<display:column property="email" titleKey="actor.email" />
	<display:column property="isSuspicious" titleKey="actor.isSuspicious" />
	<display:column>
	<input type="button" name="edit"
		value="<spring:message code="actor.show" />"
		onclick="javascript: relativeRedir('actor/administrator/show.do?actorId=${row.id}');" />
	</display:column>



</display:table>
<%-- filter TODO --%>



