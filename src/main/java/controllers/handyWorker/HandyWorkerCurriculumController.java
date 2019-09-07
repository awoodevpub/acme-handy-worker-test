
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("/curriculum/handyworker")
public class HandyWorkerCurriculumController extends AbstractController {

	@Autowired
	private CurriculumService	curriculumService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findCurriculumByHandyWorkerLoggedId();

		result = new ModelAndView("curriculum/show");
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();
		result = this.createEditModelAndView(curriculum);
		result.addObject("personalRecord", curriculum.getPersonalRecord());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, @Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum, personalRecord);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int tutorialId) {
	//		ModelAndView result;
	//		Tutorial tutorial;
	//
	//		tutorial = this.tutorialService.findOne(tutorialId);
	//		Assert.notNull(tutorial);
	//		result = this.createEditModelAndView(tutorial);
	//		result.addObject("tutorialId", tutorialId);
	//
	//		return result;
	//	}
	//

	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Tutorial tutorial, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			this.tutorialService.delete(tutorial);
	//			result = new ModelAndView("redirect:/mytutorial/handyworker/list.do");
	//
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(tutorial);
	//		}
	//
	//		return result;
	//	}

	// Ancillary methods ---------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");

		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}
}
