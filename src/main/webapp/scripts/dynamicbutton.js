$(document)
		.ready(
				function() {

					$("#add")
							.click(
									function(e) {
										event.preventDefault()
										$('#items')
												.append(
														'<div><form:label path="${section.title}"><spring:message code="tutorial.section.title" /></form:label><form:input path="${section.title}" /><form:errors cssClass="error" path="${section.title}" />'
																+ '<input type="button" value="delete" id = "delete"/></div>');
									});

					$('body').on('click', '#delete', function(e) {
						$(this).parent('div').remove();

					});
				});
