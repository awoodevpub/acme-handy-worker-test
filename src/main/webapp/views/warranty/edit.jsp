<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="warranty/administrator/edit.do" modelAttribute="warranty">

<form:hidden path="id"/>
<form:hidden path="version" />


<form:label path="title">
	<spring:message code="warranty.title" />
</form:label>
<form:input path="title" />
<form:errors cssClass="error" path="title" />
<br />

<form:label path="isFinalMode">
	<spring:message code="warranty.finalMode" />
</form:label>
<form:input path="isFinalMode" />
<form:errors cssClass="error" path="isFinalMode" />
<br />

<form:label path="terms">
	<spring:message code="fixuptask.terms" />
</form:label>
<form:input path="terms" />
<form:errors cssClass="error" path="terms" />
<br />
<spring:message code="fixuptask.laws" />
<form:textarea name="laws" id="row" path="laws" rows="20" cols="50"/>



<form:errors cssClass="error" path="laws" />

<br />


<br />




<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('warranty/administrator/list.do');" />
		
<br />

<jstl:if test="${warranty.id!=0}">
<input type="submit" name="delete" value="<spring:message code="warranty.delete" />" class = "btn">
<input type="submit" name="change" value="<spring:message code="warranty.changeMode" />" class = "btn">
</jstl:if>
<br />

<input type="submit" name="save" value="<spring:message code="warranty.save" />" class = "btn">
<br />

</form:form>
