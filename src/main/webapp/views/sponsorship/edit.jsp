<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('SPONSOR')">


<form:form id="form" action = "sponsorship/sponsor/edit.do" modelAttribute = "sponsorship">
	
	<form:hidden path = "id" />
	<form:hidden path = "version" />
	<form:hidden path = "sponsor" />
	<form:hidden path = "creditCard" />
	<form:hidden path = "tutorial" />
	
	<form:label path = "bannerUrl">
		<spring:message code = "sponsorship.bannerUrl" />
	</form:label>
	<form:input path = "bannerUrl" placeholder = "https://www.example.com" required="required"/>
	<form:errors cssClass = "error" path = "bannerUrl" />
	<br />
	
	<form:label path = "targetPageLink">
		<spring:message code = "sponsorship.targetPageLink" />
	</form:label>
	<form:input path = "targetPageLink" placeholder = "https://www.example.com" required="required"/>
	<form:errors cssClass = "error" path = "targetPageLink" />
	<br />
	
	<jstl:if test="${sponsorship.id == 0}">
	<form:label path = "creditCard.holderName">
			<spring:message code = "sponsorship.creditcard.holderName" />
		</form:label>
			<form:input path = "creditCard.holderName" required="required"/>
		<form:errors cssClass = "error" path = "creditCard.holderName" />
		<br />
		
		<form:label path = "creditCard.brandName">
			<spring:message code = "sponsorship.creditcard.brandName" />
		</form:label>
			<form:input path = "creditCard.brandName" required="required"/>
        <form:errors cssClass = "error" path = "creditCard.brandName" />
		<br />
		
		<form:label path = "creditCard.number">
			<spring:message code = "sponsorship.creditcard.number" />
		</form:label>
			<form:input path = "creditCard.number" required="required" placeholder = "XXXXXXXXXXXXXXXXX"/>
		<form:errors cssClass = "error" path = "creditCard.number"/>
		<br />
		
		<form:label path = "creditCard.expirationMonth">
			<spring:message code = "sponsorship.creditcard.expirationMonth" />
		</form:label>
			<form:input path = "creditCard.expirationMonth" required="required" placeholder = "XX"/>
        <form:errors cssClass = "error" path = "creditCard.expirationMonth"/>
		<br />
		
		<form:label path = "creditCard.expirationYear">
			<spring:message code = "sponsorship.creditcard.expirationYear" />
		</form:label>
			<form:input path = "creditCard.expirationYear" required="required" placeholder = "XX"/>
        <form:errors cssClass = "error" path = "creditCard.expirationYear"/>
		<br />
		
		<form:label path = "creditCard.ccv">
			<spring:message code = "sponsorship.creditcard.CVV" />
		</form:label>
			<form:input path = "creditCard.ccv" required="required" placeholder = "XXX"/>
		<form:errors cssClass = "error" path = "creditCard.ccv"/>
		<br />
	</jstl:if>
	
	
	<input type = "submit" name="save"  value = "<spring:message code = "tutorial.save"/>" />
	<jstl:if test="${sponsorship.id!=0}">
		<input 
			type="submit"
			name="delete"
			class = "btn"
			value="<spring:message code="sponsorship.delete" />"
			onclick="return confirm('<spring:message code='sponsorship.confirm.delete' />') " />
	</jstl:if>

	<a href = "sponsorship/sponsor/list.do">
	<input type = "button" name = "cancel" class = "btn" value = "<spring:message code = "sponsorship.cancel" />" >
	</a>

</form:form>
</security:authorize>