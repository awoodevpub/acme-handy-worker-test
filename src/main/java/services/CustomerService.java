
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	// Managed repository
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services
	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	// R8.1, R9.1
	public Customer create() {
		Customer result;

		result = new Customer();
		final Collection<FixUpTask> fixUpTasks = new HashSet<>();
		final Collection<Box> boxes = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(auth);
		result.setFixUpTasks(fixUpTasks);
		result.setBoxes(boxes);
		result.setUserAccount(userAccount);
		result.setIsSuspicious(false);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Customer findOne(final int customerId) {
		Assert.isTrue(customerId != 0);

		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		Customer result;

		result = (Customer) this.actorService.save(customer);
		result = this.customerRepository.save(result);

		return result;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);
		Assert.isTrue(this.customerRepository.exists(customer.getId()));

		this.customerRepository.delete(customer);
	}

	// Other business methods
	public Customer findCustomerByFixUpTaskId(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByFixUpTaskId(fixUpTaskId);
		return result;
	}

	public Customer findCustomerByApplicationId(final int applicationId) {
		Assert.isTrue(applicationId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByApplicationId(applicationId);
		return result;
	}

	// R12.5
	public Collection<Customer> getCustomersTenPercentFixUpTasksThanAverage() {
		Collection<Customer> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.customerRepository.getCustomersTenPercentFixUpTasksThanAverage();

		return result;
	}

	public Customer findCustomerByComplaintId(final int complaintId) {
		Assert.isTrue(complaintId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByComplaintId(complaintId);
		return result;
	}

	public Customer findCustomerByReportId(final int reportId) {
		Assert.isTrue(reportId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByReportId(reportId);
		return result;
	}

	// R38.5
	public Collection<Customer> findTopThreeCustomersByComplaints() {
		Collection<Customer> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.customerRepository.findTopThreeCustomersByComplaints();
		final List<Customer> resultList = new ArrayList<>(result);

		if (resultList.size() > 3)
			result = resultList.subList(0, 3);

		return result;
	}

	public Customer findCustomerByEndorsementGivenId(final int endorsementId) {
		Assert.isTrue(endorsementId != 0);

		Customer result;

		result = this.customerRepository.findCustomerByEndorsementGivenId(endorsementId);
		return result;
	}

	public Collection<Customer> findCustomersFromFixUpTaskWorkedByHandyWorker() {
		final Collection<Customer> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);
		final Integer id = actorLogged.getId();
		result = this.customerRepository.getCustomersOfHandyWorker(id);

		return result;
	}
}
