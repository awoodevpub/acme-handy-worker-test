
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Actor;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	// Managed repository
	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services

	@Autowired
	TutorialService				tutorialService;

	@Autowired
	ActorService				actorService;

	@Autowired
	HandyWorkerService			handyWorkerService;


	// Simple CRUD methods

	public Section create() {
		Section result;

		result = new Section();

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Section findOne(final int sectionId) {
		Assert.isTrue(sectionId != 0);

		Section result;

		result = this.sectionRepository.findOne(sectionId);
		Assert.notNull(result);

		return result;
	}

	public Section save(final Section section, final Tutorial tutorial) {
		Assert.notNull(section);
		Section result;

		final Collection<Section> sections = tutorial.getSections();

		if (section.getId() == 0) {
			final Integer number = sections.size() + 1;

			section.setNumber(number);

			result = this.sectionRepository.save(section);

			sections.add(result);
			tutorial.setSections(sections);
			this.tutorialService.saveAux(tutorial);
		} else
			result = this.sectionRepository.save(section);
		return result;
	}

	public void delete(final Section section, final Tutorial tutorial) {
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		Assert.isTrue(this.sectionRepository.exists(section.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByTutorialId(tutorial.getId());
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		final Collection<Section> sections = tutorial.getSections();
		sections.remove(section);

		this.sectionRepository.delete(section);

	}
	// Other business methods

}
