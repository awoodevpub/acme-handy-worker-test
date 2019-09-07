
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
import domain.HandyWorker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testCreate() {
		final HandyWorker handyWorker = this.handyWorkerService.create();
		Assert.notNull(handyWorker);
	}

	@Test
	public void testFindAll() {
		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();
		Assert.notNull(handyWorkers);
	}

	@Test
	public void testFindOne() {
		HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		Assert.notNull(handyWorker);
	}

	@Test
	public void testCreateSave() {
		HandyWorker handyWorker;
		Collection<HandyWorker> handyWorkers;
		handyWorker = this.handyWorkerService.create();
		handyWorker.setName("Pepito");
		handyWorker.setSurname("Vazquez");
		handyWorker.setEmail("pepitoVazquez@gmail.com");
		handyWorker.setPhoneNumber("629402942");
		final UserAccount userAccount = handyWorker.getUserAccount();
		userAccount.setUsername("PepitoV");
		userAccount.setPassword("acme1032");
		handyWorker.setUserAccount(userAccount);
		handyWorker = this.handyWorkerService.save(handyWorker);
		handyWorkers = this.handyWorkerService.findAll();
		final Authority authorityHandyWorker = new Authority();
		authorityHandyWorker.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(handyWorker.getUserAccount().getAuthorities().contains(authorityHandyWorker));
		for (final Box b : handyWorker.getBoxes())
			Assert.isTrue(b.getName().equals("In Box") || b.getName().equals("Out Box") || b.getName().equals("Trash Box") || b.getName().equals("Spam Box") && b.getIsSystemBox());
		Assert.isTrue(handyWorker.getMake().equals(handyWorker.getName() + handyWorker.getMiddleName() + handyWorker.getSurname()) || handyWorker.getMake().equals(handyWorker.getName() + handyWorker.getSurname()));
		Assert.isTrue(handyWorker.getPhoneNumber().startsWith(this.systemConfigurationService.getConfiguration().getPhoneCountryCode()) || handyWorker.getPhoneNumber() == null);
		Assert.isTrue(handyWorkers.contains(handyWorker));
	}

	@Test
	public void testModifySave() {
		super.authenticate("handyworker1");
		HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		handyWorker.setMake("MyProMake");
		handyWorker = this.handyWorkerService.save(handyWorker);
		Assert.isTrue(handyWorker.getMake().equals("MyProMake"));
		super.unauthenticate();
	}
}
