
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void testCreateForCustomer() {
		super.authenticate("customer1");
		final Endorsement endorsement = this.endorsementService.create();
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testCreateForHandyWorker() {
		super.authenticate("handyworker1");
		final Endorsement endorsement = this.endorsementService.create();
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Endorsement> endorsements = this.endorsementService.findAll();
		Assert.notNull(endorsements);
	}

	@Test
	public void testFindOne() {
		Endorsement endorsement;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Assert.notNull(endorsement);
	}

	@Test
	public void testFindEndorsementsGivenByActorLoggedIdForCustomer() {
		super.authenticate("customer1");
		Collection<Endorsement> endorsements;
		endorsements = this.endorsementService.findEndorsementsGivenByActorLoggedId();
		Assert.notNull(endorsements);
		super.unauthenticate();
	}

	@Test
	public void testFindEndorsementsGivenByActorLoggedIdForHandyWorker() {
		super.authenticate("handyworker1");
		Collection<Endorsement> endorsements;
		endorsements = this.endorsementService.findEndorsementsGivenByActorLoggedId();
		Assert.notNull(endorsements);
		super.unauthenticate();
	}

	@Test
	public void testFindEndorsementGivenByActorLoggedIdForCustomer() {
		super.authenticate("customer1");
		Endorsement endorsement;
		endorsement = this.endorsementService.findEndorsementGivenByActorLoggedId(super.getEntityId("endorsement4"));
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testFindEndorsementGivenByActorLoggedIdForHandyWorker() {
		super.authenticate("handyworker1");
		Endorsement endorsement;
		endorsement = this.endorsementService.findEndorsementGivenByActorLoggedId(super.getEntityId("endorsement1"));
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testCreateSaveForCustomer() {
		super.authenticate("customer1");
		Endorsement endorsement;
		endorsement = this.endorsementService.create();
		final Collection<String> comments = new HashSet<>();
		final String comment = "Nice job man!";
		comments.add(comment);
		final HandyWorker endorsedHandyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		endorsement.setComments(comments);
		endorsement.setEndorsedHandyWorker(endorsedHandyWorker);
		endorsement = this.endorsementService.save(endorsement);
		endorsement = this.endorsementService.findOne(endorsement.getId());
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testCreateSaveForHandyWorker() {
		super.authenticate("handyworker1");
		Endorsement endorsement;
		endorsement = this.endorsementService.create();
		final Collection<String> comments = new HashSet<>();
		final String comment = "Nice job man!";
		comments.add(comment);
		final Customer endorsedCustomer = this.customerService.findOne(super.getEntityId("customer1"));
		endorsement.setComments(comments);
		endorsement.setEndorsedCustomer(endorsedCustomer);
		endorsement = this.endorsementService.save(endorsement);
		endorsement = this.endorsementService.findOne(endorsement.getId());
		Assert.notNull(endorsement);
		super.unauthenticate();
	}

	@Test
	public void testModifySaveForCustomer() {
		super.authenticate("customer1");
		Endorsement endorsement;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement4"));
		final Collection<String> comments = endorsement.getComments();
		final String comment = "Test Comment";
		comments.add(comment);
		endorsement.setComments(comments);
		endorsement = this.endorsementService.save(endorsement);
		Assert.isTrue(endorsement.getComments().contains("Test Comment"));
		super.unauthenticate();
	}

	@Test
	public void testModifySaveForHandyWorker() {
		super.authenticate("handyworker1");
		Endorsement endorsement;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		final Collection<String> comments = endorsement.getComments();
		final String comment = "Test Comment";
		comments.add(comment);
		endorsement.setComments(comments);
		endorsement = this.endorsementService.save(endorsement);
		Assert.isTrue(endorsement.getComments().contains("Test Comment"));
		super.unauthenticate();
	}

	@Test
	public void testDeleteForCustomer() {
		super.authenticate("customer1");
		Endorsement endorsement;
		Collection<Endorsement> endorsements;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement4"));
		this.endorsementService.delete(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(!endorsements.contains(endorsement));
		super.unauthenticate();
	}

	@Test
	public void testDeleteForHandyWorker() {
		super.authenticate("handyworker1");
		Endorsement endorsement;
		Collection<Endorsement> endorsements;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		this.endorsementService.delete(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(!endorsements.contains(endorsement));
		super.unauthenticate();
	}
}
