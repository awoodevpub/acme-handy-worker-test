
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyworker")
public class HandyWorkerMiscellaneousRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService			curriculumService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		result.addObject("curriculum", curriculum);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId, final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		result = this.createEditModelAndView(miscellaneousRecord);
		result.addObject("curriculum", curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, @RequestParam final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/handyworker/show.do");
			} catch (final Throwable oops) {
				String errorMessage = "miscellaneousRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(miscellaneousRecord, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String message) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");

		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", message);

		return result;
	}

}
