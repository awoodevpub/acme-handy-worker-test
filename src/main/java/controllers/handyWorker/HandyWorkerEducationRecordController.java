
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
import services.EducationRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/handyworker")
public class HandyWorkerEducationRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId, final int curriculumId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		result = this.createEditModelAndView(educationRecord);
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding, @RequestParam final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");
			} catch (final Throwable oops) {
				String errorMessage = "educationRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(educationRecord, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");

		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);

		return result;
	}

}
