<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
     
    
     <p><spring:message code="complaint.list"/></p>
    
    <security:authorize access="hasRole('REFEREE')">
    <jstl:set value="referee" var ="role" />
    
	    <display:table name = "complaints" id = "row" 
		requestURI = "complaint/list.do" pagesize = "${pagesize}" class = "displaytag">
	    
		    <spring:message code = "complaint.ticker" var = "tickerHeader"/>
			<display:column property = "ticker" titleKey = "complaint.ticker" />
		    
		    <spring:message code ="complaint.moment" var="momentHeader" />
		    <display:column property="moment" titleKey ="complaint.moment" />
	    
		    <display:column>
			    <input type="button" name="show"
					value="<spring:message code="complaint.show" />"
					onclick="javascript: relativeRedir('complaint/referee/show.do?complaintId=${row.id}');" />
			</display:column>
			
			<jstl:set var="uri" value="/Acme-Handy-Worker/complaint/referee/list-not-assigned.do" />
			<jstl:set var="myuri" value = "${requestScope['javax.servlet.forward.request_uri']}" />
			<jstl:if test="${myuri  == uri}" >
			<display:column>
			
				<input type="button" name="assign"
					value="<spring:message code="complaint.assign" />"
					onclick="javascript: relativeRedir('report/referee/create.do?complaintId=${row.id}');" />
			</display:column>
			</jstl:if>
		</display:table>
	</security:authorize>
	
	
	<security:authorize access="hasRole('CUSTOMER')">
    <jstl:set value="customer" var ="role" />
	    <jstl:choose>
	    	<jstl:when test="${param.fixUpTaskId != null}" >
		    <display:table name = "complaints" id = "row" 
			requestURI = "complaint/list.do" pagesize = "${pagesize}" class = "displaytag">

			    <spring:message code = "complaint.ticker" var = "tickerHeader"/>
				<display:column property = "ticker" titleKey = "complaint.ticker" />
			    
			    <spring:message code ="complaint.moment" var="momentHeader" />
			    <display:column property="moment" titleKey ="complaint.momen" />
		    
			    <display:column>
				    <input type="button" name="show"
						value="<spring:message code="complaint.show" />"
						onclick="javascript: relativeRedir('complaint/customer/show.do?complaintId=${row.id}');" />
				</display:column>
				
			</display:table>
					<input type="button" name="create"
						value="<spring:message code="complaint.create" />"
						onclick="javascript: relativeRedir
						('complaint/${role}/create.do?fixUpTaskId=${param.fixUpTaskId}')" />
				</jstl:when>
				<jstl:otherwise>
			 <display:table name = "complaints" id = "row" 
			requestURI = "complaint/list-all.do" pagesize = "${pagesize}" class = "displaytag">
			
			
		    
			    <spring:message code = "complaint.ticker" var = "tickerHeader"/>
				<display:column property = "ticker" titleKey = "complaint.ticker" />
			    
			    <spring:message code ="complaint.moment" var="momentHeader" />
			    <display:column property="moment" titleKey ="complaint.moment" />
		    
			    <display:column>
				    <input type="button" name="show"
						value="<spring:message code="complaint.show" />"
						onclick="javascript: relativeRedir('complaint/customer/show.do?complaintId=${row.id}');" />
				</display:column>
				
			</display:table>	
				</jstl:otherwise>
		</jstl:choose>
		
		</security:authorize>
	
	
	<security:authorize access="hasRole('HANDYWORKER')">
    <jstl:set value="handyWorker" var ="role" />
    
	    <display:table name = "complaints" id = "row" 
		requestURI = "complaint/list.do" pagesize = "${pagesize}" class = "displaytag">
	    
		    <spring:message code = "complaint.ticker" var = "tickerHeader"/>
			<display:column property = "ticker" titleKey = "complaint.ticker" />
		    
		    <spring:message code ="complaint.moment" var="momentHeader" />
		    <display:column property="moment" titleKey ="complaint.moment" />
	    
		    <display:column>
			    <input type="button" name="show"
					value="<spring:message code="complaint.show" />"
					onclick="javascript: relativeRedir('complaint/handyworker/show.do?complaintId=${row.id}');" />
			</display:column>
			
		</display:table>
	</security:authorize>

	
    
    

    