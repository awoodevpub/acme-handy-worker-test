package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.FixUpTaskService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.FixUpTask;
import domain.Report;

@Controller
@RequestMapping("/complaint/customer")
public class CustomerComplaintController extends AbstractController{

	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private ReportService reportService;
	
	// LIST
	@RequestMapping(value = "/list-all", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = this.complaintService.findComplaintsByCustomerLoggedId();
		
		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/list.do");
		
		return result;
	}
	
	// LIST POR FIXUPTASK
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId){
		ModelAndView result;
		Collection<Complaint> complaints;
		FixUpTask fixUpTask = this.fixUpTaskService.findFixUpTaskByCustomerLoggedId(fixUpTaskId);
		complaints = fixUpTask.getComplaints();
		
		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/list.do");
		
		return result;
	}
	
	//SHOW
	@RequestMapping(value="/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId){
		ModelAndView result;
		Complaint complaint;
		Collection<Report> reports = this.reportService.findAllByComplaintId(complaintId);
		complaint = this.complaintService.findOne(complaintId);
		
		result = new ModelAndView("complaint/show");
		result.addObject("complaint",complaint);
		result.addObject("reports", reports);
		return result;
	}
	
	//CREATE
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId){
		ModelAndView result;
		Complaint complaint;
		
		
		complaint = this.complaintService.create();
		result = this.createEditModelAndView(complaint);
//		System.out.println(result);
		return result;
	}
	
	//SAVE
	@RequestMapping(value="/edit", method= RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, final BindingResult binding, @RequestParam final int fixUpTaskId){
		ModelAndView result;
		if(binding.hasErrors()){
			
			result = createEditModelAndView(complaint);
//			System.out.println(binding);
//			System.out.println("hay errores");
		}else{
			try{
				Complaint complaintAux = complaintService.save(complaint);
				
				// assign to some fixUpTask
				FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
				Collection<Complaint> complaints= fixUpTask.getComplaints();
				complaints.add(complaintAux);
				fixUpTask.setComplaints(complaints);
				this.fixUpTaskService.saveForPhases(fixUpTask);

				
				result = new ModelAndView("redirect:list-all.do");
//				System.out.println("va bien");
			} catch (Throwable oops){
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
//				System.out.println("hay errores");
			}
		}
		
		return result;
	}
	
	// EDIT no se usa, incumple requisito.
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId, @RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.findOne(complaintId);

		result = this.createEditModelAndView(complaint);

		return result;
	}
	
	
	// additional methods
	
	protected ModelAndView createEditModelAndView(final Complaint complaint){
		return this.createEditModelAndView(complaint, null);
	}
	
	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode){
		ModelAndView result;
		
		result = new ModelAndView("complaint/customer/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", messageCode);
		
		return result;
	}
}
