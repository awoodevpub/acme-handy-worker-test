<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<script type="text/javascript">
$(document).ready(function() {
		 $("#form").submit(function(){
			var middleName = document.getElementById("middleName");
			var make = document.getElementById("make");
			var phoneNumber = document.getElementById("phoneNumber");
			var address = document.getElementById("address");
			if(middleName.value == ""){
				middleName.remove();
			}
			if(photo.value == ""){
				photo.remove();
			}
			if(phoneNumber.value == ""){
				phoneNumber.remove();
			} else{
				var expreg = /^([+][1-9][0-9]{0,2}[ ][(][1-9][0-9]{0,2}[)][ ][0-9]{4,})|([+][1-9][0-9]{0,2}[ ][0-9]{4,})|([0-9]{4,})$/;
				if(!expreg.test(phoneNumber.value)){
					confirm("<spring:message code = "actor.confirm.phone"/>");
				}
			}
			if(address.value == ""){
				address.remove();
			}
			if(make.value == ""){
				make.remove();
			}
		});
});
</script>

<form:form id="form" action="${actionURL}" modelAttribute="actor">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="boxes" />
	<form:hidden path="userAccount" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="fixUpTasks"/>
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:hidden path="finder"/>
		<form:hidden path="applications"/>
	</security:authorize>
	
	<security:authorize access="hasRole('SPONSOR')">
		<form:hidden path="sponsorships"/>
	</security:authorize>	

	<form:label path="name">
		<spring:message code="actor.name"/>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br/>
	
	<form:label path="middleName">
		<spring:message code="actor.middleName"/>
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName"/>
	<br/>
	
	<form:label path="surname">
		<spring:message code="actor.surname"/>
	</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br/>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="make">
			<spring:message code="actor.make"/>
		</form:label>
		<form:input path="make"/>
		<form:errors cssClass="error" path="make"/>
		<br/>
	</security:authorize>
	
	<form:label path="photo">
		<spring:message code="actor.photo"/>
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo"/>
	<br/>
	
	<form:label path="email">
		<spring:message code="actor.email"/>
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br/>
	
	<form:label path="phoneNumber">
		<spring:message code="actor.phoneNumber"/>
	</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br/>
	
	<form:label path="address">
		<spring:message code="actor.address"/>
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="actor.save.edit" />" class = "btn">
	<input type="button" name="cancel"	value="<spring:message code="actor.cancel" />" class = "btn" onclick ="javascript: relativeRedir('welcome/index.do');">

</form:form>