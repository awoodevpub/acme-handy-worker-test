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

	<form:form action="" modelAttribute="application">
	<form:hidden path="handyWorker" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="momentOfRegistry" />

	<security:authorize access="hasRole('CUSTOMER')">
	<!-- If status of the application is ACCEPTED the customer must provide a valid credit card -->

	<form:hidden path="offeredPrice" />
	<form:hidden path="comment" />
	<input id="status" name="status" type="hidden" value="${applicationStatus}"/>

	<jstl:if test="${application.id!=0 and applicationStatus eq 'ACCEPTED'}">
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
			<form:input path="creditCard.number" placeholder="xxxxxxxxxxxxxxxx" class="creditCard" required="required"/>
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
			<form:input path="creditCard.expirationYear" placeholder="yy" class="creditCard" required="required"/>
			<form:errors cssClass="error" path="creditCard.expirationYear" />
			<br />

			<form:label path="creditCard.ccv">
				<spring:message code="application.ccv" />
			</form:label>
			<form:input path="creditCard.ccv" placeholder="xxx" class="creditCard" required="required" />
			<form:errors cssClass="error" path="creditCard.ccv" />
			<br />
			<form:label path="stateReason">
				<spring:message code="application.status.reason" />
			</form:label>
			<form:input path="stateReason" required="required" />
			<form:errors cssClass="error" path="stateReason" />
			<br />
		</jstl:if>
		
		<!-- In either case, the customer can set a comment (status reason) -->
		<jstl:if test="${application.id!=0 and applicationStatus eq 'REJECTED'}">
		<h2>
			<spring:message code="application.reject.title" />
		</h2>
		<form:label path="stateReason">
			<spring:message code="application.status.reason" />
		</form:label>
		<textarea id="stateReason" name="stateReason"></textarea>
		<form:errors cssClass="error" path="stateReason" />
		<br />
		</jstl:if>
	</security:authorize>
	
	<!-- If handy worker wants to apply for fix up task, he can set an offered price and some comments -->
	<jstl:if test="${application.id eq 0}">
	<security:authorize access="hasRole('HANDYWORKER')">
	<input id="status" name="status" type="hidden" value="PENDING"/>
	
	<form:hidden path="creditCard" />
		<h2>
			<spring:message code="application.create.title" />
		</h2>
		<form:label path="offeredPrice">
			<spring:message code="application.offeredPrice" />
			</form:label>
			<form:input path="offeredPrice" />
			<form:errors cssClass="error" path="offeredPrice" />
			<br />
		
		<form:label path="comment">
			<spring:message code="application.comment" />
			</form:label>
			<form:textarea path="comment" />
			<form:errors cssClass="error" path="comment" />
			<br />
			
		<form:label path="stateReason">
			<spring:message code="application.status.reason" />
			</form:label>
			<form:textarea path="stateReason" />
			<form:errors cssClass="error" path="stateReason" />
			<br />
	</security:authorize>
	</jstl:if>
	
	<!-- <params = "save"  -->
	<input type="submit" name="save" class="btn" value="<spring:message code="application.save" />">

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		class = "btn"
		onclick="javascript: relativeRedir('application/${role}/list.do?fixUpTaskId=${application.fixUpTask.id}');">
	</form:form>

