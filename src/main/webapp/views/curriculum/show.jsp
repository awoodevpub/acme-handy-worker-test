<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${curriculum == null}">
<h1><spring:message code="curriculum.register" /></h1>
<input type="button" name="create"	value="<spring:message code="curriculum.create" />"	onclick="javascript: relativeRedir('curriculum/handyworker/create.do');" />
</jstl:if>


<jstl:if test="${curriculum != null}">
<h1><spring:message code="curriculum.ticker" />: <jstl:out value="${curriculum.ticker}"/></h1>


	<h2><u><spring:message code = "curriculum.personalRecord"/>:</u></h2>
		<jstl:set var="personalRecord" value="${curriculum.personalRecord}" />
			<h3><jstl:out value="${personalRecord.fullName}"/></h3>
			<ul>
				<li><spring:message code="curriculum.personalRecord.photo" />: <jstl:out value="${personalRecord.photo}"/></li>
				<li><spring:message code="curriculum.personalRecord.email" />: <jstl:out value="${personalRecord.email}"/></li>
				<li><spring:message code="curriculum.personalRecord.phoneNumber" />: <jstl:out value="${personalRecord.phoneNumber}"/></li>
				<li><spring:message code="curriculum.personalRecord.urlLinkedIn" />: <jstl:out value="${personalRecord.urlLinkedIn}"/></li>
			</ul>

			<input type="button" name="edit"
			value="<spring:message code="curriculum.edit.personalRecord" />"
			onclick="javascript: relativeRedir('personalRecord/handyworker/edit.do?personalRecordId=${personalRecord.id}&curriculumId=${curriculum.id}');" />
			<br>

	<jstl:if test="${not empty curriculum.educationRecords}">
		<h2><u><spring:message code = "curriculum.educationRecords"/>:</u></h2>
		<jstl:forEach var="educationRecord" items="${curriculum.educationRecords}">
			<h3><jstl:out value="${educationRecord.diplomaTitle}"/></h3>
			<ul>
				<li><spring:message code="curriculum.educationRecord.startDateStudy" />: <jstl:out value="${educationRecord.startDateStudy}"/></li>
				<li><spring:message code="curriculum.educationRecord.endDateStudy" />: <jstl:out value="${educationRecord.endDateStudy}"/></li>
				<li><spring:message code="curriculum.educationRecord.institution" />: <jstl:out value="${educationRecord.institution}"/></li>
				<li><spring:message code="curriculum.educationRecord.linkAttachment" />: <jstl:out value="${educationRecord.linkAttachment}"/></li>
				<li>
					<spring:message code="curriculum.educationRecord.comments" />:
					<ul>
					<jstl:forEach var="comment" items="${educationRecord.comments}">
						<li>${comment}</li>
					</jstl:forEach>
					</ul>
				</li>
			</ul>

			<input type="button" name="edit"
			value="<spring:message code="curriculum.edit.educationRecords" />"
			onclick="javascript: relativeRedir('educationRecord/handyworker/edit.do?educationRecordId=${educationRecord.id}&curriculumId=${curriculum.id}');" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<jstl:if test="${empty curriculum.educationRecords}">
		<h3><spring:message code="curriculum.notice.educationRecords"/></h3>
	</jstl:if>

		<input type="button" name="add"
		value="<spring:message code="curriculum.educationRecord.add" />"
		onclick="javascript: relativeRedir('educationRecord/handyworker/create.do?curriculumId=${curriculum.id}');" />
		<br>

	<jstl:if test="${not empty curriculum.professionalRecords}">
		<h2><u><spring:message code = "curriculum.professionalRecords"/>:</u></h2>
		<jstl:forEach var="professionalRecord" items="${curriculum.professionalRecords}">
			<h3><jstl:out value="${professionalRecord.companyName}"/></h3>
			<ul>
				<li><spring:message code="curriculum.professionalRecord.startDateWork" />: <jstl:out value="${professionalRecord.startDateWork}"/></li>
				<li><spring:message code="curriculum.professionalRecord.endDateWork" />: <jstl:out value="${professionalRecord.endDateWork}"/></li>
				<li><spring:message code="curriculum.professionalRecord.rolePlayed" />: <jstl:out value="${professionalRecord.rolePlayed}"/></li>
				<li><spring:message code="curriculum.professionalRecord.linkAttachment" />: <jstl:out value="${professionalRecord.linkAttachment}"/></li>
				<li>
					<spring:message code="curriculum.professionalRecord.comments" />:
					<ul>
					<jstl:forEach var="comment" items="${professionalRecord.comments}">
						<li>${comment}</li>
					</jstl:forEach>
					</ul>
				</li>
			</ul>

			<input type="button" name="edit"
			value="<spring:message code="curriculum.edit.professionalRecords" />"
			onclick="javascript: relativeRedir('professionalRecord/handyworker/edit.do?professionalRecordId=${professionalRecord.id}&curriculumId=${curriculum.id}');" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<jstl:if test="${empty curriculum.professionalRecords}">
		<h3><spring:message code="curriculum.notice.professionalRecords"/></h3>
	</jstl:if>

		<input type="button" name="add"
		value="<spring:message code="curriculum.professionalRecord.add" />"
		onclick="javascript: relativeRedir('professionalRecord/handyworker/create.do?curriculumId=${curriculum.id}');" />
		<br>

	<jstl:if test="${not empty curriculum.miscellaneousRecords}">
		<h2><u><spring:message code = "curriculum.miscellaneousRecords"/>:</u></h2>
		<jstl:forEach var="miscellaneousRecord" items="${curriculum.miscellaneousRecords}">
			<h3><jstl:out value="${miscellaneousRecord.title}"/></h3>
			<ul>
				<li><spring:message code="curriculum.miscellaneousRecord.linkAttachment" />: <jstl:out value="${miscellaneousRecord.linkAttachment}"/></li>
				<li>
					<spring:message code="curriculum.miscellaneousRecord.comments" />:
					<ul>
					<jstl:forEach var="comment" items="${miscellaneousRecord.comments}">
						<li>${comment}</li>
					</jstl:forEach>
					</ul>
				</li>
			</ul>

			<input type="button" name="edit"
			value="<spring:message code="curriculum.edit.miscellaneousRecords" />"
			onclick="javascript: relativeRedir('miscellaneousRecord/handyworker/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}&curriculumId=${curriculum.id}');" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<jstl:if test="${empty curriculum.miscellaneousRecords}">
		<h3><spring:message code="curriculum.notice.miscellaneousRecords"/></h3>
	</jstl:if>

		<input type="button" name="add"
		value="<spring:message code="curriculum.miscellaneousRecord.add" />"
		onclick="javascript: relativeRedir('miscellaneousRecord/handyworker/create.do?curriculumId=${curriculum.id}');" />
		<br>

	<jstl:if test="${not empty curriculum.endorserRecords}">
		<h2><u><spring:message code = "curriculum.endorserRecords"/>:</u></h2>
		<jstl:forEach var="endorserRecord" items="${curriculum.endorserRecords}">
			<h3><jstl:out value="${endorserRecord.fullName}"/></h3>
			<ul>
				<li><spring:message code="curriculum.endorserRecord.email" />: <jstl:out value="${endorserRecord.email}"/></li>
				<li><spring:message code="curriculum.endorserRecord.phoneNumber" />: <jstl:out value="${endorserRecord.phoneNumber}"/></li>
				<li><spring:message code="curriculum.endorserRecord.linkLinkedIn" />: <jstl:out value="${endorserRecord.linkLinkedIn}"/></li>
				<li>
					<spring:message code="curriculum.endorserRecord.comments" />:
					<ul>
					<jstl:forEach var="comment" items="${endorserRecord.comments}">
						<li>${comment}</li>
					</jstl:forEach>
					</ul>
				</li>
			</ul>

			<input type="button" name="edit"
			value="<spring:message code="curriculum.edit.endorserRecords" />"
			onclick="javascript: relativeRedir('miscellaneousRecord/handyworker/edit.do?endorserRecordId=${endorserRecord.id}&curriculumId=${curriculum.id}');" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<jstl:if test="${empty curriculum.endorserRecords}">
		<h3><spring:message code="curriculum.notice.endorserRecords"/></h3>
	</jstl:if>

		<input type="button" name="add"
		value="<spring:message code="curriculum.endorserRecord.add" />"
		onclick="javascript: relativeRedir('endorserRecord/handyworker/create.do?curriculumId=${curriculum.id}');" />
		<br>


</jstl:if>
