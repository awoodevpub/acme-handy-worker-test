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

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set value="customer" var="role" />
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set value="handyworker" var="role" />
</security:authorize>

	<form:form action="application/${role}/edito.do" modelAttribute="application">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="status" />
	<security:authorize access="hasRole('CUSTOMER')">
	<!-- If status of the application is ACCEPTED the customer must provide a valid credit card -->
	<jstl:if test="${application.id!=0 & application.status=='ACCEPTED'}">
			<h2>
				<spring:message code="application.accept.title" />
			</h2>

			<form:label path="creditCard.holderName">
				<spring:message code="application.holderName" />
			</form:label>
			<form:input path="creditCard.holderName" class="creditCard" required="required"/>
			<form:errors cssClass="error" path="creditCard.holderName" />
			<br />

			<form:label path="creditCard.brandName">
				<spring:message code="application.brandName" />
			</form:label>
			<form:input path="creditCard.brandName" class="creditCard" required="required"/>
			<form:errors cssClass="error" path="creditCard.brandName" />
			<br />

			<form:label path="creditCard.number">
				<spring:message code="application.number" />
			</form:label>
			<form:input path="creditCard.number" placeholder="xxxxxxxxxxxxxxxx"
				class="creditCard" required="required"/>
			<form:errors cssClass="error" path="creditCard.number" />
			<br />

			<form:label path="creditCard.expirationMonth">
				<spring:message code="application.expirationMonth" />
			</form:label>
			<form:input path="creditCard.expirationMonth" class="creditCard" placeholder="mm" required="required"/>
			<form:errors cssClass="error" path="creditCard.expirationMonth" />
			<br />

			<form:label path="creditCard.expirationYear">
				<spring:message code="application.expirationYear" />
			</form:label>
			<form:input path="creditCard.expirationYear" placeholder="yy"
				class="creditCard" required="required"/>
			<form:errors cssClass="error" path="creditCard.expirationYear" />
			<br />

			<form:label path="creditCard.cvv">
				<spring:message code="application.cvv" />
			</form:label>
			<form:input path="creditCard.cvv" placeholder="xxx" class="creditCard" required="required" />
			<form:errors cssClass="error" path="creditCard.cvv" />
			<br />
		</jstl:if>
		
		<!-- In either case, the customer can set a comment (status reason) -->
		<jstl:if test="${application.id!=0}">
		<h2>
			<spring:message code="application.reject.title" />
		</h2>
		<form:label path="statusReason">
			<spring:message code="application.status.reason" />
		</form:label>
		<form:textarea path="statusReason" />
		<form:errors cssClass="error" path="statusReason" />
		<br />
		</jstl:if>
	</security:authorize>
	
	<!-- If handy worker wants to apply for fix up task, he can set an offered price and some comments -->
	<jstl:if test="${application.id==0}">
	<security:authorize access="hasRole('HANDYWORKER')">
		<h2>
			<spring:message code="application.create.title" />
		</h2>
		<form:label path="oferedPrice">
			<spring:message code="application.oferedPrice" />
			</form:label>
			<form:input path="oferedPrice" />
			<form:errors cssClass="error" path="oferedPrice" />
			<br />
		
		<form:label path="comments">
			<spring:message code="application.comments" />
			</form:label>
			<form:textarea path="comments" />
			<form:errors cssClass="error" path="comments" />
			<br />
	</security:authorize>
	</jstl:if>
	
	<input type="submit" name="save"
	class = "btn"
		value="<spring:message code="application.save" />">

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		class = "btn"
		onclick="javascript: relativeRedir('application/${role}/list.do')">
	</form:form>

