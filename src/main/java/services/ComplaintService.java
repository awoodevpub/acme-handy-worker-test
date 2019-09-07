
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Actor;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Report;

@Service
@Transactional
public class ComplaintService {

	// Managed repository
	@Autowired
	private ComplaintRepository			complaintRepository;

	// Supporting services
	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R35.1
	public Complaint create() {
		Complaint result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		result = new Complaint();
		final Collection<Report> reports = new HashSet<>();
		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final Date date = new Date(System.currentTimeMillis() - 1);
		result.setMoment(date);
		// R21
		final String ticker = dateFormat.format(date).toString() + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		result.setTicker(ticker);
		//		System.out.println(ticker);
		result.setReports(reports);

		return result;
	}

	public Collection<Complaint> findAll() {
		Collection<Complaint> result;

		result = this.complaintRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Complaint findOne(final int complaintId) {
		Assert.isTrue(complaintId != 0);

		Complaint result;

		result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);

		return result;
	}

	// R35.1
	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(complaint.getId() == 0); //R39

		Complaint result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Date date = new Date(System.currentTimeMillis() - 1);
		complaint.setMoment(date);

		result = this.complaintRepository.save(complaint);

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

	// Other business methods
	// R35.1
	public Collection<Complaint> findComplaintsByCustomerLoggedId() {
		Collection<Complaint> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		result = this.complaintRepository.findComplaintsByCustomerLoggedId(actorLogged.getId());
		return result;
	}

	// R35.1
	public Complaint findComplaintByCustomerLoggedId(final int complaintId) {
		Complaint result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Customer customerOwner = this.customerService.findCustomerByComplaintId(complaintId);
		Assert.isTrue(actorLogged.equals(customerOwner));

		result = this.complaintRepository.findOne(complaintId);
		return result;
	}

	// R36.1
	public Collection<Complaint> findComplantsNoReferee() {
		Collection<Complaint> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		result = this.complaintRepository.findComplaintsWithoutReports();

		return result;
	}

	// R36.1
	public void selfAssignComplaint(final int complaintId) {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		final Collection<Complaint> complaintsWithoutReports = this.complaintRepository.findComplaintsWithoutReports();
		final Complaint complaint = this.complaintRepository.findOne(complaintId);
		Assert.isTrue(complaintsWithoutReports.contains(complaint));
	}

	// R36.2
	public Collection<Complaint> findComplaintsByRefereeLoggedId() {
		Collection<Complaint> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		result = this.complaintRepository.findComplaintsByRefereeId(actorLogged.getId());
		return result;
	}

	public Complaint saveForReports(final Complaint complaint) {
		Assert.notNull(complaint);

		Complaint result;

		result = this.complaintRepository.save(complaint);

		return result;
	}

	// R37.3
	public Collection<Complaint> findComplaintsByHandyWorkerLoggedId() {
		Collection<Complaint> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.complaintRepository.findComplaintsByHandyWorkerLoggedId(actorLogged.getId());
		return result;
	}

	// R37.3
	public Complaint findComplaintByHandyWorkedLoggedId(final int complaintId) {
		Complaint result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkedByComplaintId(complaintId);
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		result = this.complaintRepository.findOne(complaintId);
		return result;
	}

	// R38.5
	public String getFixUpTaskComplaintsStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.complaintRepository.getFixUpTaskComplaintsStatistics();

		return result;
	}

}
