<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
	
	<spring:message code="application.momentOfRegistry" />: <jstl:out value="${application.momentOfRegistry}"/><br>
	<spring:message code="application.status" />: <jstl:out value="${application.status}"/><br>
	<spring:message code="application.offeredPrice" />: <jstl:out value="${application.offeredPrice}"/><br>
	<spring:message code="application.comment" />: <jstl:out value="${application.comment}"/><br>
	<spring:message code="application.status.reason" />: <jstl:out value="${application.stateReason}"/><br>
	
	<br>
	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: relativeRedir('application/handyworker/list.do');" />

</security:authorize>