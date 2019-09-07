
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Box;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;


	// Simple CRUD methods
	// R12.1
	public Administrator create() {
		Administrator result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Administrator();
		final Collection<Box> boxes = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(auth);
		result.setBoxes(boxes);
		result.setUserAccount(userAccount);
		result.setIsSuspicious(false);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);

		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	// R12.1
	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		Administrator result;

		result = (Administrator) this.actorService.save(administrator);
		result = this.administratorRepository.save(result);

		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));

		this.administratorRepository.delete(administrator);
	}

	// Other business methods

}
