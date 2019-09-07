<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actors" requestURI="actor/administrator/list.do" id="row">



	<!-- Attributes -->

	<fieldset>
		<legend>
			<spring:message code="actor.personalInformation" />
			:
		</legend>

		<spring:message code="userAccount.username" var="usernameHeader" />

		<display:column property="userAccount.username"
			title="${usernameHeader}" />



		<spring:message code="actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}"/>


		<display:column property="name" title="${nameHeader}" />


		<spring:message code="actor.middleName" var="middleNameHeader" />

		<display:column property="middleName" title="${middleName}" />


		<spring:message code="actor.surname" var="surnameHeader" />

		<display:column property="surname" title="${surname}" />



		<spring:message code="actor.email" var="emailHeader" />

		<display:column property="email" title="${emailHeader}" />



		<spring:message code="actor.phoneNumber" var="phoneHeader" />

		<display:column property="phoneNumber" title="${phoneHeader}" />



		<spring:message code="actor.address" var="addressHeader" />

		<display:column property="address" title="${addressHeader}" />

		<security:authorize access="hasRole('HANDYWORKER')">
			<spring:message code="actor.make" var="makeHeader" />

			<display:column property="make" title="${makeHeader}" />
		</security:authorize>



	</fieldset>

	<input type="submit" name="edit"
		value=" https://acme-handy-worker.com/actor/edit.do" />


	<security:authorize access="hasRole('ADMINISTRATOR')">
		<div id=roleform>



			<form:form id="formID" action="actor/edit.do"
				modelAttribute="authority">

				<form:select path="phone">
					<form:option value="ADMINISTRATOR">ADMINISTRATOR</form:option>
					<form:option value="SPONSOR">SPONSOR</form:option>
					<form:option value="REFEREE">REFEREE</form:option>

				</form:select>
				<input type="submit" name="create new roles"/>
			</form:form>


			value=" https://acme-handy-worker.com/actor/edit.do" /> <input
				type="submit" name="Add social profile"
				value=" https://acme-handy-worker.com/profile/edit.do" />
	</security:authorize>
</display:table>
