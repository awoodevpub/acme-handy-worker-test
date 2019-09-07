
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
import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyworker")
public class HandyWorkerProfessionalRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService			curriculumService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();
		result = this.createEditModelAndView(professionalRecord);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId, final int curriculumId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		result = this.createEditModelAndView(professionalRecord);
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding, @RequestParam final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");
			} catch (final Throwable oops) {
				String errorMessage = "professionalRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(professionalRecord, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");

		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", message);

		return result;
	}

}
