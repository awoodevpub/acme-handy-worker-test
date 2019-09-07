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



<jstl:if test="${advise == true}">

<spring:message code="finder.advise" var="adviseAccess" />
<h3>
<jstl:out value="${adviseAccess}"></jstl:out>
</h3>

</jstl:if>

<display:table pagesize="${pagesize}" class="displaytag" requestURI="finder/handyworker/show.do" name="fixUpTasks" id="row"> 



		<spring:message code="finder.maxPrice" var="maxPrice" />
		<display:column titleKey="fixuptask.maximumPrice">
		  ${row.maximumPrice}$ (${row.maximumPrice + (row.maximumPrice * 0.21)}$ IVA)
		  </display:column>
	
	
		<spring:message code="finder.minDate" var="minDate" />
	<display:column property="startDate" title="${minDate}" />
	
	<display:column titleKey="finder.fixUpTask.aplication" >
    <a href="application/handyworker/edit.do?fixUpTaskId=${row.id}"><spring:message code="application.create"/></a>
    </display:column>

	
	
</display:table>



<input type="button" name="search" value="<spring:message code="finder.search" />" class = "btn" onclick ="javascript: relativeRedir('finder/handyworker/search.do?finderId=${finder.id}');">

</security:authorize>

