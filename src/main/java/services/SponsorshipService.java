
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Actor;
import domain.CreditCard;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services
	@Autowired
	ActorService					actorService;

	@Autowired
	SponsorService					sponsorService;

	@Autowired
	TutorialService					tutorialService;

	@Autowired
	SectionService					sectionService;

	@Autowired
	CreditCardService				creditCardService;


	// Simple CRUD methods
	public Sponsorship create() {
		Sponsorship result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginSponsor(actorLogged);

		result = new Sponsorship();

		final Tutorial tutorial = new Tutorial();
		final HandyWorker handyWorker = new HandyWorker();
		final Date lastUpdated = new Date(System.currentTimeMillis() - 1);
		final Collection<String> pictures = new HashSet<>();
		final Section section = this.sectionService.create();
		final Collection<Section> sections = new HashSet<>();
		sections.add(section);
		final Collection<Sponsorship> sponsorships = new HashSet<>();

		tutorial.setHandyWorker(handyWorker);
		tutorial.setLastUpdated(lastUpdated);
		tutorial.setPictures(pictures);
		tutorial.setSections(sections);
		tutorial.setSponsorships(sponsorships);

		final CreditCard creditCard = this.creditCardService.create();

		result.setCreditCard(creditCard);
		result.setSponsor((Sponsor) actorLogged);
		result.setTutorial(tutorial);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		Sponsorship result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginSponsor(actorLogged);

		final Sponsor sponsor = (Sponsor) actorLogged; //We can't use the "sponsorOwner"

		if (sponsorship.getId() == 0) {
			final Collection<Sponsorship> sponsorships = sponsor.getSponsorships();
			sponsorships.add(sponsorship);
			sponsor.setSponsorships(sponsorships);

			final Tutorial tutorial = sponsorship.getTutorial();
			final Collection<Sponsorship> allSponsorshipTutorial = tutorial.getSponsorships();
			allSponsorshipTutorial.add(sponsorship);
			tutorial.setSponsorships(allSponsorshipTutorial);

			final CreditCard creditCard = sponsorship.getCreditCard();

			final CreditCard creditCardSave = this.creditCardService.save(creditCard);
			sponsorship.setCreditCard(creditCardSave);

			result = this.sponsorshipRepository.save(sponsorship);

			this.tutorialService.saveAux(tutorial);
			this.sponsorService.save(sponsor);
		} else {
			final Sponsor sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());

			Assert.isTrue(actorLogged.equals(sponsorOwner));

			result = this.sponsorshipRepository.save(sponsorship);
		}

		return result;
	}

	public Sponsorship saveAux(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		Sponsorship result;

		result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods

	//R51
	public Sponsorship randomSponsorShip(final Tutorial tutorial) {
		Sponsorship result;

		final Random r = new Random();
		final Collection<Sponsorship> sponsorships = tutorial.getSponsorships();
		final int i = r.nextInt(sponsorships.size());
		result = (Sponsorship) sponsorships.toArray()[i];

		return result;
	}

	public Collection<Sponsorship> findSponsorshipsBySponsorId(final int sponsorId) {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findSponsorshipsBySponsorId(sponsorId);
		return result;
	}

	public Collection<Sponsorship> findSponsorshipsBySponsorLogged() {
		Collection<Sponsorship> result;
		Actor actor;

		actor = this.actorService.findActorLogged();
		this.actorService.checkUserLoginSponsor(actor);

		final int sponsorId = actor.getId();
		result = this.sponsorshipRepository.findSponsorshipsBySponsorId(sponsorId);

		return result;
	}
}
