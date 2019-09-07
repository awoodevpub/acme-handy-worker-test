
package controllers.sponsor;

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
import services.CreditCardService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Actor;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorSponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private CreditCardService	creditCardService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findSponsorshipsBySponsorLogged();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		result = this.createEditModelAndView(sponsorship);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Actor actor;
		Tutorial tutorial;
		Sponsor sponsor;

		actor = this.actorService.findActorLogged();
		this.actorService.checkUserLoginSponsor(actor);
		sponsor = (Sponsor) actor;
		tutorial = this.tutorialService.findOne(tutorialId);

		sponsorship = this.sponsorshipService.create();
		sponsorship.setSponsor(sponsor);
		sponsorship.setTutorial(tutorial);
		result = this.createEditModelAndView(sponsorship);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship);
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		final Tutorial tutorial = sponsorship.getTutorial();
		final Sponsor sponsor = sponsorship.getSponsor();

		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorial", tutorial);
		result.addObject("sponsor", sponsor);
		result.addObject("message", messageCode);

		return result;
	}
}
