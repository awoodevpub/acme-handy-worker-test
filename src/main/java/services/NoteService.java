
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class NoteService {

	// Managed repository
	@Autowired
	private NoteRepository				noteRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private RefereeService				refereeService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private ReportService				reportService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R35.2
	public Note create() {
		Note result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorkerReferee(actorLogged);

		result = new Note();
		final Collection<String> refereeComments = new HashSet<>();
		final Collection<String> customerComments = new HashSet<>();
		final Collection<String> handyWorkerComments = new HashSet<>();
		result.setRefereeComments(refereeComments);
		result.setHandyWorkerComments(handyWorkerComments);
		result.setCustomerComments(customerComments);
		final Date date = new Date(System.currentTimeMillis() - 1);
		result.setMommentWritten(date);

		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Note findOne(final int noteId) {
		Assert.isTrue(noteId != 0);

		Note result;

		result = this.noteRepository.findOne(noteId);
		Assert.notNull(result);

		return result;
	}

	// R35.2, R36.4, R37.4
	public Note save(final Note note, final Report report) {
		Assert.notNull(note);
		Assert.notNull(report);
		// not makes sense
		//	Assert.isTrue(note.getId() == 0); //R39

		Note result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorkerReferee(actorLogged);

		Assert.isTrue(report.getIsFinalMode());

		if (actorLogged instanceof Customer) {
			final Customer customerOwner = this.customerService.findCustomerByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(customerOwner));
			Assert.isTrue(!note.getCustomerComments().isEmpty()); //R34: It is manddatory that the creator of the note leaves a comment
		} else if (actorLogged instanceof Referee) {
			final Referee refereeOwner = this.refereeService.findRefereeByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(refereeOwner));
			Assert.isTrue(!note.getRefereeComments().isEmpty()); //R34: It is manddatory that the creator of the note leaves a comment
		} else if (actorLogged instanceof HandyWorker) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
			Assert.isTrue(!note.getHandyWorkerComments().isEmpty()); //R34: It is manddatory that the creator of the note leaves a comment
		}

		final Date date = new Date(System.currentTimeMillis() - 1);
		note.setMommentWritten(date);
		result = this.noteRepository.save(note);
		if (note.getId() == 0) {
			final Collection<Note> notesReport = report.getNotes();
			notesReport.add(result);
			report.setNotes(notesReport);
			this.reportService.saveForNote(report, report.getComplaint());
		}

		// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getCustomerComments().contains(sw) || result.getRefereeComments().contains(sw) || result.getHandyWorkerComments().contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}

		return result;
	}

	// Other business methods
	// R35.3, R36.5, R37.5
	public Note writeComment(final Note note, final Report report, final String comment) {
		Assert.notNull(note);
		Assert.notNull(report);
		Assert.notNull(comment);
		Assert.isTrue(note.getId() != 0);

		Note result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomerHandyWorkerReferee(actorLogged);

		Assert.isTrue(report.getIsFinalMode());
		Assert.isTrue(report.getNotes().contains(note));

		if (actorLogged instanceof Customer) {
			final Customer customerOwner = this.customerService.findCustomerByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(customerOwner));
			final Collection<String> customerComments = note.getCustomerComments();
			customerComments.add(comment);
			note.setCustomerComments(customerComments);
		} else if (actorLogged instanceof Referee) {
			final Referee refereeOwner = this.refereeService.findRefereeByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(refereeOwner));
			final Collection<String> refereeComments = note.getRefereeComments();
			refereeComments.add(comment);
			note.setRefereeComments(refereeComments);
		} else if (actorLogged instanceof HandyWorker) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByReportId(report.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
			final Collection<String> handyWorkerComments = note.getHandyWorkerComments();
			handyWorkerComments.add(comment);
			note.setHandyWorkerComments(handyWorkerComments);
		}

		result = this.noteRepository.save(note);

		// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getCustomerComments().contains(sw) || result.getRefereeComments().contains(sw) || result.getHandyWorkerComments().contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}

		return result;
	}

	// R38.5
	public String getReportNotesStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.noteRepository.getReportNotesStatistics();

		return result;
	}

}
