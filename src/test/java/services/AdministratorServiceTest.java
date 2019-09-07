
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
import domain.Administrator;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");
		final Administrator administrator = this.administratorService.create();
		Assert.notNull(administrator);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Administrator> administrators = this.administratorService.findAll();
		Assert.notNull(administrators);
	}

	@Test
	public void testFindOne() {
		Administrator administrator;
		administrator = this.administratorService.findOne(super.getEntityId("administrator1"));
		Assert.notNull(administrator);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("admin1");
		Administrator administrator;
		administrator = this.administratorService.create();
		administrator.setName("Pepito");
		administrator.setSurname("Vazquez");
		administrator.setEmail("pepitoVazquez@gmail.com");
		administrator.setPhoneNumber("629402942");
		final UserAccount userAccount = administrator.getUserAccount();
		userAccount.setUsername("PepitoV");
		userAccount.setPassword("acme1032");
		administrator.setUserAccount(userAccount);
		administrator = this.administratorService.save(administrator);
		administrator = this.administratorService.findOne(administrator.getId());
		Assert.notNull(administrator);
		final Authority authorityAdministrator = new Authority();
		authorityAdministrator.setAuthority(Authority.ADMIN);
		Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(authorityAdministrator));
		for (final Box b : administrator.getBoxes())
			Assert.isTrue(b.getName().equals("In Box") || b.getName().equals("Out Box") || b.getName().equals("Trash Box") || b.getName().equals("Spam Box") && b.getIsSystemBox());
		Assert.isTrue(administrator.getPhoneNumber().startsWith(this.systemConfigurationService.getConfiguration().getPhoneCountryCode()) || administrator.getPhoneNumber() == null);
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("admin1");
		Administrator administrator;
		administrator = this.administratorService.findOne(super.getEntityId("administrator2"));
		administrator.setEmail("pepitoVazquezEdit@gmail.com");
		administrator = this.administratorService.save(administrator);
		Assert.isTrue(administrator.getEmail().equals("pepitoVazquezEdit@gmail.com"));
		super.unauthenticate();
	}
}
