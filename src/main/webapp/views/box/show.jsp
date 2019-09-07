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
<h2><jstl:out value="${box.name}" /></h2>
<display:table pagesize="${pagesize}" class="displaytag" requestURI="box/show.do" name="messages" id="row">
	<spring:message code="m.subject" var="nameHeader" />
	<display:column property="subject" title="${nameHeader}" sortable="true" />
	
	<spring:message code="m.priority" var="priorityHeader" />
	<display:column property="priority" title="${priorityHeader}" sortable="true" />
	
	<spring:message code="m.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" />

	<display:column>
		<input type="button" name="show" value="<spring:message code="m.show" />" class = "btn" onclick ="javascript: relativeRedir('message/show.do?boxId=${box.id}&messageId=${row.id}');">
	</display:column>
	<display:column>
		<input type="button" name="move" value="<spring:message code="m.move" />" class = "btn" onclick ="javascript: relativeRedir('message/move.do?boxId=${box.id}&messageId=${row.id}');">
	</display:column>
</display:table>
<input type="button" name="back" value="<spring:message code="box.back" />" class = "btn" onclick ="javascript: relativeRedir('box/list.do');">
</security:authorize>