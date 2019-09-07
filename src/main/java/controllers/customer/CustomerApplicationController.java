
package controllers.customer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Actor;
import domain.Application;
import domain.Customer;

@Controller
@RequestMapping("/application/customer")
public class CustomerApplicationController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private CustomerService	customerService;
	
	//List applications for a fix-up task
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Collection<Application> applications;

		final Actor actorLogged = this.actorService.findActorLogged();
		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTaskId);
		if(!actorLogged.equals(customerOwner)){
			result = new ModelAndView("redirect:/misc/403.jsp");
		}else{
			applications = this.applicationService.findApplicationsByFixUpTaskId(fixUpTaskId);
	
			Date fecha = new Date(System.currentTimeMillis()-1);
			System.out.println(fecha);
			
			result = new ModelAndView("application/list");
			result.addObject("fecha", fecha);
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/customer/list.do?fixUpTaskId=" + fixUpTaskId);
		}

		return result;
	}


	//Edit application
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId, final String applicationStatus) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		final Actor actorLogged = this.actorService.findActorLogged();
		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(application.getFixUpTask().getId());
		if(!actorLogged.equals(customerOwner)){
			result = new ModelAndView("redirect:/misc/403.jsp");
		}else{
			result = this.createEditModelAndView(application);
			result.addObject("application", application);
			result.addObject("applicationStatus", applicationStatus);
		}

		return result;
	}

	//save------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView res = null;

		if (binding.hasErrors()){
			res = this.createEditModelAndView(application);
		}else{
			try {
				this.applicationService.save(application);
				res = new ModelAndView("redirect:/fixuptask/customer/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage()+ "12");
				res = this.createEditModelAndView(application, "fixUpTask.commit.error");

			}
		}
		return res;

	}

	// Ancillary methods ---------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");

		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

}
