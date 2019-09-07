<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="isAuthenticated()">
<form:form action="${actionURL}" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSystemBox" />
	<form:hidden path="messages" />
	
	<form:label path="name">
		<spring:message code="box.name"/>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="box.save" />" class = "btn">
	<input type="button" name="cancel"	value="<spring:message code="box.cancel" />" class = "btn" onclick ="javascript: relativeRedir('box/list.do');">
	
</form:form>
</security:authorize>