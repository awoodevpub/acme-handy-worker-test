<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul style="list-style-type: disc">
	<li><b><spring:message code="m.priority"></spring:message>:</b> <jstl:out
			value="${m.priority}" /></li>
			
	<li><b><spring:message code="m.tags"></spring:message>:</b>
		<ul>
			<jstl:forEach items="${m.tags}" var = "tag" >
				<li><jstl:out value="${tag}" /></li>
		    </jstl:forEach>
	    </ul>
			

	<li><b><spring:message code="m.sender"></spring:message>:</b> <jstl:out
			value="${m.sender.userAccount.username}" /></li>

	<li><b><spring:message code="m.recipient"></spring:message>:</b>
		<ul>
			<jstl:forEach items="${m.recipients}" var = "recipient" >
				<li><jstl:out value="${recipient.userAccount.username}" /></li>
		    </jstl:forEach>
	    </ul>
		
	<li><b><spring:message code="m.subject"></spring:message>:</b> <jstl:out
			value="${m.subject}" /></li>

	<li><b><spring:message code="m.body"></spring:message>:</b> <jstl:out
			value="${m.body}" /></li>
</ul>



<input type="button" name="back"
	value="<spring:message code="m.back" />"
	onclick="javascript: relativeRedir('box/show.do?boxId=${box.id}');" />

<input type="button" name="delete"
	value="<spring:message code="m.delete" />"
	onclick="javascript: relativeRedir('message/delete.do?boxId=${box.id}&messageId=${m.id}');" />
