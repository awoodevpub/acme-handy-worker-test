
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
import domain.Warranty;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private WarrantyService	warrantyService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");
		final Warranty warranty = this.warrantyService.create();
		Assert.notNull(warranty);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Warranty> warranties = this.warrantyService.findAll();
		Assert.notNull(warranties);
	}

	@Test
	public void testFindOne() {
		Warranty warranty;
		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		Assert.notNull(warranty);
	}

	@Test
	public void testFindCatalogueWarranties() {
		super.authenticate("admin1");
		Collection<Warranty> warranties;
		warranties = this.warrantyService.findCatalogueWarranties();
		Assert.notNull(warranties);
		super.unauthenticate();
	}

	@Test
	public void testFindWarranty() {
		super.authenticate("admin1");
		Warranty warranty;
		warranty = this.warrantyService.findWarranty(super.getEntityId("warranty2"));
		Assert.notNull(warranty);
		super.unauthenticate();
	}

	@Test
	public void testCreateSave() {
		super.authenticate("admin1");
		Warranty warranty;
		Collection<Warranty> warranties;
		warranty = this.warrantyService.create();
		final String law1 = "No claims are allowed after the deadline";
		final Collection<String> laws = warranty.getLaws();
		laws.add(law1);
		warranty.setLaws(laws);
		warranty.setTitle("Warranty 1 month");
		warranty.setTerms("This Warranty covers the defects resulting from defective parts");
		warranty = this.warrantyService.save(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(warranties.contains(warranty));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("admin1");
		Warranty warranty;
		warranty = this.warrantyService.findOne(super.getEntityId("warranty2"));
		Assert.isTrue(!warranty.getIsFinalMode());
		warranty.setTitle("Test warranty");
		warranty = this.warrantyService.save(warranty);
		Assert.isTrue(warranty.getTitle().equals("Test warranty"));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("admin1");
		Warranty warranty;
		Collection<Warranty> warranties;
		warranty = this.warrantyService.findOne(super.getEntityId("warranty2"));
		this.warrantyService.delete(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(!warranties.contains(this.warrantyService));
		super.unauthenticate();
	}
}
