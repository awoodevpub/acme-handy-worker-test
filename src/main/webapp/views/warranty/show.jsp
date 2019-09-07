<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="warranty" id="row" requestURI="warranty/list.do" class="displaytag">
	<display:column property="TITLE" titleKey="warranty.title"/>
	<display:column property="TERMS" titleKey="warranty.terms"/>
	<display:column property="LAWS" titleKey="warranty.laws"/>
	<display:column property="FINAL MODE" titleKey="warranty.finalMode"/>
	<display:column property="EDIT" >
	<input type="submit" name="edit" value="<spring:message
		 code="warranty/edit.do?warranty_id=${row.id}" /> "/> <%-- how do i know the id? --%>
	</display:column>

	<input type="submit" name="create" value="<spring:message
		 code="warranty/create.do" /> "/>
</display:table>