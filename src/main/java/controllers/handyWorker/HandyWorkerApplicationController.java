
package controllers.handyWorker;

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
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Application;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyworker")
public class HandyWorkerApplicationController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixuptaskService;

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private CustomerService	customerService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;

	// Listing ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		applications = this.applicationService.findApplicationsByHandyWorkerLoggedId();
		Date fecha = new Date(System.currentTimeMillis()-1);
		
		result = new ModelAndView("application/list");
		result.addObject("fecha", fecha);
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyworker/list.do");

		return result;
	}

	// Show -----------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		final Actor actorLogged = this.actorService.findActorLogged();
		final HandyWorker handyOwner = this.handyWorkerService.findHandyWorkerByApplicationId(applicationId);
		if(!actorLogged.equals(handyOwner)){
			result = new ModelAndView("redirect:/misc/403.jsp");
		}else{	
			result = new ModelAndView("application/show");
			result.addObject("application", application);
		}
		return result;
	}

	// Create ---------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int fixUpTaskId) {
			ModelAndView result;
			Application application;
	
				application = this.applicationService.create();
				application.setFixUpTask(this.fixuptaskService.findOne(fixUpTaskId));
				application.setStatus("PENDING");
				result = this.createEditModelAndView(application);

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
					res = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					res = this.createEditModelAndView(application, "fixUpTask.commit.error");

				}
		}
			return res;

		}
	//others---------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView res;

		res = this.createEditModelAndView(application, null);

		return res;
	}
	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}
}
