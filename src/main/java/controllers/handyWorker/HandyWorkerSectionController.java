
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

import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section/handyworker")
public class HandyWorkerSectionController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;

	@Autowired
	private SectionService	sectionService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId, @RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		final Integer maxNumber = this.tutorialService.findOne(tutorialId).getSections().size();
		result = this.createEditModelAndView(section);
		result.addObject("tutorialId", tutorialId);
		result.addObject("maxNumber", maxNumber);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding, @RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;
		final Integer maxNumber = this.tutorialService.findOne(tutorialId).getSections().size();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(section);
			result.addObject("maxNumber", maxNumber);
		} else
			try {
				tutorial = this.tutorialService.findOne(tutorialId);

				this.sectionService.save(section, tutorial);
				result = new ModelAndView("redirect:/mytutorial/handyworker/show.do?tutorialId=" + tutorial.getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section, "section.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.create();
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Integer number = tutorial.getSections().size() + 1;
		section.setNumber(number);

		result = this.createEditModelAndView(section);
		result.addObject("tutorialId", tutorialId);
		result.addObject("number", number);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Section section, final BindingResult binding, @RequestParam final int tutorialId) {
		ModelAndView result;

		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			this.sectionService.delete(section, tutorial);
			result = new ModelAndView("redirect:/mytutorial/handyworker/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(section);
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section, final String message) {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findTutorialsByHandyWorkerLoggedId();

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("tutorials", tutorials);
		result.addObject("message", message);

		return result;
	}
}
