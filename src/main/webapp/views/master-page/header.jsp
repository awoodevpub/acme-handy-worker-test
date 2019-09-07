<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png"
		alt="Acme Handy Worker Co., Inc." width="500" height="200"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Additional links for administrators -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/register-administrator.do"><spring:message
								code="master.page.administrator.action.1" /></a></li>
					<li><a href="actor/administrator/register-referee.do"><spring:message
								code="master.page.administrator.action.3" /></a></li>
					<li><a href="warranty/administrator/list.do"><spring:message
								code="master.page.administrator.action.4" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.administrator.action.5" /></a></li>
					<li><a href="actor/administrator/listSuspicious.do"><spring:message
								code="master.page.administrator.action.6" /></a></li>
					<li><a href="configuration/administrator/listPositive.do"><spring:message
								code="master.page.administrator.action.7" /></a></li>
					<li><a href="configuration/administrator/listNegative.do"><spring:message
								code="master.page.administrator.action.8" /></a></li>
					<li><a href="configuration/administrator/score.do"><spring:message
								code="master.page.administrator.action.9" /></a></li>
<li><a href="dashboard/administrator/show.do"><spring:message
								code="master.page.administrator.action.10" /></a></li>

				</ul></li>
		</security:authorize>


		<!-- Additional links for customers -->
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixuptask/customer/list.do"><spring:message
								code="master.page.customer.action.1" /></a></li>
					<li><a href="complaint/customer/list-all.do"><spring:message
								code="master.page.customer.action.3" /></a></li>
								<li><a href="endorsement/customer/list.do"><spring:message
								code="master.page.customer.action.4" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- Additional links for handy workers -->
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message
						code="master.page.handy.worker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixuptask/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.1" /></a></li>
					<li><a href="oblemic/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.oblemics" /></a></li>
					<li><a href="application/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.2" /></a></li>
					<li><a href="finder/handyworker/show.do"><spring:message
								code="master.page.handy.worker.action.3" /></a></li>
					<li><a href="complaint/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.4" /></a></li>
					<li><a href="mytutorial/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.5" /></a></li>
					<li><a href="endorsement/handyworker/list.do"><spring:message
								code="master.page.handy.worker.action.6" /></a></li>
					<li><a href="curriculum/handyworker/show.do"><spring:message
								code="master.page.handy.worker.action.7" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- Additional links for referees -->
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message
						code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list-assigned.do"><spring:message
								code="master.page.referee.action.1" /></a></li>

					<li><a href="complaint/referee/list-not-assigned.do"><spring:message
								code="master.page.referee.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- Additional links for referees -->
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message
								code="master.page.sponsor.action.1" /></a></li>
				</ul></li>
		</security:authorize>

		<!-- If not authenticated -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register-customer.do"><spring:message code="master.page.register.customer" /></a></li>
					<li><a href="actor/register-handy-worker.do"><spring:message code="master.page.register.handy.worker" /></a></li>
					<li><a href="actor/register-sponsor.do"><spring:message code="master.page.register.sponsor" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="tutorial/list.do"><spring:message
						code="master.page.tutorial.list" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<!-- All actors -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="tutorial/list.do"><spring:message
						code="master.page.tutorial.list" /></a></li>
			<li><a href="box/list.do"><spring:message code="master.page.message.box" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="actor/administrator/edit.do"><spring:message
									code="master.page.profile.action.1" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('CUSTOMER')">
						<li><a href="actor/customer/edit.do"><spring:message
									code="master.page.profile.action.1" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('HANDYWORKER')">
						<li><a href="actor/handyworker/edit.do"><spring:message
									code="master.page.profile.action.1" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('REFEREE')">
						<li><a href="actor/referee/edit.do"><spring:message
									code="master.page.profile.action.1" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="actor/sponsor/edit.do"><spring:message
									code="master.page.profile.action.1" /></a></li>
					</security:authorize>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
