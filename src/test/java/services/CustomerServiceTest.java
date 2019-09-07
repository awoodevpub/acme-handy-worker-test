
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Box;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private CustomerService				customerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testCreate() {
		final Customer customer = this.customerService.create();
		Assert.notNull(customer);
	}

	@Test
	public void testFindAll() {
		final Collection<Customer> customers = this.customerService.findAll();
		Assert.notNull(customers);
	}

	@Test
	public void testFindOne() {
		Customer customer;
		customer = this.customerService.findOne(super.getEntityId("customer1"));
		Assert.notNull(customer);
	}

	@Test
	public void testCreateSave() {
		Customer customer;
		Collection<Customer> customers;
		customer = this.customerService.create();
		customer.setName("Pepita");
		customer.setSurname("Vazquez");
		customer.setEmail("pepitaVazquez@gmail.com");
		customer.setPhoneNumber("629402942");
		final UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("PepitaV");
		userAccount.setPassword("acme1032");
		customer.setUserAccount(userAccount);
		customer = this.customerService.save(customer);
		customers = this.customerService.findAll();
		final Authority authorityCustomer = new Authority();
		authorityCustomer.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(customer.getUserAccount().getAuthorities().contains(authorityCustomer));
		for (final Box b : customer.getBoxes())
			Assert.isTrue(b.getName().equals("In Box") || b.getName().equals("Out Box") || b.getName().equals("Trash Box") || b.getName().equals("Spam Box") && b.getIsSystemBox());
		Assert.isTrue(customer.getPhoneNumber().startsWith(this.systemConfigurationService.getConfiguration().getPhoneCountryCode()) || customer.getPhoneNumber() == null);
		Assert.isTrue(customers.contains(customer));
	}

	@Test
	public void testModifySave() {
		super.authenticate("customer1");
		Customer customer;
		customer = this.customerService.findOne(super.getEntityId("customer1"));
		customer.setEmail("pepitoVazquezEdit@gmail.com");
		customer = this.customerService.save(customer);
		Assert.isTrue(customer.getEmail().equals("pepitoVazquezEdit@gmail.com"));
		super.unauthenticate();
	}
}
