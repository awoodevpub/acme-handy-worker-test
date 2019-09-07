
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Actor;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	// Managed repository
	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;


	// Simple CRUD methods
	// R48.1, R49.2
	public Endorsement create() {
		Endorsement result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		result = new Endorsement();
		final Collection<String> comments = new HashSet<>();
		final Date date = new Date(System.currentTimeMillis() - 1);
		result.setMomentWritten(date);
		result.setComments(comments);

		if (actorLogged instanceof Customer)
			result.setEndorserCustomer((Customer) actorLogged);
		else if (actorLogged instanceof HandyWorker)
			result.setEndorserHandyWorker((HandyWorker) actorLogged);

		return result;
	}

	public Collection<Endorsement> findAll() {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Endorsement findOne(final int endorsementId) {
		Assert.isTrue(endorsementId != 0);

		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);

		return result;
	}

	// R48.1, R49.2
	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		final Endorsement result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		if (actorLogged instanceof Customer) {
			if (endorsement.getId() == 0) {
				Assert.notNull(endorsement.getEndorserCustomer());
				Assert.notNull(endorsement.getEndorsedHandyWorker());
				Assert.isNull(endorsement.getEndorserHandyWorker());
				Assert.isNull(endorsement.getEndorsedCustomer());
				final Collection<FixUpTask> fixUpTasksCustomer = endorsement.getEndorserCustomer().getFixUpTasks();
				final Collection<FixUpTask> fixUpTasksHandyWorker = this.fixUpTaskService.findFixUpTasksByHandyWorkerId(endorsement.getEndorsedHandyWorker().getId());
				final Collection<FixUpTask> intersection = new HashSet<>();
				intersection.addAll(fixUpTasksCustomer);
				intersection.retainAll(fixUpTasksHandyWorker);
				Assert.isTrue(!intersection.isEmpty());
			} else {
				final Customer customerOwner = this.customerService.findCustomerByEndorsementGivenId(endorsement.getId());
				Assert.isTrue(actorLogged.equals(customerOwner));
			}
		} else if (actorLogged instanceof HandyWorker)
			if (endorsement.getId() == 0) {
				Assert.notNull(endorsement.getEndorserHandyWorker());
				Assert.notNull(endorsement.getEndorsedCustomer());
				Assert.isNull(endorsement.getEndorserCustomer());
				Assert.isNull(endorsement.getEndorsedHandyWorker());
				final Collection<FixUpTask> fixUpTasksHandyWorker = this.fixUpTaskService.findFixUpTasksByHandyWorkerId(endorsement.getEndorserHandyWorker().getId());
				final Collection<FixUpTask> fixUpTasksCustomer = endorsement.getEndorsedCustomer().getFixUpTasks();
				final Collection<FixUpTask> intersection = new HashSet<>();
				intersection.addAll(fixUpTasksHandyWorker);
				intersection.retainAll(fixUpTasksCustomer);
				Assert.isTrue(!intersection.isEmpty());
			} else {
				final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByEndorsementGivenId(endorsement.getId());
				Assert.isTrue(actorLogged.equals(handyWorkerOwner));
			}

		result = this.endorsementRepository.save(endorsement);

		return result;
	}
	// R48.1, R49.2
	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		if (actorLogged instanceof Customer) {
			final Customer customerOwner = this.customerService.findCustomerByEndorsementGivenId(endorsement.getId());
			Assert.isTrue(actorLogged.equals(customerOwner));
		} else {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByEndorsementGivenId(endorsement.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		this.endorsementRepository.delete(endorsement);
	}

	// Other business methods
	// R48.1, R49.2
	public Collection<Endorsement> findEndorsementsGivenByActorLoggedId() {
		Collection<Endorsement> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		if (actorLogged instanceof Customer)
			result = this.endorsementRepository.findEndorsementsGivenByCustomerLoggedId(actorLogged.getId());
		else
			result = this.endorsementRepository.findEndorsementsGivenByHandyWorkerLoggedId(actorLogged.getId());

		return result;
	}

	public Collection<Endorsement> findEndorsementsReceivedByActorLoggedId() {
		Collection<Endorsement> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		if (actorLogged instanceof Customer)
			result = this.endorsementRepository.findEndorsementsReceivedByCustomerLoggedId(actorLogged.getId());
		else
			result = this.endorsementRepository.findEndorsementsReceivedByHandyWorkerLoggedId(actorLogged.getId());

		return result;
	}

	// R48.1, R49.2
	public Endorsement findEndorsementGivenByActorLoggedId(final int endorsementId) {
		Endorsement result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorker(actorLogged);

		if (actorLogged instanceof Customer) {
			final Customer customerOwner = this.customerService.findCustomerByEndorsementGivenId(endorsementId);
			Assert.isTrue(actorLogged.equals(customerOwner));
		} else {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByEndorsementGivenId(endorsementId);
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.endorsementRepository.findOne(endorsementId);
		return result;
	}

}
