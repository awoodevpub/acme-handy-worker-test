
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import services.TutorialService;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findAll();

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;
		Sponsorship sponsorship;

		tutorial = this.tutorialService.findOne(tutorialId);
		if (!tutorial.getSponsorships().isEmpty())
			sponsorship = this.sponsorshipService.randomSponsorShip(tutorial);
		else
			sponsorship = null;

		result = new ModelAndView("tutorial/show");
		result.addObject("id", tutorial.getId());
		result.addObject("title", tutorial.getTittle());
		result.addObject("summary", tutorial.getSummary());
		result.addObject("lastUpdate", tutorial.getLastUpdated());
		result.addObject("pictures", tutorial.getPictures());
		result.addObject("sections", tutorial.getSections());
		result.addObject("authorName", tutorial.getHandyWorker().getName());
		result.addObject("sponsorship", sponsorship);
		result.addObject("requestURI", "tutorial/show.do?tutorialId=" + tutorialId);

		return result;
	}

}
