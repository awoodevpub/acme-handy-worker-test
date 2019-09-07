
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Box;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting services
	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods
	// R8.1, R9.1
	public HandyWorker create() {
		HandyWorker result;

		result = new HandyWorker();
		final Collection<Application> applications = new HashSet<>();
		final Collection<Box> boxes = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Finder finder = this.finderService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.HANDYWORKER);
		userAccount.addAuthority(auth);
		result.setApplications(applications);
		result.setBoxes(boxes);
		result.setUserAccount(userAccount);
		result.setFinder(finder);
		result.setIsSuspicious(false);

		return result;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		Assert.isTrue(handyWorkerId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		HandyWorker result;

		if (handyWorker.getId() == 0) {
			final Finder finder = this.finderService.save(handyWorker.getFinder());
			handyWorker.setFinder(finder);
		}

		if (handyWorker.getMake() == null) {
			String make = handyWorker.getName();
			if (handyWorker.getMiddleName() != null)
				make = make + handyWorker.getMiddleName();
			make = make + handyWorker.getSurname();
			handyWorker.setMake(make);
		}

		result = (HandyWorker) this.actorService.save(handyWorker);
		result = this.handyWorkerRepository.save(result);

		return result;
	}
	public void delete(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		Assert.isTrue(this.handyWorkerRepository.exists(handyWorker.getId()));

		this.handyWorkerRepository.delete(handyWorker);
	}

	// Other business methods
	public HandyWorker findHandyWorkerByApplicationId(final int applicationId) {
		Assert.isTrue(applicationId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByApplicationId(applicationId);
		return result;
	}

	// R12.5
	public Collection<HandyWorker> getHandyWorkerGotAcceptedTenPercentApplications() {
		Collection<HandyWorker> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.handyWorkerRepository.getHandyWorkerGotAcceptedTenPercentApplications();

		return result;
	}

	public HandyWorker findHandyWorkerByReportId(final int reportId) {
		Assert.isTrue(reportId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByReportId(reportId);
		return result;
	}

	public HandyWorker findHandyWorkedByComplaintId(final int complaintId) {
		Assert.isTrue(complaintId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkedByComplaintId(complaintId);
		return result;
	}

	// R38.5
	public Collection<HandyWorker> findTopThreeHandyWorkerByComplaints() {
		Collection<HandyWorker> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.handyWorkerRepository.findTopThreeHandyWorkerByComplaints();
		final List<HandyWorker> resultList = new ArrayList<>(result);

		if (resultList.size() > 3)
			result = resultList.subList(0, 3);

		return result;
	}

	public HandyWorker findHandyWorkerByTutorialId(final int tutorialId) {
		Assert.isTrue(tutorialId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByTutorialId(tutorialId);
		return result;
	}

	public HandyWorker findHandyWorkerByEndorsementGivenId(final int endorsementId) {
		Assert.isTrue(endorsementId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByEndorsementGivenId(endorsementId);
		return result;
	}

	public Collection<HandyWorker> getHandyWorkersOfFixUpTasksCustomerLogged() {

		final Collection<HandyWorker> result = new ArrayList<>();
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);
		final Customer logueado = (Customer) actorLogged;

		final Collection<FixUpTask> fixUpTasksCustomer = logueado.getFixUpTasks();
		Assert.notNull(fixUpTasksCustomer);
		if (!(fixUpTasksCustomer.isEmpty()))
			for (final FixUpTask f : fixUpTasksCustomer) {
				final Collection<Application> applications = f.getApplications();
				Assert.notNull(applications);
				if (!(applications.isEmpty()))
					for (final Application a : applications)
						if (a.getStatus().equals("ACCEPTED")) {
							final HandyWorker hw = a.getHandyWorker();
							Assert.notNull(hw);

							if (!result.contains(hw))
								result.add(hw);
						}

			}
		return result;
	}

	public HandyWorker findHandyWorkerByCurriculumId(final int CurriculumId) {
		Assert.isTrue(CurriculumId != 0);

		HandyWorker result;

		result = this.handyWorkerRepository.findHandyWorkerByCurriculumId(CurriculumId);
		return result;
	}

}
