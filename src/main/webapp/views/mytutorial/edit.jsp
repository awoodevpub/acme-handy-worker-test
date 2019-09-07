<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="mytutorial/handyworker/edit.do"
	modelAttribute="tutorial">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdated" format="{0,date,dd/MM/yyyy HH:mm}" />
	<form:hidden path="sections" />
	<form:hidden path="sponsorships" />
	<form:hidden path="handyWorker" />

	<form:label path="tittle">
		<spring:message code="tutorial.title" />
	</form:label>
	<form:input path="tittle" />
	<form:errors cssClass="error" path="tittle" />
	<br />

	<form:label path="summary">
		<spring:message code="tutorial.summary" />
	</form:label>
	<form:input path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />

	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<form:textarea path="pictures" placeholder="www.ejemplo.com"/>
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />


	<input type = "submit" name="save"  value = "<spring:message code = "tutorial.save"/>" />

	<input type="button" name="back"
		value="<spring:message code="tutorial.cancel" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/show.do?tutorialId=${tutorialId}');" />

	<jstl:if test="${tutorial.id!=0}">
		<input
			type="submit"
			name="delete"
			value="<spring:message code="tutorial.delete" />"
			onclick="return confirm('<spring:message code='tutorial.confirm.delete' />') " />
	</jstl:if>


</form:form>
</security:authorize>
