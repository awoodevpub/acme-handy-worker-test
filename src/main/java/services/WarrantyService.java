package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Actor;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository
	@Autowired
	private WarrantyRepository warrantyRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Simple CRUD methods
	// R12.2
	public Warranty create() {
		Warranty result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Warranty();
		final Collection<String> laws = new HashSet<>();
		result.setLaws(laws);
		result.setIsFinalMode(false);

		return result;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Warranty findOne(final int warrantyId) {
		Assert.isTrue(warrantyId != 0);

		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);
		Assert.notNull(result);

		return result;
	}

	// R12.2
	public Warranty save(final Warranty warranty) {
		Assert.notNull(warranty);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Warranty result;

		Assert.isTrue(!warranty.getIsFinalMode());

		result = this.warrantyRepository.save(warranty);

		// R38.2: An actor is considered suspicious if he or she publishes some
		// data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService
				.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getTitle().contains(sw)
					|| result.getTerms().contains(sw) || result.getLaws()
					.contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				System.out.println(actorLogged);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}

		return result;
	}

	// R12.2
	public void delete(final Warranty warranty) {
		Assert.notNull(warranty);
		Assert.isTrue(warranty.getId() != 0);
		Assert.isTrue(this.warrantyRepository.exists(warranty.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!warranty.getIsFinalMode());

		this.warrantyRepository.delete(warranty);
	}

	// Other business methods
	// R12.2
	public Collection<Warranty> findCatalogueWarranties() {
		Collection<Warranty> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// R12.2
	public Warranty findWarranty(final int warrantyId) {
		Assert.isTrue(warrantyId != 0);

		Warranty result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.warrantyRepository.findOne(warrantyId);
		Assert.notNull(result);

		return result;
	}

	public int findIdFromWarranty(final Warranty warranty) {
		int result;

		result = this.warrantyRepository.findIdFromWarranty(warranty);
		return result;
	}

	public Warranty changeMode(Warranty warranty) {
		Warranty result;
		Assert.notNull(warranty);
		Assert.isTrue(warranty.getId() != 0);
		Assert.isTrue(this.warrantyRepository.exists(warranty.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!warranty.getIsFinalMode());
		warranty.setIsFinalMode(true);

		result = this.warrantyRepository.save(warranty);
		return result;

	}
}
