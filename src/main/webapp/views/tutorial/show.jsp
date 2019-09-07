<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<h1><jstl:out value="${title}"/></h1>
	<fmt:formatDate var="lastUpdate" value="${lastUpdate}" pattern="yyyy-MM-dd HH:mm" />
	<p><spring:message code="tutorial.lastUpdate" />: <jstl:out value="${lastUpdate}" /></p>
	<p><spring:message code="tutorial.summary" />: <jstl:out value="${summary}"/></p>
	<p><spring:message code="tutorial.written" />: <a href="${pageContext.request.contextPath}/profile/show.do?tutorialId=${id}"><jstl:out value="${authorName}"/></a></p>
	
	<p><spring:message code="tutorial.pictures" />:</p>
	<jstl:forEach var="picture" items="${pictures}">
		<img src="${picture}"/>
	</jstl:forEach>
	
<h2><spring:message code = "tutorial.sections"/></h2>
	<jstl:forEach var="section" items="${sections}">
		<h3><jstl:out value="${section.number}"/> - <jstl:out value="${section.title}"/></h3>
		<p><jstl:out value="${section.text}"/></p>
		<jstl:forEach var="picture" items="${section.pictures}">
			<img src="${picture}"/>
		</jstl:forEach>
		<br>
	</jstl:forEach>
	
	<!-- Sponsorship -->
	<br>
	<a href ="<jstl:out value = "${sponsorship.targetPageLink}"/>"><img src = "<jstl:out value ="${sponsorship.bannerUrl}"/>" /></a>
	<br>
	<br>
	<security:authorize access="hasRole('SPONSOR')">
		<input type="button" name="support"
		value="<spring:message code="tutorial.support" />"
		onclick="javascript: relativeRedir('sponsorship/sponsor/create.do?tutorialId=${id}');" />
	</security:authorize>
	

		<input type="button" name="back"
		value="<spring:message code="tutorial.back" />"
		onclick="javascript: relativeRedir('tutorial/list.do');" />