
package controllers.admin;

import java.util.Collection;

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
import services.RefereeService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Referee;

@Controller
@RequestMapping("/actor/administrator")
public class AdminActorController extends AbstractController {

	@Autowired
	ActorService			actorService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	RefereeService			refereeService;

	@Autowired
	UserAccountService		userAccountService;


	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		Double score;
		actor = this.actorService.findOne(actorId);
		score = this.actorService.getActorScore(actorId);
		result = new ModelAndView("actor/show");
		result.addObject("actor", actor);
		result.addObject("score", score);
		result.addObject("requestURI", "actor/administrator/show.do");

		return result;
	}

	// Display a listing of suspicious actors
	@RequestMapping(value = "/listSuspicious", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actor;

		actor = this.actorService.findSuspiciousActors();
		result = new ModelAndView("actor/suspiciousList");
		result.addObject("actor", actor);
		result.addObject("requestURI", "actor/administrator/suspiciousList.do");

		return result;
	}

	// BAN
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam(required = true) final int actorId) {
		ModelAndView result;

		try {

			final Actor actor = this.actorService.findOne(actorId);

			Assert.notNull(actor);

			Assert.isTrue(actor.getUserAccount().getStatusAccount() == true);

			this.actorService.banActor(actor);

			result = new ModelAndView("redirect:listSuspicious.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/misc/403");

		}

		return result;

	}

	// allow
	@RequestMapping(value = "/allow", method = RequestMethod.GET)
	public ModelAndView allow(@RequestParam(required = true) final int actorId) {
		ModelAndView result;

		try {

			final Actor actor = this.actorService.findOne(actorId);

			Assert.notNull(actor);

			Assert.isTrue(actor.getUserAccount().getStatusAccount() == false);

			this.actorService.unbanActor(actor);

			result = new ModelAndView("redirect:listSuspicious.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/misc/403");

		}

		return result;

	}

	@RequestMapping(value = "/register-administrator", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		Actor actor;

		actor = this.administratorService.create();

		result = this.createEditModelAndView(actor);

		result.addObject("actionURL", "actor/administrator/register-administrator.do");

		return result;
	}

	@RequestMapping(value = "/register-administrator", method = RequestMethod.POST, params = {
		"save", "confirmPassword"
	})
	public ModelAndView registerAdministrator(@ModelAttribute("actor") @Valid final Administrator administrator, final BindingResult binding, @RequestParam("confirmPassword") final String confirmPassword) {
		ModelAndView result;
		Authority auth;

		if (binding.hasErrors())
			result = this.createEditModelAndView(administrator);
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.ADMIN);
				Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(confirmPassword.equals(administrator.getUserAccount().getPassword()), "Passwords do not match");
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndView(administrator, "actor.params.confirm.error");
				else
					result = this.createEditModelAndView(administrator, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/register-referee", method = RequestMethod.GET)
	public ModelAndView registerReferee() {
		ModelAndView result;
		Actor actor;

		actor = this.refereeService.create();

		result = this.createEditModelAndView(actor);

		result.addObject("actionURL", "actor/administrator/register-referee.do");

		return result;
	}

	@RequestMapping(value = "/register-referee", method = RequestMethod.POST, params = {
		"save", "confirmPassword"
	})
	public ModelAndView registerReferee(@ModelAttribute("actor") @Valid final Referee referee, final BindingResult binding, @RequestParam("confirmPassword") final String confirmPassword) {
		ModelAndView result;
		Authority auth;

		if (binding.hasErrors())
			result = this.createEditModelAndView(referee);
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.REFEREE);
				Assert.isTrue(referee.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(confirmPassword.equals(referee.getUserAccount().getPassword()), "Passwords do not match");
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndView(referee, "actor.params.confirm.error");
				else
					result = this.createEditModelAndView(referee, "actor.commit.error");
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

		if (actor instanceof Administrator)
			result.addObject("authority", Authority.ADMIN);
		else if (actor instanceof Referee)
			result.addObject("authority", Authority.REFEREE);

		result.addObject("actor", actor);
		result.addObject("message", messageCode);

		return result;
	}
}
