<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<security:authorize access="hasRole('ADMIN')">
	<spring:message code="actor.name" /> : <jstl:out
		value="${actor.name }" />
	<br>
	<spring:message code="actor.middleName" /> 	<br>
	<spring:message code="actor.surname" /> : <jstl:out
		value="${actor.surname }" />
	<br>
	<spring:message code="actor.email" /> : <jstl:out
		value="${actor.email }" />
	<br>
	<spring:message code="actor.phoneNumber" /> : <jstl:out
		value="${actor.phoneNumber }" />
	<br>
	<spring:message code="actor.score" /> : <jstl:out
		value="${score }" />
	<br>
	

	<jstl:choose>

		<jstl:when test="${actor.userAccount.statusAccount eq false}">
			
			<br>
			<a href="actor/administrator/allow.do?actorId=${actor.id}"><spring:message
					code="actor.allow" /></a>

		</jstl:when>

		<jstl:otherwise>
			
			<a href="actor/administrator/ban.do?actorId=${actor.id}"><spring:message
					code="actor.ban" /></a>

		</jstl:otherwise>

	</jstl:choose>

</security:authorize>
