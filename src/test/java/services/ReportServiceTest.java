
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
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private ReportService		reportService;

	@Autowired
	private ComplaintService	complaintService;


	@Test
	public void testCreate() {
		super.authenticate("referee1");
		final Report report = this.reportService.create();
		Assert.notNull(report);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Report> reports = this.reportService.findAll();
		Assert.notNull(reports);
	}

	@Test
	public void testFindOne() {
		Report report;
		report = this.reportService.findOne(super.getEntityId("report1"));
		Assert.notNull(report);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("referee1");
		Report report;
		Collection<Report> reports;
		final Complaint complaint = this.complaintService.findOne(super.getEntityId("complaint1"));
		report = this.reportService.create();
		final Collection<String> linkAttachments = new HashSet<>();
		final String link = "http://www.acme-handy-worker.com/pdfReport.pdf";
		linkAttachments.add(link);
		final String description = "This customer is dissatisfied";
		report.setLinkAttachments(linkAttachments);
		report.setDescription(description);
		report = this.reportService.save(report, complaint);
		reports = this.reportService.findAll();
		Assert.isTrue(reports.contains(report));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("referee2");
		Report report;
		report = this.reportService.findOne(super.getEntityId("report3"));
		final Complaint complaint = this.complaintService.findOne(super.getEntityId("complaint3"));
		report.setDescription("Test description");
		report = this.reportService.save(report, complaint);
		Assert.isTrue(report.getDescription().equals("Test description"));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("referee2");
		Report report;
		Collection<Report> reports;
		report = this.reportService.findOne(super.getEntityId("report3"));
		this.reportService.delete(report);
		reports = this.reportService.findAll();
		Assert.isTrue(!reports.contains(report));
		super.unauthenticate();
	}
}
