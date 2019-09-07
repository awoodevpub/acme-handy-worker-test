
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
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private SponsorService				sponsorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testCreate() {
		final Sponsor sponsor = this.sponsorService.create();
		Assert.notNull(sponsor);
	}

	@Test
	public void testFindAll() {
		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		Assert.notNull(sponsors);
	}

	@Test
	public void testFindOne() {
		Sponsor sponsor;
		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.notNull(sponsor);
	}

	@Test
	public void testCreateSave() {
		Sponsor sponsor;
		sponsor = this.sponsorService.create();
		sponsor.setName("Pepita");
		sponsor.setSurname("Vazquez");
		sponsor.setEmail("pepitaVazquez@gmail.com");
		sponsor.setPhoneNumber("629402942");
		final UserAccount userAccount = sponsor.getUserAccount();
		userAccount.setUsername("PepitaV");
		userAccount.setPassword("acme1032");
		sponsor.setUserAccount(userAccount);
		sponsor = this.sponsorService.save(sponsor);
		sponsor = this.sponsorService.findOne(sponsor.getId());
		Assert.notNull(sponsor);
		final Authority authoritySponsor = new Authority();
		authoritySponsor.setAuthority(Authority.SPONSOR);
		Assert.isTrue(sponsor.getUserAccount().getAuthorities().contains(authoritySponsor));
		for (final Box b : sponsor.getBoxes())
			Assert.isTrue(b.getName().equals("In Box") || b.getName().equals("Out Box") || b.getName().equals("Trash Box") || b.getName().equals("Spam Box") && b.getIsSystemBox());
		Assert.isTrue(sponsor.getPhoneNumber().startsWith(this.systemConfigurationService.getConfiguration().getPhoneCountryCode()) || sponsor.getPhoneNumber() == null);
	}

	@Test
	public void testModifySave() {
		super.authenticate("sponsor1");
		Sponsor sponsor;
		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		sponsor.setEmail("pepitoVazquezEdit@gmail.com");
		sponsor = this.sponsorService.save(sponsor);
		Assert.isTrue(sponsor.getEmail().equals("pepitoVazquezEdit@gmail.com"));
		super.unauthenticate();
	}
}
