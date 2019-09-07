<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<display:table name="warranty" id="row" requestURI="warranty/list.do"
	class="displaytag">

	<h1>
		<spring:message code="warranty.title" />
	</h1>
	<display:column property="title" titleKey="warranty.title" />
	<display:column property="terms" titleKey="warranty.terms" />
	<display:column property="laws" titleKey="warranty.laws" />
	<display:column property="isFinalMode" titleKey="warranty.finalMode" />
	<display:column>
		<jstl:choose>


			<jstl:when test="${row.isFinalMode eq false}">
				<input type="button" name="edit"
					value="<spring:message code="warranty.edit" />"
					onclick="javascript: relativeRedir('warranty/administrator/edit.do?warrantyId=${row.id}');" />

			</jstl:when>
		</jstl:choose>

	</display:column>
</display:table>



<input type="button" name="create"
	value="<spring:message code="warranty.create" />" class="btn"
	onclick="javascript: relativeRedir('warranty/administrator/create.do');">
