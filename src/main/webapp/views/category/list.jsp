<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jstl:choose>
	<jstl:when test="${empty category}">
		<spring:message code="category.empty" />
	</jstl:when>
	<jstl:otherwise>
	
		
		<display:table name="category" id="row" requestURI="category/list.do"
			class="displaytag">
		
		
			<jstl:set var="es" value="es"></jstl:set>
			<jstl:set var="c" value=""></jstl:set>
			<jstl:set var="f" value=""></jstl:set>
			<jstl:forEach items="#{row.name}" var="item1">
				<jstl:choose>
		
		
					<jstl:when test="${language eq es}">
						<display:column value="${item1.value}" titleKey="category.name" />
		
					</jstl:when>
					<jstl:otherwise>
						<display:column value="${item1.key}" titleKey="category.name" />
					</jstl:otherwise>
				</jstl:choose>
			</jstl:forEach>
		
		
			<jstl:set var="parent" value="${row.parentCategory.name}"></jstl:set>
			<jstl:set var="parentName" value="${fn:replace(parent, '{', ' ')} "></jstl:set>
			<jstl:set var="parentName2"
				value="${fn:replace(parentName, '}', ' ')} "></jstl:set>
			<jstl:set var="parentNameFinal" value="${fn:split(parentName2,'=')}"></jstl:set>
			<jstl:choose>
		
		
				<jstl:when test="${language eq es}">
		
					<display:column value="${parentNameFinal[1]}"
						titleKey="category.parentCategory" />
				</jstl:when>
				<jstl:otherwise>
					<display:column value="${parentNameFinal[0]}"
						titleKey="category.parentCategory" />
				</jstl:otherwise>
			</jstl:choose>
		
		
		
			<jstl:forEach items="${row.childsCategory}" var="child"
				varStatus="stat">
				<jstl:forEach items="#{child.name}" var="item1">´
				
				<jstl:choose>
		
		
						<jstl:when test="${language eq es}">
		
							<jstl:set var="c" value="${stat.first ? '' : c} ${item1.value}"></jstl:set>
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="c" value="${stat.first ? '' : c} ${item1.key}"></jstl:set>
		
						</jstl:otherwise>
					</jstl:choose>
				</jstl:forEach>
		
			</jstl:forEach>
		
			<display:column value="${c}" titleKey="category.childsCategory" />
		
			<jstl:forEach items="${row.fixUpTasks}" var="fix" varStatus="stat">
				<jstl:set var="f" value="${stat.first ? '' : f} ${fix.ticker}"></jstl:set>
			</jstl:forEach>
		
			<display:column value="${f}" titleKey="category.fixUpTasks" />
			<display:column>
			
			
			
			
				<input type="button" name="edit"
					value="<spring:message code="category.edit" />"
					onclick="javascript: relativeRedir('category/administrator/edit.do?categoryId=${row.id}');" />
			</display:column>
			<display:column>
				<input type="button" name="create"
					value="<spring:message code="category.create"/>" class="btn"
					onclick="javascript: relativeRedir('category/administrator/create.do?parentId=${row.id}');">
			</display:column>
			
				<display:column>
					<jstl:if test="${row.id!=0}">
						<input type="button" name="delete"	value="<spring:message code="category.delete" />" class = "btn" onclick ="javascript: relativeRedir('category/administrator/delete.do?categoryId=${row.id}');">
					</jstl:if>
					</display:column>
		</display:table>

	</jstl:otherwise>
</jstl:choose>