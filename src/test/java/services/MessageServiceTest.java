
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private MessageService		messageService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		final Message message = this.messageService.create();
		Assert.notNull(message);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Message> messages = this.messageService.findAll();
		Assert.notNull(messages);
	}

	@Test
	public void testFindOne() {
		Message message;
		message = this.messageService.findOne(super.getEntityId("message1"));
		Assert.notNull(message);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");
		Message message;
		Collection<Message> messages;
		message = this.messageService.create();
		message.setSubject("Hi");
		message.setBody("Hello world, give me sex");
		message.setPriority("NEUTRAL");
		final Customer customer1 = this.customerService.findOne(super.getEntityId("customer1"));
		message.setSender(customer1);
		final Customer customer2 = this.customerService.findOne(super.getEntityId("customer2"));
		final HandyWorker handyWorker1 = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		final Collection<Actor> recipients = new HashSet<>();
		Collections.addAll(recipients, customer2, handyWorker1);
		message.setRecipients(recipients);
		message = this.messageService.save(message);
		messages = this.messageService.findAll();

		Assert.isTrue(messages.contains(message));
		final Box outBoxCustomer1 = this.boxService.findOutBoxByActorId(customer1.getId());
		final Box spamBoxCustomer2 = this.boxService.findSpamBoxByActorId(customer2.getId());
		final Box spamBoxHandyWorker1 = this.boxService.findSpamBoxByActorId(handyWorker1.getId());
		Assert.isTrue(outBoxCustomer1.getMessages().contains(message));
		Assert.isTrue(spamBoxCustomer2.getMessages().contains(message));
		Assert.isTrue(spamBoxHandyWorker1.getMessages().contains(message));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("customer1");
		Message message;
		message = this.messageService.findOne(super.getEntityId("message16"));
		this.messageService.delete(message);
		final Customer customer1 = this.customerService.findOne(super.getEntityId("customer1"));
		final Box inBoxCustomer1 = this.boxService.findInBoxByActorId(customer1.getId());
		final Box outBoxCustomer1 = this.boxService.findOutBoxByActorId(customer1.getId());
		final Box spamBoxCustomer1 = this.boxService.findSpamBoxByActorId(customer1.getId());
		final Box trashBoxCustomer1 = this.boxService.findTrashBoxByActorId(customer1.getId());
		Assert.isTrue(!inBoxCustomer1.getMessages().contains(message));
		Assert.isTrue(!outBoxCustomer1.getMessages().contains(message));
		Assert.isTrue(!spamBoxCustomer1.getMessages().contains(message));
		Assert.isTrue(!trashBoxCustomer1.getMessages().contains(message));
		super.unauthenticate();
	}

	@Test
	public void testBroadcastMessage() {
		super.authenticate("admin1");
		Message message;
		Collection<Message> messages;
		message = this.messageService.create();
		message.setSubject("Broadcast test");
		message.setBody("This is a broadcast message");
		message.setPriority("HIGH");
		message = this.messageService.broadcastMessage(message);
		messages = this.messageService.findAll();
		Assert.isTrue(messages.contains(message));
		final Actor sender = this.actorService.findOne(super.getEntityId("administrator2"));
		final Box outBoxSender = this.boxService.findOutBoxByActorId(sender.getId());
		Assert.isTrue(outBoxSender.getMessages().contains(message));
		final Collection<Actor> recievers = this.actorService.findAll();
		recievers.remove(sender);
		for (final Actor a : recievers) {
			final Box inBoxReciever = this.boxService.findInBoxByActorId(a.getId());
			Assert.isTrue(inBoxReciever.getMessages().contains(message));
		}
		super.unauthenticate();
	}
}
