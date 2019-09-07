
package controllers.customer;

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

import services.ActorService;
import services.EndorsementService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorsement/customer")
public class CustomerEndorsementController extends AbstractController {

	@Autowired
	EndorsementService	endorsementService;

	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	ActorService		actorService;


	//Create the endorsement
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Collection<HandyWorker> handyWorkers;
		Endorsement endorsement;
		endorsement = this.endorsementService.create();
		handyWorkers = this.handyWorkerService.getHandyWorkersOfFixUpTasksCustomerLogged();
		if (!handyWorkers.isEmpty()) {
			result = this.createEditModelAndView(endorsement);
			result.addObject("handyWorkers", handyWorkers);
			return result;
		} else {
			result = new ModelAndView("redirect:list.do");
			return result;
		}
	}

	//List the endorsements for customers
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listCustomer() {
		ModelAndView result;
		Collection<Endorsement> endorsementsCreatedCustomer;
		Collection<Endorsement> endorsementsReceivedCustomer;
		Collection<HandyWorker> handyWorkers;

		handyWorkers = this.handyWorkerService.getHandyWorkersOfFixUpTasksCustomerLogged();
		endorsementsCreatedCustomer = this.endorsementService.findEndorsementsGivenByActorLoggedId();
		endorsementsReceivedCustomer = this.endorsementService.findEndorsementsReceivedByActorLoggedId();
		Assert.notNull(endorsementsCreatedCustomer);
		Assert.notNull(endorsementsReceivedCustomer);
		result = new ModelAndView("endorsement/list");
		result.addObject("endorsementsCreatedCustomer", endorsementsCreatedCustomer);
		result.addObject("endorsementsReceivedCustomer", endorsementsReceivedCustomer);
		result.addObject("handyWorkers", handyWorkers);
		return result;
	}

	//Show the endorsement selected
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int endorsementId) {

		ModelAndView result;
		final Actor logged = this.actorService.findActorLogged();
		final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
		result = new ModelAndView("endorsement/show");
		result.addObject("endorsement", endorsement);
		result.addObject("logged", logged);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {

		ModelAndView result;
		try {

			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			result = new ModelAndView("endorsement/edit");
			result.addObject("endorsement", endorsement);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	//Method for save in the endorsement form
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors()) {
			final Collection<HandyWorker> handyWorkers = this.handyWorkerService.getHandyWorkersOfFixUpTasksCustomerLogged();
			if (!handyWorkers.isEmpty()) {
				result = this.createEditModelAndView(endorsement);
				result.addObject("handyWorkers", handyWorkers);
			} else
				result = new ModelAndView("redirect:list.do");
		} else
			try {
				this.endorsementService.save(endorsement);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}

		return result;

	}

	//Delete operation
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(Endorsement endorsement, final BindingResult binding, @RequestParam final int endorsementId) {
		ModelAndView result;
		endorsement = this.endorsementService.findOne(endorsementId);
		Collection<Endorsement> endorsementsCreatedCustomer;
		Collection<Endorsement> endorsementsReceivedCustomer;

		try {

			this.endorsementService.delete(endorsement);
			endorsementsCreatedCustomer = this.endorsementService.findEndorsementsGivenByActorLoggedId();
			endorsementsReceivedCustomer = this.endorsementService.findEndorsementsReceivedByActorLoggedId();
			Assert.notNull(endorsementsCreatedCustomer);
			Assert.notNull(endorsementsReceivedCustomer);
			result = new ModelAndView("endorsement/list");
			result.addObject("endorsementsCreatedCustomer", endorsementsCreatedCustomer);
			result.addObject("endorsementsReceivedCustomer", endorsementsReceivedCustomer);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
		}

		return result;

	}
	//createModelAndView methods
	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("message", messageCode);
		return result;
	}

}
