<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!--  Control Check  -->

	<jstl:if test="${avgTraneutsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.avgTraneutsPerFixUpTask" /> = <fmt:formatNumber value="${avgTraneutsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${stddevTraneutsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.stddevTraneutsPerFixUpTask" /> = <fmt:formatNumber value="${stddevTraneutsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${RatioPublishedTraneuts != 'null'}">
	<spring:message code="dashboard.RatioPublishedTraneuts" /> = <fmt:formatNumber value="${RatioPublishedTraneuts}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${RatioUnpublishedTraneuts != 'null'}">
	<spring:message code="dashboard.RatioUnpublishedTraneuts" /> = <fmt:formatNumber value="${RatioUnpublishedTraneuts}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

<!-- Min, max, stdev and stdev of trips per manager. -->
	<h3>
		<spring:message code="dashboard.customerFixUpTasksStadistics" />
	</h3>

	<jstl:if test="${minNumFixUpTasks != 'null'}">
	<spring:message code="dashboard.minNumFixUpTasks" /> = <fmt:formatNumber value="${minNumFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${maxNumFixUpTasks != 'null'}">
	<spring:message code="dashboard.maxNumFixUpTasks" /> = <fmt:formatNumber value="${maxNumFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${averageFixUpTasks != 'null'}">
	<spring:message code="dashboard.averageFixUpTasks" /> = <fmt:formatNumber value="${averageFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>
	
	<jstl:if test="${standarDesviationFixUpTasks != 'null'}">
	<spring:message code="dashboard.standarDesviationFixUpTasks" /> =	<fmt:formatNumber value="${standarDesviationFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>
		<!-- Min, max, stdev and stdev of trips per manager. -->
		
		<h3>
		<spring:message code="dashboard.applicationsPerFixUpTaskStadistics" />
	</h3>
	
	<jstl:if test="${minApplicationsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.minApplicationsPerFixUpTask" /> = <fmt:formatNumber value="${minApplicationsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${maxApplicationsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.maxApplicationsPerFixUpTask" /> = <fmt:formatNumber value="${maxApplicationsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${averageApplicationsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.averageApplicationsPerFixUpTask" /> = <fmt:formatNumber value="${averageApplicationsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${standarDesviationApplicationsPerFixUpTask != 'null'}">
	<spring:message code="dashboard.standarDesviationApplicationsPerFixUpTask" /> =	<fmt:formatNumber value="${standarDesviationApplicationsPerFixUpTask}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>
			<h3>
		<spring:message code="dashboard.maximumPricesFixUpTasksStadistics" />
	</h3>
	
	<jstl:if test="${minMaximumPricesFixUpTasks != 'null'}">
	<spring:message code="dashboard.minMaximumPricesFixUpTasks" /> = <fmt:formatNumber value="${minMaximumPricesFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${maxMaximumPricesFixUpTasks != 'null'}">
	<spring:message code="dashboard.maxMaximumPricesFixUpTasks" /> = <fmt:formatNumber value="${maxMaximumPricesFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${averageMaximumPricesFixUpTasks != 'null'}">
	<spring:message code="dashboard.averageMaximumPricesFixUpTasks" /> = <fmt:formatNumber value="${averageMaximumPricesFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${standarDesviationMaximumPricesFixUpTasks != 'null'}">
	<spring:message code="dashboard.standarDesviationMaximumPricesFixUpTasks" /> =	<fmt:formatNumber value="${standarDesviationMaximumPricesFixUpTasks}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>
		
				<h3>
		<spring:message code="dashboard.priceOfferedApplicationsStadistics" />
	</h3>
	
	<jstl:if test="${minPriceOfferedApplications != 'null'}">
	<spring:message code="dashboard.minPriceOfferedApplications" /> = <fmt:formatNumber value="${minPriceOfferedApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${maxPriceOfferedApplications != 'null'}">
	<spring:message code="dashboard.maxPriceOfferedApplications" /> = <fmt:formatNumber value="${maxPriceOfferedApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${averagePriceOfferedApplications != 'null'}">
	<spring:message code="dashboard.averagePriceOfferedApplications" /> = <fmt:formatNumber value="${averagePriceOfferedApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${standarDesviationPriceOfferedApplications != 'null'}">
	<spring:message code="dashboard.standarDesviationPriceOfferedApplications" /> =	<fmt:formatNumber value="${standarDesviationPriceOfferedApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>
	
	<jstl:if test="${ratioPendingApplications != 'null'}">
		<spring:message code="dashboard.ratioPendingApplications" /> = <fmt:formatNumber value="${ratioPendingApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${rattioAcceptedAplications != 'null'}">
	<spring:message code="dashboard.rattioAcceptedAplications" /> = <fmt:formatNumber value="${rattioAcceptedAplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${ratioRejectedApplications != 'null'}">
	<spring:message code="dashboard.ratioRejectedApplications" /> = <fmt:formatNumber value="${ratioRejectedApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${ratioPendingApplicationsWithElapsedPeriod != 'null'}">
	<spring:message code="dashboard.ratioPendingApplicationsWithElapsedPeriod" /> =	<fmt:formatNumber value="${ratioPendingApplicationsWithElapsedPeriod}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>

		<h3><spring:message code="dashboard.tenPercentFixUpTasksAverage" /></h3>
		<jstl:if test="${tenPercentFixUpTasksAverage != 'null'}">
			<jstl:forEach var="x" items="${tenPercentFixUpTasksAverage}">
			<h4><spring:message code="dashboard.name" /> <jstl:out value="${x.name}"/> <jstl:out value="${x.surname}"/></h4>
			</jstl:forEach>
			<br />
		</jstl:if>
	
		<h3><spring:message code="dashboard.handyWorkersGotAcceptedMoreTenPercent" /></h3>
		<jstl:if test="${handyWorkersGotAcceptedMoreTenPercent != 'null'}">
			<jstl:forEach var="x" items="${handyWorkersGotAcceptedMoreTenPercent}">
			<h4><spring:message code="dashboard.name" /> <jstl:out value="${x.name}"/> <jstl:out value="${x.surname}"/></h4>
			</jstl:forEach>
		</jstl:if>
		<br />
		
		
					<h3>
		<spring:message code="dashboard.fixUpTasksComplaintStadistics" />
	</h3>
	
	<jstl:if test="${minFixUpTasksComplaint != 'null'}">
	<spring:message code="dashboard.minFixUpTasksComplaint" /> = <fmt:formatNumber value="${minFixUpTasksComplaint}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${maxFixUpTasksComplaint != 'null'}">
	<spring:message code="dashboard.maxFixUpTasksComplaint" /> = <fmt:formatNumber value="${maxFixUpTasksComplaint}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${averageFixUpTasksComplaint != 'null'}">
	<spring:message code="dashboard.averageFixUpTasksComplaint" /> = <fmt:formatNumber value="${averageFixUpTasksComplaint}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${standarDesviationFixUpTasksComplaint != 'null'}">
	<spring:message code="dashboard.standarDesviationFixUpTasksComplaint" /> =	<fmt:formatNumber value="${standarDesviationFixUpTasksComplaint}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>	
		
						<h3>
		<spring:message code="dashboard.notesPerRefeeeReportStadistics" />
	</h3>
	
	<jstl:if test="${minNotesPerRefeeeReport != 'null'}">
	<spring:message code="dashboard.minNotesPerRefere" /> = <fmt:formatNumber value="${minNotesPerRefeeeReport}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${maxNotesPerRefeeeReport != 'null'}">
	<spring:message code="dashboard.maxNotesPerRefere" /> = <fmt:formatNumber value="${maxNotesPerRefeeeReport}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${averageNotesPerRefeeeReport != 'null'}">
	<spring:message code="dashboard.averageNotesPerRefere" /> = <fmt:formatNumber value="${averageNotesPerRefeeeReport}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${standarDesviationNotesPerRefeeeReport != 'null'}">
	<spring:message code="dashboard.standarDesviationNotesPerRefere" /> =	<fmt:formatNumber value="${standarDesviationNotesPerRefeeeReport}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	</jstl:if>

	<jstl:if test="${ratiofixUpTasksWithComplaint != 'null'}">
		<spring:message code="dashboard.ratiofixUpTasksWithComplaint" /> = <fmt:formatNumber value="${ratiofixUpTasksWithComplaint}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		<br />
	</jstl:if>	
		
		<h3><spring:message code="dashboard.topThreeCustomers" /></h3>
		<jstl:if test="${topThreeCustomers != 'null'}">
			<jstl:forEach var="x" items="${topThreeCustomers}">
				<h4><spring:message code="dashboard.name" /> <jstl:out value="${x.name}"/> <jstl:out value="${x.surname}"/></h4>
			</jstl:forEach>
		</jstl:if>
		<br />
		
		<h3><spring:message code="dashboard.topThreeHandyWorkers" /></h3>
		<jstl:if test="${topThreeHandyWorkers != 'null'}">
			<jstl:forEach var="x" items="${topThreeHandyWorkers}">
				<h4><spring:message code="dashboard.name" /> <jstl:out value="${x.name}"/> <jstl:out value="${x.surname}"/></h4>
			</jstl:forEach>
		</jstl:if>