
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

import services.TutorialService;
import controllers.AbstractController;
import domain.Tutorial;

@Controller
@RequestMapping("/mytutorial/handyworker")
public class HandyWorkerTutorialController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findTutorialsByHandyWorkerLoggedId();

		result = new ModelAndView("mytutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "mytutorial/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);

		result = new ModelAndView("mytutorial/show");
		result.addObject("tutorial", tutorial);
		result.addObject("authorId", tutorial.getHandyWorker().getId());
		result.addObject("authorName", tutorial.getHandyWorker().getName());
		result.addObject("requestURI", "mytutorial/show.do?tutorialId=" + tutorialId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		result = this.createEditModelAndView(tutorial);
		result.addObject("tutorialId", tutorialId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {

				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:/mytutorial/handyworker/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.create();
		result = this.createEditModelAndView(tutorial);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:/mytutorial/handyworker/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial);
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("mytutorial/edit");

		result.addObject("tutorial", tutorial);
		result.addObject("message", messageCode);

		return result;
	}
}
