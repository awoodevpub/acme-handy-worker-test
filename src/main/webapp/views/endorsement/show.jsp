<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	
		<security:authorize access="hasRole('HANDYWORKER')">
	
	<jstl:set var = "rol" value = "handyworker"/>
	
	</security:authorize>
	
	
<security:authorize access="hasRole('CUSTOMER')">

<jstl:set var = "rol" value = "customer"/>
				
</security:authorize>
	
<jstl:if test="${endorsement.endorserCustomer != null }">
	<p><spring:message code="endorsement.endorserCustomer"/>
	<jstl:set var="endorserCustomer" value="${endorsement.endorserCustomer}"  />
 <jstl:out value="${endorserCustomer.name}" /></p>
</jstl:if>

<jstl:if test="${endorsement.endorserHandyWorker != null }">
	<p><spring:message code="endorsement.endorserHandyWorker"/>
	<jstl:set var="endorserHandyWorker" value="${endorsement.endorserHandyWorker}"  />
 <jstl:out value="${endorserHandyWorker.name}" /></p>
</jstl:if>

<jstl:if test="${endorsement.endorsedCustomer != null }">
	<p><spring:message code="endorsement.endorsedCustomer"/>
	<jstl:set var="endorsedCustomer" value="${endorsement.endorsedCustomer}"  />
 <jstl:out value="${endorsedCustomer.name}" /></p>
</jstl:if>


<jstl:if test="${endorsement.endorsedHandyWorker != null }">
	<p><spring:message code="endorsement.endorsedHandyWorker"/>
	<jstl:set var="endorsedHandyWorker" value="${endorsement.endorsedHandyWorker}"  />
 <jstl:out value="${endorsedHandyWorker.name}" /></p>
</jstl:if>

<p><spring:message code="endorsement.comments"/>
	<jstl:set var="comments" value="${endorsement.comments}"  />
	<jstl:forEach var="comment" items="${comments }">
		<jstl:out value="${comment},"/>
	</jstl:forEach>
	</p>


<jstl:if test="${logged.equals(endorsement.endorserHandyWorker) || logged.equals(endorsement.endorserCustomer) }">
	
	<input type="button" name="edit" 
	value="<spring:message code="endorsement.edit" />"
	onclick="javascript: relativeRedir('endorsement/${rol}/edit.do?endorsementId=${endorsement.id}');" />
	
	
	</jstl:if>
	
	
	<input type="button" name="back"
	value="<spring:message code="endorsement.back" />"
	onclick="javascript: relativeRedir('endorsement/${rol}/list.do');" />
    
  