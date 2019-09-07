<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<table>
   <tr>
      <th><spring:message code="system.title" /></th>
  
      <!-- rest of you columns -->
   </tr>

   <jstl:forEach items="${words}" var="word">
     <tr>
         <td>${word}</td>      
     </tr>
   </jstl:forEach>

 </table> 
<form action="configuration/administrator/edit.do" method="post">
 <input type = "text" name="positiveWord" />
<input type="submit" name="savePositive" value="<spring:message code="system.newPositive" />" class = "btn">
<input type="submit" name="deletePositive" value="<spring:message code="system.deletePositive" />" class = "btn">
</form>
<br>
<form action="configuration/administrator/edit.do" method="post">
 <input type = "text" name="negativeWord" />
<input type="submit" name="saveNegative" value="<spring:message code="system.newNegative" />" class = "btn">
<input type="submit" name="deleteNegative" value="<spring:message code="system.deleteNegative" />" class = "btn">
</form>



<jstl:set var="es" value="es"></jstl:set>
<jstl:set var="error" value="error"></jstl:set>
			
			<jstl:choose>
				<jstl:when test="${message eq error }">
					<a><spring:message code="system.blank.error" /></a>
				</jstl:when>
			</jstl:choose>





