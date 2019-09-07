<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set value="customer" var="role"/>
	
	<form:form id="form" action="oblemic/${role}/edit.do?fixUpTaskId=${param.fixUpTaskId}" modelAttribute="oblemic">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="keylet" />
	
		<form:label path="body">
		<spring:message code="oblemic.body" />
	</form:label>
		<form:textarea path="body" placeholder="Write body...."/>
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="picture">
		<spring:message code="oblemic.picture" />
	</form:label>
	<form:textarea path="picture" placeholder="Put another picture"/>
	<form:errors cssClass="error" path="picture" />
	<br />	
	
	<form:label path="isFinalMode">
		<spring:message code="oblemic.finalMode" />
	
	<form:select path="isFinalMode" name="isFinalMode">
		<form:option value="false"> false</form:option>
		<form:option value="true"> true</form:option>
	</form:select>
	</form:label>
	
	<form:label path="status">
		<spring:message code="oblemic.status" />
		
	<form:select path="status" name="status">
		<form:option value="GUSTRO">GUSTRO</form:option>
		<form:option value="RENTOL">RENTOL</form:option>
		<form:option value="BLOWTIM">BLOWTIM</form:option>
		<form:option value="TOSK">TOSK</form:option>
	</form:select>
	
	</form:label>
	
	<br />
	
	
	<input type = "submit" class = "btn" name = "save"  value = "<spring:message code = "oblemic.save"/>" />
	<input type = "button" class = "btn" name = "cancel" value = "<spring:message code = "oblemic.cancel" />" 
			onclick="javascript: relativeRedir('oblemic/${role}/list.do?fixUpTaskId=${ param.fixUpTaskId }');"/>
	
	<jstl:if test="${oblemic.id!=0}">
		<input 
			type="submit"
			name="delete"
			value="<spring:message code="oblemic.delete" />"
			onclick="return confirm('<spring:message code='oblemic.confirm.delete' />') " /> 
	</jstl:if> 
	
</form:form>
	
</security:authorize>