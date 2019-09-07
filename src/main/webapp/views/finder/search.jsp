<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="master.page.date.format" var="dateFormat" />
<security:authorize access="isAuthenticated()">   
<security:authorize access="hasRole('HANDYWORKER')">
<form:form action="finder/handyworker/search.do" modelAttribute="finder">

	<spring:message code="finder.searchForFixUpTasks" />
	<br />

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="searchMoment" />
	<form:hidden path="fixUpTasks" />

	<form:label path="keyWord">
		<spring:message code="finder.keyWord" />:
	</form:label>
	<form:input path="keyWord" placeholder="Lorem ipsum" />
	<form:errors cssClass="error" path="keyWord" />
	<br />

	<form:label path="minPrice">
		<spring:message code="finder.minPrice" />:
	</form:label>
	<form:input path="minPrice" placeholder="0.0" />
	<form:errors cssClass="error" path="minPrice" />
	<br />

	<form:label path="maxPrice">
		<spring:message code="finder.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" placeholder="0.0" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />

	<form:label path="minDate">
		<spring:message code="finder.minDate" />:
	</form:label>
	<form:input path="minDate" placeholder="${dateFormat}" />
	<form:errors cssClass="error" path="minDate" />
	<br />

	<form:label path="maxDate">
		<spring:message code="finder.maxDate" />:
	</form:label>
	<form:input path="maxDate" placeholder="${dateFormat}" />
	<form:errors cssClass="error" path="maxDate" />
	<br />

	<form:label path="category">
		<spring:message code="finder.category" />:
	</form:label>
	<form:select path="category">
		<form:option value="0">----</form:option>
		<form:options items="${categories}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />

	<form:label path="warranty">
		<spring:message code="finder.warranty" />:
	</form:label>
	<form:select path="warranty">
		<form:option value="0">----</form:option>
		<form:options items="${warranties}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="warranty" />
	<br />

	<input type="submit" name="search"
		value="<spring:message code="finder.search" />" />

	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onclick="javascript: relativeRedir('finder/handyworker/show.do');" />
</form:form>
</security:authorize>
</security:authorize>
