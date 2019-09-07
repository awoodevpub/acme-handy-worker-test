package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/referee")
public class RefereeComplaintController extends AbstractController{
	
	@Autowired
	private ComplaintService complaintService;
	
	@RequestMapping(value="/list-assigned", method = RequestMethod.GET)
	public ModelAndView listAssigned(){
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = this.complaintService.findComplaintsByRefereeLoggedId();
		
		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/list.do");
		
		return result;
	}
	
	@RequestMapping(value="/list-not-assigned", method = RequestMethod.GET)
	public ModelAndView listNotAssigned(){
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = this.complaintService.findComplantsNoReferee();
		
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
		
		complaint = this.complaintService.findOne(complaintId);
		
		result = new ModelAndView("complaint/show");
		result.addObject("complaint",complaint);
		
		
		result.addObject("requestURI", "complaint/show.do?complaintId=" + complaintId);
		
		return result;
	}
	
	
}

