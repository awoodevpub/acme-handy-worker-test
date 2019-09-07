
package controllers.handyWorker;

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
import services.CustomerService;
import services.EndorsementService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import domain.Endorsement;

@Controller
@RequestMapping("/endorsement/handyworker")
public class HandyWorkerEndorsementController extends AbstractController {

	@Autowired
	EndorsementService	endorsementService;

	@Autowired
	CustomerService		customerService;

	@Autowired
	ActorService		actorService;


	//Create the endorsement
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Endorsement endorsement;
		Collection<Customer> customers;
		endorsement = this.endorsementService.create();
		customers = this.customerService.findCustomersFromFixUpTaskWorkedByHandyWorker();
		if (!customers.isEmpty()) {
			result = this.createEditModelAndView(endorsement);
			result.addObject("customers", customers);
			return result;
		} else {
			result = new ModelAndView("redirect:list.do");
			return result;
		}

	}

	//List the endorsements for handyWorkers
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listHandyWorker() {
		ModelAndView result;
		Collection<Endorsement> endorsementsCreatedHandyWorker;
		Collection<Endorsement> endorsementsReceivedHandyWorker;
		Collection<Customer> customers;

		customers = this.customerService.findCustomersFromFixUpTaskWorkedByHandyWorker();
		endorsementsCreatedHandyWorker = this.endorsementService.findEndorsementsGivenByActorLoggedId();
		endorsementsReceivedHandyWorker = this.endorsementService.findEndorsementsReceivedByActorLoggedId();
		Assert.notNull(endorsementsCreatedHandyWorker);
		Assert.notNull(endorsementsReceivedHandyWorker);
		result = new ModelAndView("endorsement/list");
		result.addObject("endorsementsCreatedHandyWorker", endorsementsCreatedHandyWorker);
		result.addObject("endorsementsReceivedHandyWorker", endorsementsReceivedHandyWorker);
		result.addObject("customers", customers);
		//result.addObject("requestURI", "endorsement/handyworker/list.do");

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
			final Collection<Customer> customers = this.customerService.findCustomersFromFixUpTaskWorkedByHandyWorker();
			if (!customers.isEmpty()) {
				result = this.createEditModelAndView(endorsement);
				result.addObject("customers", customers);
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
		Collection<Endorsement> endorsementsCreatedHandyWorker;
		Collection<Endorsement> endorsementsReceivedHandyWorker;

		try {

			this.endorsementService.delete(endorsement);
			endorsementsCreatedHandyWorker = this.endorsementService.findEndorsementsGivenByActorLoggedId();
			endorsementsReceivedHandyWorker = this.endorsementService.findEndorsementsReceivedByActorLoggedId();
			Assert.notNull(endorsementsCreatedHandyWorker);
			Assert.notNull(endorsementsReceivedHandyWorker);
			result = new ModelAndView("endorsement/list");
			result.addObject("endorsementsCreatedHandyWorker", endorsementsCreatedHandyWorker);
			result.addObject("endorsementsReceivedHandyWorker", endorsementsReceivedHandyWorker);
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
