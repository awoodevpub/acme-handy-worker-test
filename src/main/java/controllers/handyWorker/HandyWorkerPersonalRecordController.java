
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyworker")
public class HandyWorkerPersonalRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId, final int curriculumId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.findOne(personalRecordId);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		result = this.createEditModelAndView(personalRecord);
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding, @RequestParam final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");
			} catch (final Throwable oops) {
				String errorMessage = "personalRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(personalRecord, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");

		result.addObject("personalRecord", personalRecord);
		result.addObject("message", message);

		return result;
	}

}
