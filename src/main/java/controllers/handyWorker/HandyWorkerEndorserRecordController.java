
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
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/handyworker")
public class HandyWorkerEndorserRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId, final int curriculumId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		result = this.createEditModelAndView(endorserRecord);
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding, @RequestParam final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");
			} catch (final Throwable oops) {
				String errorMessage = "endorserRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(endorserRecord, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");

		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", message);

		return result;
	}

}
