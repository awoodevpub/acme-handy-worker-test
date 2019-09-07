<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><jstl:out value="${title}"/></h1>

	<fmt:formatDate var="lastUpdate" value="${tutorial.lastUpdated}" pattern="yyyy-MM-dd HH:mm" />
	<p><spring:message code="tutorial.lastUpdate" />: <jstl:out value="${lastUpdate}" /></p>
	<p><spring:message code="tutorial.summary" />: <jstl:out value="${tutorial.summary}"/></p>

	<p><spring:message code="tutorial.pictures" />:</p>
	<jstl:forEach var="picture" items="${tutorial.pictures}">
		<img src="${picture}"/>
	</jstl:forEach>


	<jstl:if test="${not empty tutorial.sections}">
		<input type="button" name="edit"
		value="<spring:message code="tutorial.edit" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/edit.do?tutorialId=${tutorial.id}');" />

		<h2><spring:message code = "tutorial.sections"/></h2>
		<jstl:forEach var="section" items="${tutorial.sections}">
			<h3><jstl:out value="${section.number}"/> - <jstl:out value="${section.title}"/></h3>
			<p><jstl:out value="${section.text}"/></p>
			<jstl:forEach var="picture" items="${section.pictures}">
				<img src="${picture}"/>
			</jstl:forEach>
			<input type="button" name="edit"
			value="<spring:message code="tutorial.edit" />"
			onclick="javascript: relativeRedir('section/handyworker/edit.do?sectionId=${section.id}&tutorialId=${tutorial.id}');" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<jstl:if test="${empty tutorial.sections}">
		<h3><spring:message code="tutorial.notice"/></h3>
	</jstl:if>

		<input type="button" name="add"
		value="<spring:message code="tutorial.section.add" />"
		onclick="javascript: relativeRedir('section/handyworker/create.do?tutorialId=${tutorial.id}');" />
		<br>


		<input type="button" name="back"
		value="<spring:message code="tutorial.back" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/list.do');" />
