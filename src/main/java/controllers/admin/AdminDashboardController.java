
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.NoteService;
import services.OblemicService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class AdminDashboardController extends AbstractController {

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
	
	@Autowired
	private OblemicService		traneutService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {

		final ModelAndView result;

		//FixUpTask per User Stadistics 
		final String[] ratiosFixUpTask = this.fixUpTaskService.getCustomerFixUpTasksStatistics().split(",");
		final String minNumFixUpTasks = ratiosFixUpTask[1];
		final String maxNumFixUpTasks = ratiosFixUpTask[2];
		final String averageFixUpTasks = ratiosFixUpTask[0];
		final String standarDesviationFixUpTasks = ratiosFixUpTask[3];

		//Number of applications per FixUpTask Stadistics
		final String[] ratiosApplicationsPerFixUpTask = this.applicationService.getFixUpTaskApplicationsStatistics().split(",");
		final String minApplicationsPerFixUpTask = ratiosApplicationsPerFixUpTask[1];
		final String maxApplicationsPerFixUpTask = ratiosApplicationsPerFixUpTask[2];
		final String averageApplicationsPerFixUpTask = ratiosApplicationsPerFixUpTask[0];
		final String standarDesviationApplicationsPerFixUpTask = ratiosApplicationsPerFixUpTask[3];

		//Maximun Prices per FixUpTask Stadistics
		final String[] ratiosMaximumPricesFixUpTasks = this.fixUpTaskService.getMaximumPricesFixUpTasksStatistics().split(",");
		final String minMaximumPricesFixUpTasks = ratiosMaximumPricesFixUpTasks[1];
		final String maxMaximumPricesFixUpTasks = ratiosMaximumPricesFixUpTasks[2];
		final String averageMaximumPricesFixUpTasks = ratiosMaximumPricesFixUpTasks[0];
		final String standarDesviationMaximumPricesFixUpTasks = ratiosMaximumPricesFixUpTasks[3];

		//Price offered in the application Stadistics
		final String[] ratiosPriceOfferedApplications = this.applicationService.getOfferedPricesApplicationsStatistics().split(",");
		final String minPriceOfferedApplications = ratiosPriceOfferedApplications[1];
		final String maxPriceOfferedApplications = ratiosPriceOfferedApplications[2];
		final String averagePriceOfferedApplications = ratiosPriceOfferedApplications[0];
		final String standarDesviationPriceOfferedApplications = ratiosPriceOfferedApplications[3];

		//Number of Complaints per FixUpTask Stadistics
		final String[] ratiosFixUpTasksComplaint = this.complaintService.getFixUpTaskComplaintsStatistics().split(",");
		final String minFixUpTasksComplaint = ratiosFixUpTasksComplaint[1];
		final String maxFixUpTasksComplaint = ratiosFixUpTasksComplaint[2];
		final String averageFixUpTasksComplaint = ratiosFixUpTasksComplaint[0];
		final String standarDesviationFixUpTasksComplaint = ratiosFixUpTasksComplaint[3];

		//Number of Notes per Referee Report Stadistics
		final String[] ratiosNotesPerRefeeeReport = this.noteService.getReportNotesStatistics().split(",");
		final String minNotesPerRefeeeReport = ratiosNotesPerRefeeeReport[1];
		final String maxNotesPerRefeeeReport = ratiosNotesPerRefeeeReport[2];
		final String averageNotesPerRefeeeReport = ratiosNotesPerRefeeeReport[0];
		final String standarDesviationNotesPerRefeeeReport = ratiosNotesPerRefeeeReport[3];
		
		// --------------------- Control check ---------------------------------------------
		
		//Traneuts per FixUpTask Stadistics 
		final String[] TraneutsPerFixUpTaskStats = this.traneutService.getOblemicsFixUpTasksStatistics().split(",");
		final String avgTraneutsPerFixUpTask = TraneutsPerFixUpTaskStats[0];
		final String stddevTraneutsPerFixUpTask = TraneutsPerFixUpTaskStats[1];

		// Ratio of published traneuts versus total number of fixUpTasks
		final Double RatioPublishedTraneuts = this.traneutService.getRatioPublishedOblemics();
		
		// Ratio of unpublished Traneut versus total number of FixUpTasks
		final Double RatioUnpublishedTraneuts = this.traneutService.getRatioUnpublishedOblemics();
	
		// ---------------------------------------------------------------------------------

		//------------------------------------------------------------------------------------------------

		result = new ModelAndView("dashboard/show");

		result.addObject("minNumFixUpTasks", minNumFixUpTasks);
		result.addObject("maxNumFixUpTasks", maxNumFixUpTasks);
		result.addObject("averageFixUpTasks", averageFixUpTasks);
		result.addObject("standarDesviationFixUpTasks", standarDesviationFixUpTasks);

		result.addObject("minApplicationsPerFixUpTask", minApplicationsPerFixUpTask);
		result.addObject("maxApplicationsPerFixUpTask", maxApplicationsPerFixUpTask);
		result.addObject("averageApplicationsPerFixUpTask", averageApplicationsPerFixUpTask);
		result.addObject("standarDesviationApplicationsPerFixUpTask", standarDesviationApplicationsPerFixUpTask);

		result.addObject("minMaximumPricesFixUpTasks", minMaximumPricesFixUpTasks);
		result.addObject("maxMaximumPricesFixUpTasks", maxMaximumPricesFixUpTasks);
		result.addObject("averageMaximumPricesFixUpTasks", averageMaximumPricesFixUpTasks);
		result.addObject("standarDesviationMaximumPricesFixUpTasks", standarDesviationMaximumPricesFixUpTasks);

		result.addObject("minPriceOfferedApplications", minPriceOfferedApplications);
		result.addObject("maxPriceOfferedApplications", maxPriceOfferedApplications);
		result.addObject("averagePriceOfferedApplications", averagePriceOfferedApplications);
		result.addObject("standarDesviationPriceOfferedApplications", standarDesviationPriceOfferedApplications);

		result.addObject("ratioPendingApplications", this.applicationService.getRatioPendingApplications());
		result.addObject("rattioAcceptedAplications", this.applicationService.getRatioAcceptedApplications());
		result.addObject("ratioRejectedApplications", this.applicationService.getRatioRejectedApplications());
		result.addObject("ratioPendingApplicationsWithElapsedPeriod", this.applicationService.getRatioPendingApplicationsWithElapsedPeriod());
		result.addObject("tenPercentFixUpTasksAverage", this.customerService.getCustomersTenPercentFixUpTasksThanAverage());
		result.addObject("handyWorkersGotAcceptedMoreTenPercent", this.handyWorkerService.getHandyWorkerGotAcceptedTenPercentApplications());

		result.addObject("minFixUpTasksComplaint", minFixUpTasksComplaint);
		result.addObject("maxFixUpTasksComplaint", maxFixUpTasksComplaint);
		result.addObject("averageFixUpTasksComplaint", averageFixUpTasksComplaint);
		result.addObject("standarDesviationFixUpTasksComplaint", standarDesviationFixUpTasksComplaint);

		result.addObject("minNotesPerRefeeeReport", minNotesPerRefeeeReport);
		result.addObject("maxNotesPerRefeeeReport", maxNotesPerRefeeeReport);
		result.addObject("averageNotesPerRefeeeReport", averageNotesPerRefeeeReport);
		result.addObject("standarDesviationNotesPerRefeeeReport", standarDesviationNotesPerRefeeeReport);

		result.addObject("ratiofixUpTasksWithComplaint", this.fixUpTaskService.getRatioOfFixUpTaskWithComplaint());
		result.addObject("topThreeCustomers", this.customerService.findTopThreeCustomersByComplaints());
		result.addObject("topThreeHandyWorkers", this.handyWorkerService.findTopThreeHandyWorkerByComplaints());
		
		// Control check 
		result.addObject("avgTraneutsPerFixUpTask", avgTraneutsPerFixUpTask);
		result.addObject("stddevTraneutsPerFixUpTask", stddevTraneutsPerFixUpTask);
		
		result.addObject("RatioPublishedTraneuts", RatioPublishedTraneuts);
		result.addObject("RatioUnpublishedTraneuts", RatioUnpublishedTraneuts);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return result;
	}
}
