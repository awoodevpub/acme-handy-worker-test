<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">


<form:form id="form" action="section/handyworker/edit.do?tutorialId=${tutorialId}"
	modelAttribute="section">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:if test = "${section.id==0}">
		<form:hidden path="number" value = "${number}"/>
	</jstl:if>

	<form:label path="title">
		<spring:message code="section.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
		
	<jstl:if test="${section.id!=0}">
		<form:label path="number">
			<spring:message code="section.number" />
		</form:label>
		<form:select path="number" >
			<form:option label="-----" value="0"/>
			<jstl:forEach var="x" begin="1" end="${maxNumber}">
				<form:option label="${x}" value="${x}"/>
			</jstl:forEach>
		</form:select>
		<form:errors cssClass="error" path="number" />
		<br />
	</jstl:if>	

	<form:label path="text">
		<spring:message code="section.text" />
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br />

	<form:label path="pictures">
		<spring:message code="section.pictures" />
	</form:label>
	<form:textarea path="pictures" placeholder="http://www.ejemplo.com"/>
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />
	 
	

	<input type = "submit" name="save"  value = "<spring:message code = "section.save"/>" />
	
	<input type="button" name="back"
		value="<spring:message code="section.cancel" />"
		onclick="javascript: relativeRedir('mytutorial/handyworker/show.do?tutorialId=' + ${tutorialId});" />
		
	<jstl:if test="${section.id!=0}">
		<input 
			type="submit"
			name="delete"
			value="<spring:message code="section.delete" />"
			onclick="return confirm('<spring:message code='section.confirm.delete' />') " /> 
	</jstl:if> 
	

</form:form>
</security:authorize>