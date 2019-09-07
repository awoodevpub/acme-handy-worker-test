<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<jstl:choose>
	<jstl:when test="${param.categoryId eq null}">

		<form action="category/administrator/edit.do?parentId=${param.parentId}"
			method="post">
			<spring:message code="category.languageEn" />
			<input name="nombreEn">
			<spring:message code="category.language" />
			<input name="nombreEs"> <input type="button" name="cancel"
				value="<spring:message code="actor.cancel" />"
				onclick="javascript: relativeRedir('category/administrator/list.do');" />


			<br />
		
			<br /> <input type="submit" name="save"
				value="<spring:message code="category.save" />" class="btn">
			<br />

		</form>
	</jstl:when>
	<jstl:otherwise>
		<form
			action="category/administrator/edit.do?categoryId=${param.categoryId}"
			method="post">
			<spring:message code="category.languageEn" />
			<input name="nombreEn">
			<spring:message code="category.language" />
			<input name="nombreEs"> <input type="button" name="cancel"
				value="<spring:message code="actor.cancel" />"
				onclick="javascript: relativeRedir('category/administrator/list.do');" />


			<br />

			<br /> <input type="submit" name="save"
				value="<spring:message code="category.save" />" class="btn">
			<br />

		</form>

	</jstl:otherwise>
</jstl:choose>





