
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository
	@Autowired
	private RefereeRepository	refereeRepository;

	// Supporting services
	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Referee create() {
		Referee result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Referee();
		final Collection<Box> boxes = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.REFEREE);
		userAccount.addAuthority(auth);
		result.setBoxes(boxes);
		result.setUserAccount(userAccount);
		result.setIsSuspicious(false);

		return result;
	}

	public Collection<Referee> findAll() {
		Collection<Referee> result;

		result = this.refereeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Referee findOne(final int refereeId) {
		Assert.isTrue(refereeId != 0);

		Referee result;

		result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);

		return result;
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);

		Referee result;

		result = (Referee) this.actorService.save(referee);
		result = this.refereeRepository.save(result);

		return result;
	}

	public void delete(final Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);
		Assert.isTrue(this.refereeRepository.exists(referee.getId()));

		this.refereeRepository.delete(referee);
	}

	// Other business methods
	public Referee findRefereeByReportId(final int reportId) {
		Assert.isTrue(reportId != 0);

		Referee result;

		result = this.refereeRepository.findRefereeByReportId(reportId);
		return result;
	}

}
