
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Actor;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository
	@Autowired
	private PhaseRepository				phaseRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R11.4
	public Phase create() {
		Phase result;

		result = new Phase();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		return result;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;

		result = this.phaseRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Phase findOne(final int phaseId) {
		Assert.isTrue(phaseId != 0);

		Phase result;

		result = this.phaseRepository.findOne(phaseId);
		Assert.notNull(result);

		return result;
	}

	// R11.4
	public Collection<Phase> findWorkPlanByFixUpTaskId(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		Collection<Phase> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final Application application = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTaskId);
		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(application.getId());
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		result = fixUpTask.getPhases();

		return result;
	}

	// R11.4
	public Phase save(final Phase phase, final FixUpTask fixUpTask) {
		Assert.notNull(phase);
		Assert.notNull(fixUpTask);

		Phase result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		//R7
		Assert.isTrue(phase.getStartMoment().after(fixUpTask.getStartDate()), "phase.error.f1");
		Assert.isTrue(phase.getEndMoment().before(fixUpTask.getEndDate()) , "phase.error.f2");

		if (phase.getId() == 0) {
			final Application application = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTask.getId());
			Assert.notNull(application);
			result = this.phaseRepository.save(phase);
			final Collection<Phase> phasesFixUpTask = fixUpTask.getPhases();
			phasesFixUpTask.add(result);
			fixUpTask.setPhases(phasesFixUpTask);
			this.fixUpTaskService.saveForPhases(fixUpTask);
		} else {
			Assert.isTrue(fixUpTask.getPhases().contains(phase));
			result = this.phaseRepository.save(phase);
		}

		// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getDescription().contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}

		return result;
	}

	// R11.4
	public Collection<Phase> saveWorkPlan(final Collection<Phase> phases, final FixUpTask fixUpTask) {
		Assert.notNull(phases);
		Assert.notNull(fixUpTask);

		Collection<Phase> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		Assert.isTrue(fixUpTask.getPhases().containsAll(phases));

		//R7
		for (final Phase p : phases) {
			Assert.isTrue(p.getStartMoment().after(fixUpTask.getStartDate()));
			Assert.isTrue(p.getEndMoment().before(fixUpTask.getEndDate()));
		}
		result = this.phaseRepository.save(phases);
		final Collection<Phase> phasesFixUpTask = fixUpTask.getPhases();
		phasesFixUpTask.addAll(result);
		fixUpTask.setPhases(phasesFixUpTask);
		this.fixUpTaskService.saveForPhases(fixUpTask);

		// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords)
			for (final Phase p : result) {
				final Boolean flagSpam = (p.getDescription().contains(sw)) ? true : false;
				if (flagSpam) {
					actorLogged.setIsSuspicious(flagSpam);
					this.actorService.saveForSuspiciusActor(actorLogged);
					break;
				}
			}

		return result;
	}

	// R11.4
	public void delete(final Phase phase, final FixUpTask fixUpTask) {
		Assert.notNull(phase);
		Assert.notNull(fixUpTask);
		Assert.isTrue(phase.getId() != 0);
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(this.phaseRepository.exists(phase.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final Application application = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTask.getId());
		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(application.getId());
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		Assert.isTrue(fixUpTask.getPhases().contains(phase));

		final Collection<Phase> phasesFixUpTask = fixUpTask.getPhases();
		phasesFixUpTask.remove(phase);
		fixUpTask.setPhases(phasesFixUpTask);
		this.fixUpTaskService.saveForPhases(fixUpTask);

		this.phaseRepository.delete(phase);
	}

	// R11.4
	public void deleteWorkPlan(final Collection<Phase> phases, final FixUpTask fixUpTask) {
		Assert.notNull(phases);
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final Application application = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTask.getId());
		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(application.getId());
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		Assert.isTrue(fixUpTask.getPhases().containsAll(phases));

		final Collection<Phase> phasesFixUpTask = fixUpTask.getPhases();
		phasesFixUpTask.removeAll(phases);
		fixUpTask.setPhases(phasesFixUpTask);
		this.fixUpTaskService.saveForPhases(fixUpTask);

		this.phaseRepository.delete(phases);
	}

	// Other business methods

}
