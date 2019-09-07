
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private NoteService		noteService;

	@Autowired
	private ReportService	reportService;


	@Test
	public void testCreateForCustomer() {
		super.authenticate("customer1");
		final Note note = this.noteService.create();
		Assert.notNull(note);
		super.unauthenticate();
	}

	@Test
	public void testCreateForReferee() {
		super.authenticate("referee1");
		final Note note = this.noteService.create();
		Assert.notNull(note);
		super.unauthenticate();
	}

	@Test
	public void testCreateForHandyWorker() {
		super.authenticate("handyWorker1");
		final Note note = this.noteService.create();
		Assert.notNull(note);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Note> notes = this.noteService.findAll();
		Assert.notNull(notes);
	}

	@Test
	public void testFindOne() {
		Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		Assert.notNull(note);
	}

	@Test
	public void testCreateSaveForCustomer() {
		super.authenticate("customer1");
		Note note;
		Collection<Note> notes;
		note = this.noteService.create();
		final String mandatoryComment = "Test comment";
		final Collection<String> customerComments = note.getCustomerComments();
		customerComments.add(mandatoryComment);
		note.setCustomerComments(customerComments);
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		note = this.noteService.save(note, report);
		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(note));
		super.unauthenticate();
	}

	@Test
	public void testCreateSaveForReferee() {
		super.authenticate("referee1");
		Note note;
		Collection<Note> notes;
		note = this.noteService.create();
		final String mandatoryComment = "Test comment";
		final Collection<String> refereeComments = note.getRefereeComments();
		refereeComments.add(mandatoryComment);
		note.setRefereeComments(refereeComments);
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		note = this.noteService.save(note, report);
		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(note));
		super.unauthenticate();
	}

	@Test
	public void testCreateSaveForHandyWorker() {
		super.authenticate("handyWorker1");
		Note note;
		Collection<Note> notes;
		note = this.noteService.create();
		final String mandatoryComment = "Test comment";
		final Collection<String> handyWorkerComments = note.getHandyWorkerComments();
		handyWorkerComments.add(mandatoryComment);
		note.setHandyWorkerComments(handyWorkerComments);
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		note = this.noteService.save(note, report);
		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(note));
		super.unauthenticate();
	}

	@Test
	public void testWriteCommentNoteCustomer() {
		super.authenticate("customer1");
		Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		final String comment = "Test comment";
		note = this.noteService.writeComment(note, report, comment);
		Assert.isTrue(note.getCustomerComments().contains("Test comment"));
		super.unauthenticate();
	}

	@Test
	public void testWriteCommentNoteReferee() {
		super.authenticate("referee1");
		Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		final String comment = "Test comment";
		note = this.noteService.writeComment(note, report, comment);
		Assert.isTrue(note.getRefereeComments().contains("Test comment"));
		super.unauthenticate();
	}

	@Test
	public void testWriteCommentNoteHandyWorker() {
		super.authenticate("handyWorker1");
		Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		final Report report = this.reportService.findOne(super.getEntityId("report1"));
		final String comment = "Test comment";
		note = this.noteService.writeComment(note, report, comment);
		Assert.isTrue(note.getHandyWorkerComments().contains("Test comment"));
		super.unauthenticate();
	}

}
