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

<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="${pagesize}" class="displaytag">
	
	<h1>
		<spring:message code="applications.title" />
	</h1>
	
	<display:column property="moment" titleKey="application.moment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<display:column property="status" titleKey="application.status" />
	<display:column property="oferedPrice" titleKey="application.oferedPrice" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column property="comments" titleKey="application.comments" />
		<display:column titleKey="application.actions" />
		
		<!-- If status is PENDING the customer can REJECT it or ACCEPT it -->
		<jstl:if test="${row.status=='PENDING'}">
		<input type="button" name="accept"
		value="<spring:message code="application.accept" />"
		onclick="javascript: relativeRedir('application/customer/edit.do?applicationId=${row.id}&applicationStatus=${row.status=='ACCEPTED'}');" />
		
		<input type="button" name="reject"
		value="<spring:message code="application.reject" />"
		onclick="javascript: relativeRedir('application/customer/edit.do?applicationId=${row.id}&applicationStatus=${row.status=='REJECTED'}');" />
		</jstl:if>
	</security:authorize>
	
	<!-- Handy workers can view his applications details -->
	<h1>
		<spring:message code="application.create.title" />
	</h1>
	<security:authorize access="hasRole('HANDYWORKER')">
	
		<display:column titleKey="application.actions" />
		
		<input type="button" name="view"
		value="<spring:message code="application.view" />"
		onclick="javascript: relativeRedir('application/handy-worker/show.do?applicationId=${row.id}');" />
		
	</security:authorize>
	
</display:table>

