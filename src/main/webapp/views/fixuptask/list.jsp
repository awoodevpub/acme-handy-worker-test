<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<display:table name="fixUpTasks" id="row" requestURI="fixuptask/customer/list.do" class="displaytag">

	<display:column titleKey = "fixuptask.categoryName">

		<jstl:set var="a" value="0"/>

		<jstl:forEach items="${allcategorys}" var="category" >
			<jstl:if test="${fn:contains(category.fixUpTasks, row)}">

				<jstl:forEach items="${category.name}" var="map" >
					<jstl:if test="${language eq 'es'}">
				    ${map.value},
				    </jstl:if>
				    <jstl:if test="${language eq 'en'}">
				    ${map.key},
				    </jstl:if>
				</jstl:forEach>
				<jstl:set var="a" value="1"/>
			</jstl:if>
		</jstl:forEach>

		<jstl:if test="${a eq 0}">
			 <spring:message code="fixuptask.category" />
		</jstl:if>

	</display:column>
  <display:column property = "ticker" titleKey = "fixuptask.ticker" />
  <display:column titleKey="fixuptask.maximumPrice">
  ${row.maximumPrice}$ (${row.maximumPrice + (row.maximumPrice * 0.21)}$ IVA)
  </display:column>
  <display:column property="startDate" titleKey="fixuptask.startDate" format="{0,date,dd/MM/yyyy}"/>



  <security:authorize access="hasRole('CUSTOMER')">
  
    <display:column titleKey="fixuptask.edit" >
    	<jstl:if test="${empty row.applications}">
    		<a href="fixuptask/customer/edit.do?fixuptaskId=${row.id}"><spring:message code="fixuptask.edit"/></a>
    	</jstl:if>
    </display:column>
  

    <display:column titleKey="fixuptask.applications" >
    <a href="application/customer/list.do?fixUpTaskId=${row.id}"><spring:message code="fixuptask.list"/></a>
    </display:column>

    <display:column titleKey="fixuptask.complaints" >
    <a href="complaint/customer/list.do?fixUpTaskId=${row.id}"><spring:message code="fixuptask.list"/></a>
    </display:column>
    
    <display:column titleKey="fixuptask.oblemics">
    <a href="oblemic/customer/list.do?fixUpTaskId=${row.id}"><spring:message code="fixuptask.list" /></a>
    </display:column>
  </security:authorize>


  <security:authorize access="hasRole('HANDYWORKER')">
    <display:column titleKey="fixuptask.customer" >
    <a href="profile/customer.do?fixuptaskId=${row.id}"><spring:message code="profile.show"/></a>
    </display:column>
    <display:column titleKey="fixuptask.applications" >
    <a href="application/handyworker/edit.do?fixUpTaskId=${row.id}"><spring:message code="application.create"/></a>
    </display:column>
  </security:authorize>



</display:table>

<%-- filter TODO --%>


<security:authorize access="hasRole('CUSTOMER')">


  <a href="fixuptask/customer/create.do"><spring:message code="fixuptask.create"/></a>

</security:authorize>
