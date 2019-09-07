
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Actor;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository
	@Autowired
	private ReportRepository			reportRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private ComplaintService			complaintService;

	@Autowired
	private RefereeService				refereeService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R36.3
	public Report create() {
		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		result = new Report();
		final Collection<Note> notes = new HashSet<>();
		final Collection<String> linkAttachments = new HashSet<>();
		final Complaint complaint = new Complaint();
		final Referee referee = this.refereeService.findOne(this.actorService.findActorLogged().getId());
		result.setNotes(notes);
		result.setLinkAttachments(linkAttachments);
		result.setComplaint(complaint);
		result.setReferee(referee);
		result.setIsFinalMode(false);
		final Date date = new Date(System.currentTimeMillis() - 1);
		result.setMomentWritten(date);

		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Report> findAllByComplaintId(final int complaintId) {
		Assert.isTrue(complaintId != 0);
		Collection<Report> result;

		result = this.reportRepository.findAllByComplaint(complaintId);
		Assert.notNull(result);

		return result;
	}

	public Report findOne(final int reportId) {
		Assert.isTrue(reportId != 0);

		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	// R36.3
	public Report save(final Report report, final Complaint complaint) {
		Assert.notNull(report);
		final Report auxReport = this.reportRepository.findOne(report.getId());
		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		// checks for existence of report, and if exists checks if it's not in finalMode
		if (auxReport != null)
			Assert.isTrue(!auxReport.getIsFinalMode());
		if (report.getId() == 0) {
			final Date date = new Date(System.currentTimeMillis() - 1);
			report.setMomentWritten(date);
			final Referee refereeLogged = (Referee) actorLogged;
			report.setReferee(refereeLogged);
			report.setComplaint(complaint);
			result = this.reportRepository.save(report);
			final Collection<Report> reports = complaint.getReports();
			reports.add(report);
			complaint.setReports(reports);
			this.complaintService.saveForReports(complaint);
		} else {
			final Referee refereeOwner = this.refereeService.findRefereeByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(refereeOwner));
			result = this.reportRepository.save(report);
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

	public Report saveForNote(final Report report, final Complaint complaint) {
		Assert.notNull(report);
		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		final Date date = new Date(System.currentTimeMillis() - 1);
		report.setMomentWritten(date);
		report.setComplaint(complaint);
		result = this.reportRepository.save(report);
		final Collection<Report> reports = complaint.getReports();
		reports.add(result);
		complaint.setReports(reports);
		this.complaintService.saveForReports(complaint);

		return result;
	}

	// R36.3
	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(this.reportRepository.exists(report.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginReferee(actorLogged);

		Assert.isTrue(!report.getIsFinalMode());

		final Referee refereeOwner = this.refereeService.findRefereeByReportId(report.getId());
		Assert.isTrue(actorLogged.equals(refereeOwner));

		final Collection<Complaint> complaints = this.complaintService.findAll();
		for (final Complaint com : complaints) {
			final Collection<Report> reportsComplaints = com.getReports();
			reportsComplaints.remove(report);
			com.setReports(reportsComplaints);
			this.complaintService.saveForReports(com);
		}
		final Collection<Report> reportsRefereeLogged = refereeOwner.getReports();
		reportsRefereeLogged.remove(report);
		refereeOwner.setReports(reportsRefereeLogged);
		this.refereeService.save(refereeOwner);

		this.reportRepository.delete(report);
	}

	// Other business methods

}
