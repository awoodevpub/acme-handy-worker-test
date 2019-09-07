
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
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BoxServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private BoxService	boxService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		final Box box = this.boxService.create();
		Assert.notNull(box);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Box> boxes = this.boxService.findAll();
		Assert.notNull(boxes);
	}

	@Test
	public void testFindOne() {
		Box box;
		box = this.boxService.findOne(super.getEntityId("box1"));
		Assert.notNull(box);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");
		Box box;
		Collection<Box> boxes;
		box = this.boxService.create();
		box.setName("DP Box");
		box = this.boxService.save(box);
		boxes = this.boxService.findAll();
		Assert.isTrue(!box.getIsSystemBox());
		Assert.isTrue(boxes.contains(box));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("customer1");
		Box box;
		box = this.boxService.findOne(super.getEntityId("box117"));
		box.setName("Test box");
		box = this.boxService.save(box);
		Assert.isTrue(box.getName().equals("Test box"));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("customer1");
		Box box;
		Collection<Box> boxes;
		box = this.boxService.findOne(super.getEntityId("box117"));
		this.boxService.delete(box);
		boxes = this.boxService.findAll();
		Assert.isTrue(!boxes.contains(box));
		super.unauthenticate();
	}
}
