<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="${pagesize}" class="displaytag" name="boxes" id="row" requestURI="message/move.do">
	
	<spring:message code="m.boxName" var="boxName" />
	<display:column property="name" title="${boxName}"/>
	<display:column>
	<jstl:choose>
		<jstl:when test="${box.name eq row.name}">
			<spring:message code="m.alreadyIn" />
		</jstl:when>
		<jstl:otherwise>
			<input type="button" name="move" value="<spring:message code="m.move" />" class = "btn" onclick ="javascript: relativeRedir('message/saveMove.do?boxId=${row.id}&messageId=${m.id}');">
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
</display:table>