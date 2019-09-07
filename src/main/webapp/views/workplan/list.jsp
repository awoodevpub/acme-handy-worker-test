<%@page import="java.util.Date"%>
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

<display:table name="phases" id="row" requestURI="${requestURI}" pagesize="${pagesize}">

	<display:column property="title" titleKey="phase.title" />
	<display:column property="description" titleKey="phase.description" />
	<display:column property="startMoment" titleKey="phase.startMoment" format="{0,date,dd/MM/yyyy}" />
	<display:column property="endMoment" titleKey="phase.endMoment" format="{0,date,dd/MM/yyyy}" />
	<display:column titleKey="phase.actions">
		
				<input type="button" name="edit"
				value="<spring:message code="phase.edit" />"
				onclick="javascript: relativeRedir('workplan/handyworker/edit.do?phaseId=${row.id}&fixUpTaskId=${fixUpTaskId}');" />
				
				<input type="button" name="delete"
				value="<spring:message code="phase.delete" />"
				onclick="javascript: relativeRedir('workplan/handyworker/delete.do?phaseId=${row.id}&fixUpTaskId=${fixUpTaskId}');" />
			
	</display:column>
	 
</display:table>

<a href="workplan/handyworker/create.do?fixUpTaskId=${fixUpTaskId}"><spring:message code="phase.create"/></a>
<a href="application/handyworker/list.do"><spring:message code="phase.back"/></a>