package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;

import controllers.AbstractController;
import domain.Report;


@Controller
@RequestMapping("/report/handyworker")
public class HandyWorkerReportController extends AbstractController{
	
	@Autowired
	private ReportService reportService;

	//LIST
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId){
		ModelAndView result;
		Collection<Report> reports;
		reports = this.reportService.findAllByComplaintId(complaintId);
//		System.out.println(reports);
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", 
				"report/list.do");
		return result;
	}
	
	//SHOW
	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId){
		ModelAndView result;
		Report report;
		
		report = this.reportService.findOne(reportId);
		Assert.notNull(report);
		
		result = new ModelAndView("report/show");
		result.addObject("report", report);
		
		result.addObject("requestURI", "report/show.do");
		
		return result;
		
		
	}
}
