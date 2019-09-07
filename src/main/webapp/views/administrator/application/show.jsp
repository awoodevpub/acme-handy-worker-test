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
	<h1>
		<spring:message code="application.view.title" />
	</h1>
	<spring:message code="application.moment" /><p>: </p><jstl:out value="${moment}"/>
	<spring:message code="application.status" /><p>: </p><jstl:out value="${status}"/>
	<spring:message code="application.price" /><p>: </p><jstl:out value="${oferedPrice}"/>
	<spring:message code="application.moment" /><p>: </p><jstl:out value="${moment}"/>
	<spring:message code="application.comments" /><p>: </p><jstl:out value="${comments}"/>
	<spring:message code="application.status.reason" /><p>: </p><jstl:out value="${statusReason}"/>
	
	
	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: relativeRedir('application/handy-worker/list.do');" />

</security:authorize>