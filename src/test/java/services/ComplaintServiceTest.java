
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ReportService		reportService;


	@Test
	public void testCreate() {
		super.authenticate("customer1");
		final Complaint complaint = this.complaintService.create();
		Assert.notNull(complaint);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Complaint> complaints = this.complaintService.findAll();
		Assert.notNull(complaints);
	}

	@Test
	public void testFindOne() {
		Complaint complaint;
		complaint = this.complaintService.findOne(super.getEntityId("complaint1"));
		Assert.notNull(complaint);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");
		Complaint complaint;
		Collection<Complaint> complaints;
		complaint = this.complaintService.create();
		complaint.setDescription("Very bad work");
		complaint = this.complaintService.save(complaint);
		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.contains(complaint));
		super.unauthenticate();
	}

	@Test
	public void testFindComplaintsByCustomerLoggedId() {
		super.authenticate("customer1");
		Collection<Complaint> complaints;
		complaints = this.complaintService.findComplaintsByCustomerLoggedId();
		Assert.notNull(complaints);
		super.unauthenticate();
	}

	@Test
	public void testFindComplaintByCustomerLoggedId() {
		super.authenticate("customer1");
		Complaint complaint;
		complaint = this.complaintService.findComplaintByCustomerLoggedId(super.getEntityId("complaint1"));
		Assert.notNull(complaint);
		super.unauthenticate();
	}

	@Test
	public void testFindComplaintsByRefereeLoggedId() {
		super.authenticate("referee1");
		Collection<Complaint> complaints;
		complaints = this.complaintService.findComplaintsByRefereeLoggedId();
		Assert.notNull(complaints);
		super.unauthenticate();
	}

	@Test
	public void testFindComplantsNoRefereeAndSelfAssign() {
		super.authenticate("referee1");
		final Collection<String> linkAttachments = new HashSet<>();
		final String link = "http://www.acme-handy-worker.com/pdfReport.pdf";
		linkAttachments.add(link);
		final String description = "This customer is dissatisfied";
		final Referee refereeOld = this.refereeService.findOne(super.getEntityId("referee1"));
		final Collection<Complaint> complaintsRefereeOld = this.complaintService.findComplaintsByRefereeLoggedId();
		final Integer oldSizeReports = refereeOld.getReports().size();
		final Integer oldSizeComplaints = complaintsRefereeOld.size();
		final Report report = this.reportService.create();
		this.complaintService.selfAssignComplaint(this.complaintService.findOne(super.getEntityId("complaint5")).getId());
		final Complaint complaint = this.complaintService.findOne(this.complaintService.findOne(super.getEntityId("complaint5")).getId());
		report.setDescription(description);
		report.setLinkAttachments(linkAttachments);
		report.setComplaint(complaint);
		final Collection<Report> reportsRefereeLogged = refereeOld.getReports();
		reportsRefereeLogged.add(report);
		refereeOld.setReports(reportsRefereeLogged);
		this.refereeService.save(refereeOld);
		this.reportService.save(report, complaint);
		final Collection<Complaint> complaintsRefereeNew = this.complaintService.findComplaintsByRefereeLoggedId();
		final Referee refereeNew = this.refereeService.findOne(super.getEntityId("referee1"));
		final Integer newSizeReports = refereeNew.getReports().size();
		final Integer newSizeComplaints = complaintsRefereeNew.size();
		Assert.isTrue(oldSizeReports < newSizeReports);
		Assert.isTrue(oldSizeComplaints < newSizeComplaints);
		super.unauthenticate();
	}

	@Test
	public void testFindComplaintsByHandyWorkerLoggedId() {
		super.authenticate("handyworker1");
		Collection<Complaint> complaints;
		complaints = this.complaintService.findComplaintsByHandyWorkerLoggedId();
		Assert.notNull(complaints);
		super.unauthenticate();
	}

	@Test
	public void testFindComplaintByHandyWorkerLoggedId() {
		super.authenticate("handyworker1");
		Complaint complaint;
		complaint = this.complaintService.findComplaintByHandyWorkedLoggedId(super.getEntityId("complaint1"));
		Assert.notNull(complaint);
		super.unauthenticate();
	}
}
