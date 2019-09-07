
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
import domain.Actor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private ActorService	actorService;


	@Test
	public void testFindAll() {
		final Collection<Actor> actors = this.actorService.findAll();
		Assert.notNull(actors);
	}

	@Test
	public void testFindOne() {
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("administrator1"));
		Assert.notNull(actor);
	}

	@Test
	public void testModifySave() {
		super.authenticate("admin1");
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("administrator2"));
		actor.setEmail("pepitoVazquezEdit@gmail.com");
		actor = this.actorService.save(actor);
		Assert.isTrue(actor.getEmail().equals("pepitoVazquezEdit@gmail.com"));
		super.unauthenticate();
	}

	@Test
	public void testFindSuspiciusActors() {
		super.authenticate("admin1");
		Collection<Actor> actors;
		actors = this.actorService.findSuspiciousActors();
		Assert.notNull(actors);
		super.unauthenticate();
	}

	@Test
	public void testBanActor() {
		super.authenticate("admin1");
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("customer5"));
		actor = this.actorService.banActor(actor);
		Assert.isTrue(!actor.getUserAccount().getStatusAccount());
		super.unauthenticate();
	}

	@Test
	public void testUnbanActor() {
		super.authenticate("admin1");
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("handyWorker3"));
		actor = this.actorService.unbanActor(actor);
		Assert.isTrue(actor.getUserAccount().getStatusAccount());
		super.unauthenticate();
	}
}
