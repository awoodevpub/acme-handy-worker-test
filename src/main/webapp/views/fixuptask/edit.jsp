<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="date" class="java.util.Date" />

<form:form action="fixuptask/customer/edit.do" modelAttribute="fixUpTask">

<form:hidden path="ticker" />
<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="momentPublished" />
<form:hidden path="phases" />
<form:hidden path="applications" />
<form:hidden path="complaints" />

<form:label path="address">
  <spring:message code="fixuptask.address" />
</form:label>
<form:input path="address" />
<form:errors cssClass="error" path="address" />
<br />

<form:label path="maximumPrice">
  <spring:message code="fixuptask.maximumPrice" />
</form:label>
<form:input path="maximumPrice" />
<form:errors cssClass="error" path="maximumPrice" />
<br />

<form:label path="startDate">
  <spring:message code="fixuptask.startDate" />
</form:label>
<form:input path="startDate" placeholder="dd/MM/yyyy" />
<form:errors cssClass="error" path="startDate" />
<br />

<form:label path="endDate">
  <spring:message code="fixuptask.endDate" />
</form:label>
<form:input path="endDate" placeholder="dd/MM/yyyy" />
<form:errors cssClass="error" path="endDate" />
<br />

<form:label path="description">
  <spring:message code="fixuptask.description" />
</form:label>
<form:textarea path="description" rows="20" cols="100"/>
<form:errors cssClass="error" path="description" />
<br />

	<form:label path="warranty">
		<spring:message code="fixuptask.warranty" />:
	</form:label>
		<form:select id="warrantys" path="warranty">
			<jstl:forEach items="${warrantys}" var="warranty" >
				<jstl:if test="${warranty.isFinalMode eq true}">
					<form:option value="${warranty.id}" label="${warranty.title}" />
				</jstl:if>
			</jstl:forEach>
		</form:select>
	<form:errors cssClass="error" path="warranty" />
<br/><br/>

	<label for="category">
		<spring:message code="fixuptask.categoryName" />:
	</label>
  <select name="categoryID" multiple>
      <jstl:forEach items="${categories}" var="category" varStatus="stat">
     	 <jstl:choose>
			  <jstl:when test="${stat.first}">
				<option selected value="${category.id}">
		      		<jstl:forEach items="${category.name}" var="map" >
						<jstl:if test="${language eq 'es'}">
					    ${map.value}
					    </jstl:if>
					    <jstl:if test="${language eq 'en'}">
					    ${map.key}
					    </jstl:if>
					</jstl:forEach>
	      		</option>
			  </jstl:when>
			  <jstl:otherwise>
				<option value="${category.id}">
		      		<jstl:forEach items="${category.name}" var="map" >
						<jstl:if test="${language eq 'es'}">
					    ${map.value}
					    </jstl:if>
					    <jstl:if test="${language eq 'en'}">
					    ${map.key}
					    </jstl:if>
					</jstl:forEach>
	      		</option>
			  </jstl:otherwise>
		</jstl:choose>
  	  </jstl:forEach>
  </select>
<br/><br/>

<a href="/Acme-Handy-Worker/fixuptask/customer/list.do">Cancel</a>

<jstl:if test="${fixUpTask.id!=0}">
<input type="submit" name="delete"
value="<spring:message code="fixuptask.delete" /> " />
</jstl:if>

<input type="submit" name="save"
value="<spring:message code="fixuptask.save" /> " />
<br />

</form:form>
