
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Actor;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private SectionService		sectionService;


	// Simple CRUD methods
	// R49.1
	public Tutorial create() {
		Tutorial result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = new Tutorial();

		final Date lastUpdated = new Date(System.currentTimeMillis() - 1);
		final Collection<String> pictures = new HashSet<>();
		final Collection<Section> sections = new HashSet<>();
		final Collection<Sponsorship> sponsorships = new HashSet<>();

		result.setHandyWorker((HandyWorker) actorLogged);
		result.setLastUpdated(lastUpdated);
		result.setPictures(pictures);
		result.setSections(sections);
		result.setSponsorships(sponsorships);

		return result;
	}
	// R47.2 (personal data from handyWorker will be showed in views)
	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tutorial findOne(final int tutorialId) {
		Assert.isTrue(tutorialId != 0);

		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);

		return result;
	}

	// R49.1
	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Tutorial result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final Date lastUpdated = new Date(System.currentTimeMillis() - 1);
		tutorial.setLastUpdated(lastUpdated);

		if (tutorial.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByTutorialId(tutorial.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.tutorialRepository.save(tutorial);

		final Collection<Sponsorship> sponsorshipsTutorial = result.getSponsorships();
		if (sponsorshipsTutorial != null)
			for (final Sponsorship sponsorship : sponsorshipsTutorial) {
				sponsorship.setTutorial(result);
				this.sponsorshipService.saveAux(sponsorship);
			}
		return result;
	}

	public Tutorial saveAux(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Tutorial result;

		result = this.tutorialRepository.save(tutorial);

		final Collection<Sponsorship> sponsorshipsTutorial = result.getSponsorships();
		if (sponsorshipsTutorial != null)
			for (final Sponsorship sponsorship : sponsorshipsTutorial) {
				sponsorship.setTutorial(result);
				this.sponsorshipService.saveAux(sponsorship);
			}
		return result;

	}

	// R49.1
	public void delete(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		Assert.isTrue(this.tutorialRepository.exists(tutorial.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final Collection<Sponsorship> sponsorshipsTutorial = tutorial.getSponsorships();
		for (final Sponsorship sponsorship : sponsorshipsTutorial) {
			final Sponsor sponsor = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());
			final Collection<Sponsorship> sponsorshipsSponsor = sponsor.getSponsorships();
			sponsorshipsSponsor.remove(sponsorship);
			sponsor.setSponsorships(sponsorshipsSponsor);
			this.sponsorService.saveForTutorials(sponsor);
			this.sponsorshipService.delete(sponsorship);
		}
		final Collection<Section> sectionsTutorial = tutorial.getSections();
		for (final Section section : sectionsTutorial)
			this.sectionService.delete(section, tutorial);

		this.tutorialRepository.delete(tutorial);
	}

	// Other business methods
	// R49.1
	public Collection<Tutorial> findTutorialsByHandyWorkerLoggedId() {
		Collection<Tutorial> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.tutorialRepository.findTutorialsByHandyWorkerLoggedId(actorLogged.getId());
		return result;
	}
	// NEW 
	public Collection<Tutorial> findTutorialsByHandyWorkerId(final int handyWorkerId) {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findTutorialsByHandyWorkerLoggedId(handyWorkerId);
		return result;
	}

	// R49.1
	public Tutorial findTutorialByHandyWorkerLoggedId(final int tutorialId) {
		Tutorial result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByTutorialId(tutorialId);
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		result = this.tutorialRepository.findOne(tutorialId);
		return result;
	}
}
