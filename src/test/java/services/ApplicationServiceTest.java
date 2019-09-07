
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Box;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		final Application application = this.applicationService.create();
		Assert.notNull(application);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Application> applications = this.applicationService.findAll();
		Assert.notNull(applications);
	}

	@Test
	public void testFindOne() {
		Application application;
		application = this.applicationService.findOne(super.getEntityId("application1"));
		Assert.notNull(application);
	}

	@Test
	public void testFindApplicationsByFixUpTaskId() {
		super.authenticate("customer1");
		Collection<Application> applications;
		applications = this.applicationService.findApplicationsByFixUpTaskId(super.getEntityId("fixUpTask1"));
		Assert.notNull(applications);
		super.unauthenticate();
	}

	@Test
	public void testCreateSave() {
		super.authenticate("handyWorker1");
		Application application;
		Collection<Application> applications;
		application = this.applicationService.create();
		application.setComment("I want work with you");
		application.setStateReason("Waiting answer");
		application.setOfferedPrice(50.0);
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask13"));
		application.setFixUpTask(fixUpTask);
		application = this.applicationService.save(application);
		applications = this.applicationService.findAll();
		Assert.isTrue(applications.contains(application));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("customer2");
		Application application;
		application = this.applicationService.findOne(super.getEntityId("application3"));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		application.setStatus("ACCEPTED");
		application.setStateReason("I accept your application because it has a good price");
		application.setComment("I would like to give some details");
		final CreditCard creditCard = this.creditCardService.findOne(super.getEntityId("creditCard1"));
		application.setCreditCard(creditCard);
		application = this.applicationService.save(application);
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask3"));
		final Collection<Application> applicationsFixUpTask = this.applicationService.findApplicationsByFixUpTaskId(fixUpTask.getId());
		for (final Application a : applicationsFixUpTask)
			if (a.equals(application))
				Assert.isTrue(a.getStatus().equals("ACCEPTED"));
			else
				Assert.isTrue(a.getStatus().equals("REJECTED"));
		final Customer customer2 = this.customerService.findOne(super.getEntityId("customer2"));
		final HandyWorker handyWorker2 = this.handyWorkerService.findOne(super.getEntityId("handyWorker2"));
		Boolean messageSystemSent = false;
		for (final Box b : customer2.getBoxes())
			for (final Message m : b.getMessages())
				if (m.getSubject().equals("Applicación cambiada de estado") || m.getSubject().equals("Application changed of status")) {
					messageSystemSent = true;
					break;
				}
		Assert.isTrue(messageSystemSent);
		messageSystemSent = false;
		for (final Box b : handyWorker2.getBoxes())
			for (final Message m : b.getMessages())
				if (m.getSubject().equals("Applicación cambiada de estado") || m.getSubject().equals("Application changed of status")) {
					messageSystemSent = true;
					break;
				}
		Assert.isTrue(messageSystemSent);
		super.unauthenticate();
	}

	@Test
	public void testFindApplicationsByHandyWorkerLoggedId() {
		super.authenticate("handyWorker1");
		Collection<Application> applications;
		applications = this.applicationService.findApplicationsByHandyWorkerLoggedId();
		Assert.notNull(applications);
		super.unauthenticate();
	}

	@Test
	public void testFindApplicationByHandyWorkerLoggedId() {
		super.authenticate("handyWorker1");
		Application application;
		application = this.applicationService.findApplicationByHandyWorkerLoggedId(super.getEntityId("application1"));
		Assert.notNull(application);
		super.unauthenticate();
	}
}
