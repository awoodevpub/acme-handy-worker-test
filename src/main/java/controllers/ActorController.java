
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import services.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	ActorService			actorService;

	@Autowired
	CustomerService			customerService;

	@Autowired
	HandyWorkerService		handyWorkerService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	RefereeService			refereeService;

	@Autowired
	SponsorService			sponsorService;

	@Autowired
	UserAccountService		userAccountService;


	@RequestMapping(value = "/register-customer", method = RequestMethod.GET)
	public ModelAndView registerCustomer() {
		ModelAndView result;
		Actor actor;

		actor = this.customerService.create();

		result = this.createEditModelAndView(actor);

		result.addObject("actionURL", "actor/register-customer.do");

		return result;
	}

	@RequestMapping(value = "/register-customer", method = RequestMethod.POST, params = {
		"save", "confirmPassword"
	})
	public ModelAndView registerCustomer(@ModelAttribute("actor") @Valid final Customer customer, final BindingResult binding, @RequestParam("confirmPassword") final String confirmPassword) {
		ModelAndView result;
		Authority auth;

		if (binding.hasErrors())
			result = this.createEditModelAndView(customer);
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.CUSTOMER);
				Assert.isTrue(customer.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(confirmPassword.equals(customer.getUserAccount().getPassword()), "Passwords do not match");
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndView(customer, "actor.params.confirm.error");
				else
					result = this.createEditModelAndView(customer, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/register-handy-worker", method = RequestMethod.GET)
	public ModelAndView registerHandyWorker() {
		ModelAndView result;
		Actor actor;

		actor = this.handyWorkerService.create();

		result = this.createEditModelAndView(actor);

		result.addObject("actionURL", "actor/register-handy-worker.do");

		return result;
	}

	@RequestMapping(value = "/register-handy-worker", method = RequestMethod.POST, params = {
		"save", "confirmPassword"
	})
	public ModelAndView registerHandyWorker(@ModelAttribute("actor") @Valid final HandyWorker actor, final BindingResult binding, @RequestParam("confirmPassword") final String confirmPassword) {
		ModelAndView result;
		Authority auth;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.HANDYWORKER);
				Assert.isTrue(actor.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(confirmPassword.equals(actor.getUserAccount().getPassword()), "Passwords do not match");
				this.handyWorkerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndView(actor, "actor.params.confirm.error");
				else
					result = this.createEditModelAndView(actor, "actor.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/register-sponsor", method = RequestMethod.GET)
	public ModelAndView registerSponsor() {
		ModelAndView result;
		Actor actor;

		actor = this.sponsorService.create();

		result = this.createEditModelAndView(actor);

		result.addObject("actionURL", "actor/register-sponsor.do");

		return result;
	}

	@RequestMapping(value = "/register-sponsor", method = RequestMethod.POST, params = {
		"save", "confirmPassword"
	})
	public ModelAndView registerSponsor(@ModelAttribute("actor") @Valid final Sponsor sponsor, final BindingResult binding, @RequestParam("confirmPassword") final String confirmPassword) {
		ModelAndView result;
		Authority auth;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.SPONSOR);
				Assert.isTrue(sponsor.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(confirmPassword.equals(sponsor.getUserAccount().getPassword()), "Passwords do not match");
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndView(sponsor, "actor.params.confirm.error");
				else
					result = this.createEditModelAndView(sponsor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/**/edit", method = RequestMethod.GET)
	public ModelAndView editActor() {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findActorLogged();

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editActor(@ModelAttribute("actor") @Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actor", customer);
			result.addObject("message");
		} else
			try {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				result.addObject("actor", customer);
				result.addObject("message", "actor.params.error");
			}

		return result;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editActor(@ModelAttribute("actor") @Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actor", handyWorker);
			result.addObject("message");
		} else
			try {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				result.addObject("actor", handyWorker);
				result.addObject("message", "actor.params.error");
			}

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editActor(@ModelAttribute("actor") @Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actor", administrator);
			result.addObject("message");
		} else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				result.addObject("actor", administrator);
				result.addObject("message", "actor.params.error");
			}

		return result;
	}

	@RequestMapping(value = "/referee/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editActor(@ModelAttribute("actor") @Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actor", referee);
			result.addObject("message");
		} else
			try {
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				result.addObject("actor", referee);
				result.addObject("message", "actor.params.error");
			}

		return result;
	}

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editActor(@ModelAttribute("actor") @Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actor", sponsor);
			result.addObject("message");
		} else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				result.addObject("actor", sponsor);
				result.addObject("message", "actor.params.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/register");

		if (actor instanceof Customer)
			result.addObject("authority", Authority.CUSTOMER);
		else if (actor instanceof HandyWorker)
			result.addObject("authority", Authority.HANDYWORKER);
		else if (actor instanceof Sponsor)
			result.addObject("authority", Authority.SPONSOR);

		result.addObject("actor", actor);
		result.addObject("message", messageCode);

		return result;
	}
}
