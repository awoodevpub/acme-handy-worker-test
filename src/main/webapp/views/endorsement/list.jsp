<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
<jstl:set var = "rol" value = "customer"/>
<h3>
<spring:message code="endorsement.givenCustomer" />
</h3>
	<display:table pagesize="${pagesize}" class="displaytag"
		keepStatus="true" name="endorsementsCreatedCustomer" requestURI="endorsement/${row.id}/list.do"
		id="row">


			<spring:message code="endorsement.endorser" var="endorserCustomer" />
			<display:column property="endorserCustomer.name" title="${endorserCustomer}" />


		<spring:message code="endorsement.endorsed" var="endorsedHandyWorker" />
			<display:column property="endorsedHandyWorker.name" title="${endorsedHandyWorker}" />


<spring:message code="endorsement.actions" var="endorserActions" />

<display:column title="${endorserActions}">
			<input type="button" name="show"
				value="<spring:message code="endorsement.show" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/show.do?endorsementId=${row.id}');" />
			<input type="button" name="edit"
				value="<spring:message code="endorsement.edit" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/edit.do?endorsementId=${row.id}');" />
			<input type="button" name="delete"
				value="<spring:message code="endorsement.delete" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/delete.do?endorsementId=${row.id}');" />
		</display:column>


</display:table>
<h3>
<spring:message code="endorsement.receivedCustomer" />
</h3>
<display:table pagesize="${pagesize}" class="displaytag"
		keepStatus="true" name="endorsementsReceivedCustomer" requestURI="endorsement/${row.id}/list.do"
		id="row">

		<spring:message code="endorsement.endorser" var="endorserHandyWorker" />
		<display:column property="endorserHandyWorker.name" title="${endorserHandyWorker}" />

			<spring:message code="endorsement.endorsed" var="endorsedCustomer" />
			<display:column property="endorsedCustomer.name" title="${endorsedCustomer}" />


		<display:column title="${endorserActions}">
			<input type="button" name="show"
				value="<spring:message code="endorsement.show" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/show.do?endorsementId=${row.id}');" />

		</display:column>

</display:table>

<jstl:choose>
<jstl:when test="${empty handyWorkers}">
<h3>
<spring:message code="endorsement.noHandyWorkers"  />
</h3>
</jstl:when>
<jstl:otherwise>
<input type="button" name="create"	value="<spring:message code="endorsement.create" />" class = "btn" onclick ="javascript: relativeRedir('endorsement//${rol}/create.do');">
</jstl:otherwise>
</jstl:choose>

</security:authorize>


<security:authorize access="hasRole('HANDYWORKER')">

<jstl:set var = "rol" value = "handyworker"/>
<h3>
<spring:message code="endorsement.givenHandyWorker" />
</h3>
	<display:table pagesize="${pagesize}" class="displaytag"
		keepStatus="true" name="endorsementsCreatedHandyWorker" requestURI="endorsement/${row.id}/list.do"
		id="row">
		

		<spring:message code="endorsement.endorser" var="endorserHandyWorker" />
		<display:column property="endorserHandyWorker.name" title="${endorserHandyWorker}" />


			<spring:message code="endorsement.endorsed" var="endorsedCustomer" />
			<display:column property="endorsedCustomer.name" title="${endorsedCustomer}" />

			<spring:message code="endorsement.actions" var="endorserActions" />

<display:column title="${endorserActions}">
			<input type="button" name="show"
				value="<spring:message code="endorsement.show" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/show.do?endorsementId=${row.id}');" />
			<input type="button" name="edit"
				value="<spring:message code="endorsement.edit" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/edit.do?endorsementId=${row.id}');" />
			<input type="button" name="delete"
				value="<spring:message code="endorsement.delete" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/delete.do?endorsementId=${row.id}');" />
		</display:column>

</display:table>
<h3>
<spring:message code="endorsement.receivedHandyWorker" />
</h3>
<display:table pagesize="${pagesize}" class="displaytag"
		keepStatus="true" name="endorsementsReceivedHandyWorker" requestURI="endorsement/${row.id}/list.do"
		id="row">

		<spring:message code="endorsement.endorser" var="endorserCustomer" />
			<display:column property="endorserCustomer.name" title="${endorserCustomer}" />

			<spring:message code="endorsement.endorsed" var="endorsedHandyWorker" />
			<display:column property="endorsedHandyWorker.name" title="${endorsedHandyWorker}" />

		<display:column title="${endorserActions}">
			<input type="button" name="show"
				value="<spring:message code="endorsement.show" />"
				onclick="javascript: relativeRedir('endorsement/${rol}/show.do?endorsementId=${row.id}');" />

		</display:column>

</display:table>
<jstl:choose>
<jstl:when test="${empty customers}">
<h3>
<spring:message code="endorsement.noCustomers"  />
</h3>
</jstl:when>
<jstl:otherwise>
<input type="button" name="create"	value="<spring:message code="endorsement.create" />" class = "btn" onclick ="javascript: relativeRedir('endorsement//${rol}/create.do');">
</jstl:otherwise>
</jstl:choose>
</security:authorize>
