package controllers.referee;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class RefereeReportController extends AbstractController{
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ComplaintService complaintService;
	
	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int complaintId){
		ModelAndView result;
		Report report;

		Complaint complaint = this.complaintService.findOne(complaintId);
		
		
		report = this.reportService.create();
		
		report.setComplaint(complaint);
		result = this.createEditModelAndView(report);
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
	
	
	// LIST
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId){
		ModelAndView result;
		Collection<Report> reports;
		
		reports = this.reportService.findAllByComplaintId(complaintId);
		
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/list.do");
		
		return result;
	}
	
	// EDIT
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportId){
		ModelAndView result;
		Report report;
		// this?
		
		report = this.reportService.findOne(reportId);
		Assert.notNull(report);
		if(report.getIsFinalMode()){
			result = this.createEditModelAndView(report, "referee.commit.error");
		}else{
			result = this.createEditModelAndView(report);
		}
		return result;
	}
	
	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST,params="delete")
	public ModelAndView delete(Report report,BindingResult binding) {
		ModelAndView result;
		if(report.getIsFinalMode()){
			result = this.createEditModelAndView(report, "referee.commit.error");
		}else{
			try{
			this.reportService.delete(report);
			
			result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(report,
						"referee.commit.error");
			}
		}
		return result;
}
	
	//SAVE
	@RequestMapping(value="/edit", method= RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Report report, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(report);
		}else{
			try{
				report = reportService.save(report, report.getComplaint());
				
				result = new ModelAndView("redirect:show.do?reportId=" + report.getId());
			} catch (Throwable oops){
				result = this.createEditModelAndView(report,
						"referee.commit.error");
			}
		}
		
		return result;
	}
	
	// additional methods
	
	protected ModelAndView createEditModelAndView(final Report report){
		return this.createEditModelAndView(report, null);
	}
	
	protected ModelAndView createEditModelAndView(final Report report, final String messageCode){
		ModelAndView result;
		
		result = new ModelAndView("report/referee/edit");
		result.addObject("report", report);
		result.addObject("message", messageCode);
		
		return result;
	}
	
	
}