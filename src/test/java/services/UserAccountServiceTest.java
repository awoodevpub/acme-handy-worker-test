
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

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserAccountServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private UserAccountService	userAccountService;


	@Test
	public void testCreate() {
		final UserAccount userAccount = this.userAccountService.create();
		Assert.notNull(userAccount);
	}

	@Test
	public void testFindAll() {
		final Collection<UserAccount> userAccounts = this.userAccountService.findAll();
		Assert.notNull(userAccounts);
	}

	@Test
	public void testFindOne() {
		UserAccount userAccount;
		userAccount = this.userAccountService.findOne(super.getEntityId("userAccount1"));
		Assert.notNull(userAccount);
	}

	@Test
	public void testCreateSave() {
		UserAccount userAccount;
		Collection<UserAccount> userAccounts;
		userAccount = this.userAccountService.create();
		userAccount.setUsername("UserPrueba");
		userAccount.setPassword("acme1032");
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		final Collection<Authority> authorities = new HashSet<>();
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		userAccount = this.userAccountService.save(userAccount);
		userAccounts = this.userAccountService.findAll();
		Assert.isTrue(userAccounts.contains(userAccount));
	}

	@Test
	public void testModifySave() {
		UserAccount userAccount;
		userAccount = this.userAccountService.findOne(super.getEntityId("userAccount1"));
		userAccount.setUsername("UserPrueba");
		userAccount = this.userAccountService.save(userAccount);
		Assert.isTrue(userAccount.getUsername().equals("UserPrueba"));
	}
}
