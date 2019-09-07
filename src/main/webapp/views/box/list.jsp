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

<security:authorize access="isAuthenticated()">
	<display:table name="boxes" id="row" requestURI="box/list.do" pagesize="${pagesize}" class = "displaytag">
		<spring:message code="box.name" var="boxName"/>
		<display:column property="name" title="${boxName}"/>
		<display:column>
			<input type="button" name="show" value="<spring:message code="box.show" />" class = "btn" onclick ="javascript: relativeRedir('box/show.do?boxId=${row.id}');">
		</display:column>
			<display:column>
			<jstl:if test="${!row.isSystemBox}">
				<input type="button" name="edit" value="<spring:message code="box.edit" />" class = "btn" onclick ="javascript: relativeRedir('box/edit.do?boxId=${row.id}');">
			</jstl:if>
			</display:column>
			<display:column>
			<jstl:if test="${!row.isSystemBox}">
				<input type="button" name="delete"	value="<spring:message code="box.delete" />" class = "btn" onclick ="javascript: relativeRedir('box/delete.do?boxId=${row.id}');">
			</jstl:if>
			</display:column>
	</display:table>
	<input type="button" name="create"	value="<spring:message code="box.create" />" class = "btn" onclick ="javascript: relativeRedir('box/create.do');">
	<input type="button" name="createMessage"	value="<spring:message code="m.create" />" class = "btn" onclick ="javascript: relativeRedir('message/create.do');">
	<security:authorize access="hasRole('ADMIN')">
		<input type="button" name="createBroadcast"	value="<spring:message code="m.createBroadcast" />" class = "btn" onclick ="javascript: relativeRedir('message/createBroadcast.do');">
	</security:authorize>
</security:authorize>