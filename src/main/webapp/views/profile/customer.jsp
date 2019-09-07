<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<spring:message code="profile.name" />: <jstl:out value="${customer.name}"/><br>
	<spring:message code="profile.middleName" />: <jstl:out value="${customer.middleName}"/><br>
	<spring:message code="profile.surname" />: <jstl:out value="${customer.surname}"/><br>
	<spring:message code="profile.email" />: <jstl:out value="${customer.email}"/><br>
	<spring:message code="profile.phone" />: <jstl:out value="${customer.phoneNumber}"/><br>
	<spring:message code="profile.address" />: <jstl:out value="${customer.address}"/> <br>
	<spring:message code="profile.score" />: <jstl:out value="${personalScore}"/> <br>

<display:table name="fixUpTasks" id="row" class="displaytag">

	<display:column property = "ticker" titleKey = "fixuptask.ticker" />
	<display:column property="maximumPrice" titleKey="fixuptask.maximumPrice"/>
	<display:column property="startDate" titleKey="fixuptask.startDate" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
</display:table>

<input type="button" name="back" value="<spring:message code="profile.back" />" onclick="javascript: relativeRedir('fixuptask/handyworker/list.do');" />