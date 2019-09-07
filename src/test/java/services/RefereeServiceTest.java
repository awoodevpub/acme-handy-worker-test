
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
import domain.Referee;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private RefereeService				refereeService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");
		final Referee referee = this.refereeService.create();
		Assert.notNull(referee);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Referee> referees = this.refereeService.findAll();
		Assert.notNull(referees);
	}

	@Test
	public void testFindOne() {
		Referee referee;
		referee = this.refereeService.findOne(super.getEntityId("referee1"));
		Assert.notNull(referee);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("admin1");
		Referee referee;
		referee = this.refereeService.create();
		referee.setName("Pepito");
		referee.setSurname("Vazquez");
		referee.setEmail("pepitoVazquez@gmail.com");
		referee.setPhoneNumber("629402942");
		final UserAccount userAccount = referee.getUserAccount();
		userAccount.setUsername("PepitoV");
		userAccount.setPassword("acme1032");
		referee.setUserAccount(userAccount);
		referee = this.refereeService.save(referee);
		referee = this.refereeService.findOne(referee.getId());
		Assert.notNull(referee);
		final Authority authorityReferee = new Authority();
		authorityReferee.setAuthority(Authority.REFEREE);
		Assert.isTrue(referee.getUserAccount().getAuthorities().contains(authorityReferee));
		for (final Box b : referee.getBoxes())
			Assert.isTrue(b.getName().equals("In Box") || b.getName().equals("Out Box") || b.getName().equals("Trash Box") || b.getName().equals("Spam Box") && b.getIsSystemBox());
		Assert.isTrue(referee.getPhoneNumber().startsWith(this.systemConfigurationService.getConfiguration().getPhoneCountryCode()) || referee.getPhoneNumber() == null);
		super.unauthenticate();
	}
}
