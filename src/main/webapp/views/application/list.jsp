<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="applications" requestURI="${requestURI}" pagesize="${pagesize}" class="displaytag" id="row">

	<h1>
		<spring:message code="applications.title" />
	</h1>

	<p>${row.fixUpTask.endDate}</p>
	<p>${fecha}</p>

	<display:column property="momentOfRegistry" titleKey="application.momentOfRegistry" format="{0,date,dd/MM/yyyy}" />
	<display:column titleKey="application.status">
	
		<jstl:if test="${row.status eq 'ACCEPTED'}">
			<p style ="color: green;">${row.status}</p>
		</jstl:if>
		<jstl:if test="${row.status eq 'REJECTED'}">
			<p style ="color: orange;">${row.status}</p>
		</jstl:if>
	
		<jstl:if test="${row.status eq 'PENDING'}">
			<jstl:choose>
			  <jstl:when test="${fecha > row.fixUpTask.endDate}">
			  	<p style ="color: gray;">${row.status}</p>
			  </jstl:when>
			  <jstl:otherwise>
			   	${row.status}
			  </jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
	<display:column titleKey="application.offeredPrice">
		${row.offeredPrice}$ (${row.offeredPrice + (row.offeredPrice * 0.21)}$ IVA)
	</display:column>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column property="comment" titleKey="application.comment" />

		<display:column titleKey="application.actions">
		<jstl:if test="${row.status eq 'PENDING'}">

				<input type="button" name="accept"
				value="<spring:message code="application.accept" />"
				onclick="javascript: relativeRedir('application/customer/edit.do?applicationId=${row.id}&applicationStatus=ACCEPTED');" />

				<input type="button" name="reject"
				value="<spring:message code="application.reject" />"
				onclick="javascript: relativeRedir('application/customer/edit.do?applicationId=${row.id}&applicationStatus=REJECTED');" />

		</jstl:if>
		</display:column>

	</security:authorize>
	<!-- Handy workers can view his applications details -->
	<h1>
		<spring:message code="application.createTitle" />
	</h1>

	<security:authorize access="hasRole('HANDYWORKER')">

		<display:column titleKey="application.actions">
			<input type="button" name="view"
			value="<spring:message code="application.view" />"
			onclick="javascript: relativeRedir('application/handyworker/show.do?applicationId=${row.id}');" />

			<jstl:if test="${row.status eq 'ACCEPTED'}">
				<input type="button" name="workplan"
				value="<spring:message code="application.workplan" />"
				onclick="javascript: relativeRedir('workplan/handyworker/list.do?applicationId=${row.id}');" />
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>

<br>

<security:authorize access="hasRole('CUSTOMER')">
<jstl:set var = "rol" value = "customer" />

<input type="button" name="back"
				value="<spring:message code="application.back" />"
				onclick="javascript: relativeRedir('fixuptask/${rol}/list.do');" />
</security:authorize>
