
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CustomerService		customerService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		final FixUpTask fixUpTask = this.fixUpTaskService.create();
		Assert.notNull(fixUpTask);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();
		Assert.notNull(fixUpTasks);
	}

	@Test
	public void testFindOne() {
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Assert.notNull(fixUpTask);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");
		FixUpTask fixUpTask;
		Collection<FixUpTask> fixUpTasks;
		fixUpTask = this.fixUpTaskService.create();
		fixUpTask.setDescription("I need to fix a leg of a table");
		fixUpTask.setAddress("41032 C/Falsa 252");
		fixUpTask.setMaximumPrice(50.0);
		final Calendar cal = Calendar.getInstance();
		cal.set(2018, 11, 5);
		final Date startDate = cal.getTime();
		fixUpTask.setStartDate(startDate);
		cal.set(2018, 12, 5);
		final Date endDate = cal.getTime();
		fixUpTask.setEndDate(endDate);
		final Warranty warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		fixUpTask.setWarranty(warranty);
		fixUpTask = this.fixUpTaskService.save(fixUpTask);
		fixUpTasks = this.fixUpTaskService.findAll();
		Assert.isTrue(fixUpTasks.contains(fixUpTask));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("customer3");
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask13"));
		fixUpTask.setMaximumPrice(80.0);
		fixUpTask = this.fixUpTaskService.save(fixUpTask);
		Assert.isTrue(fixUpTask.getMaximumPrice().equals(80.0));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("customer3");
		FixUpTask fixUpTask;
		Collection<FixUpTask> fixUpTasks;
		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask13"));
		this.fixUpTaskService.delete(fixUpTask);
		fixUpTasks = this.fixUpTaskService.findAll();
		Assert.isTrue(!fixUpTasks.contains(fixUpTask));
		super.unauthenticate();
	}

	@Test
	public void testFindFixUpTasksByCustomerLogged() {
		super.authenticate("customer1");
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findFixUpTasksByCustomerLogged();
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFixUpTaskByCustomerLoggedId() {
		super.authenticate("customer1");
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findFixUpTaskByCustomerLoggedId(super.getEntityId("fixUpTask1"));
		Assert.notNull(fixUpTask);
		super.unauthenticate();
	}

	@Test
	public void testFindFixUpTasksByCustomerId() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		final Customer customer = this.customerService.findOne(super.getEntityId("customer1"));
		fixUpTasks = this.fixUpTaskService.findFixUpTasksByCustomerId(customer.getId());
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFilterKeyWordFixUpTask() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findFilterKeyWordFixUpTask("Door");
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFilterCategoryNameFixUpTasks() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findFilterCategoryNameFixUpTasks("Carpentry");
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFilterPriceFixUpTasks() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findFilterPriceFixUpTasks(20.0, 800.0);
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFilterDateFixUpTasks() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		final Calendar cal = Calendar.getInstance();
		cal.set(2018, 0, 14);
		final Date startDate = cal.getTime();
		cal.set(2018, 1, 16);
		final Date endDate = cal.getTime();
		fixUpTasks = this.fixUpTaskService.findFilterDateFixUpTasks(startDate, endDate);
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testFindFilterWarrantyIdFixUpTasks() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		final Warranty warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		fixUpTasks = this.fixUpTaskService.findFilterWarrantyIdFixUpTasks(warranty.getId());
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}

	@Test
	public void testfindFixUpTasksFromFinderHandyWorkedLogged() {
		super.authenticate("handyworker1");
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findFixUpTasksFromFinderHandyWorkedLogged(null, null, null, null, null, 0, 0);
		Assert.notNull(fixUpTasks);
		super.unauthenticate();
	}
}
