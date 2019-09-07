
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.TutorialService;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		ModelAndView result;
		HandyWorker handyWorker;
		Collection<Tutorial> tutorials;

		handyWorker = this.handyWorkerService.findHandyWorkerByTutorialId(tutorialId);
		tutorials = this.tutorialService.findTutorialsByHandyWorkerId(handyWorker.getId());
		final Actor actor = handyWorker;
		final Double personalScore = this.actorService.getActorScore(actor.getId());

		result = new ModelAndView("profile/show");
		result.addObject("handyworker", handyWorker);
		result.addObject("tutorials", tutorials);
		result.addObject("tutorialId", tutorialId);
		result.addObject("personalScore", personalScore);
		result.addObject("requestURI", "profile/show.do?tutorialId=" + tutorialId);

		return result;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		Customer customer;
		Collection<FixUpTask> fix;

		customer = this.customerService.findCustomerByFixUpTaskId(fixuptaskId);
		fix = this.fixUpTaskService.findFixUpTasksByCustomerId(customer.getId());
		final Actor actor = customer;
		final Double personalScore = this.actorService.getActorScore(actor.getId());

		result = new ModelAndView("profile/customer");
		result.addObject("personalScore", personalScore);
		result.addObject("customer", customer);
		result.addObject("fixUpTasks", fix);

		return result;
	}

}
