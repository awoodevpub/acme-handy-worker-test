
package services;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardAdminServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private NoteService			noteService;


	@Test
	public void testDashboard() {
		super.authenticate("admin1");
		final SortedMap<String, Object> dashboard = new TreeMap<>();
		dashboard.put("Query C/01 result:", this.fixUpTaskService.getCustomerFixUpTasksStatistics());
		dashboard.put("Query C/02 result:", this.applicationService.getFixUpTaskApplicationsStatistics());
		dashboard.put("Query C/03 result:", this.fixUpTaskService.getMaximumPricesFixUpTasksStatistics());
		dashboard.put("Query C/04 result:", this.applicationService.getOfferedPricesApplicationsStatistics());
		dashboard.put("Query C/05 result:", this.applicationService.getRatioPendingApplications());
		dashboard.put("Query C/06 result:", this.applicationService.getRatioAcceptedApplications());
		dashboard.put("Query C/07 result:", this.applicationService.getRatioRejectedApplications());
		dashboard.put("Query C/08 result:", this.applicationService.getRatioPendingApplicationsWithElapsedPeriod());
		dashboard.put("Query C/09 result:", this.customerService.getCustomersTenPercentFixUpTasksThanAverage());
		dashboard.put("Query C/10 result:", this.handyWorkerService.getHandyWorkerGotAcceptedTenPercentApplications());
		dashboard.put("Query B/01 result:", this.complaintService.getFixUpTaskComplaintsStatistics());
		dashboard.put("Query B/02 result:", this.noteService.getReportNotesStatistics());
		dashboard.put("Query B/03 result:", this.fixUpTaskService.getRatioOfFixUpTaskWithComplaint());
		dashboard.put("Query B/04 result:", this.customerService.findTopThreeCustomersByComplaints());
		dashboard.put("Query B/05 result:", this.handyWorkerService.findTopThreeHandyWorkerByComplaints());
		for (final String s : dashboard.keySet()) {
			final Object printObject = dashboard.get(s);
			System.out.println(s + " --> " + printObject);
		}
		super.unauthenticate();
	}
}
