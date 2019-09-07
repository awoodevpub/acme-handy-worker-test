package controllers.handyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FixUpTaskService;
import services.PhaseService;
import services.SectionService;


import controllers.AbstractController;
import domain.Application;
import domain.FixUpTask;
import domain.Phase;
import domain.Section;

@Controller
@RequestMapping("/workplan/handyworker")
public class HandyWorkerWorkplanController extends AbstractController{
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixuptaskService;
	@Autowired
	private PhaseService	phaseService;
	
	
	// Listing ---------------------------------------------------------------	
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam final int applicationId) {
			ModelAndView result;
			Collection<Phase> phases;
			FixUpTask fix;

			fix = this.applicationService.findOne(applicationId).getFixUpTask();
			System.out.println(fix.getId());
			phases = this.phaseService.findWorkPlanByFixUpTaskId(fix.getId());

			result = new ModelAndView("workplan/list");
			result.addObject("phases", phases);
			result.addObject("fixUpTaskId", fix.getId());
			result.addObject("requestURI", "workplan/handyworker/list.do");

			return result;
		}
	
			// Create ---------------------------------------------------------------
			@RequestMapping(value = "/create", method = RequestMethod.GET)
			public ModelAndView create(@RequestParam final int fixUpTaskId) {
				ModelAndView result;
				Phase phase;

				phase = this.phaseService.create();
				result = this.createEditModelAndView(phase);
				result.addObject("fixUpTaskId", fixUpTaskId);

				return result;
			}

			//edit------------------------------------------------
			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@RequestParam final int phaseId,@RequestParam final int fixUpTaskId) {
				ModelAndView result;
				Phase phase;

				phase = this.phaseService.findOne(phaseId);

				result = this.createEditModelAndView(phase);
				result.addObject("fixUpTaskId", fixUpTaskId);

				return result;
			}

			//save------------------------------------------------
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(@RequestParam final int fixUpTaskId, @Valid final Phase phase, final BindingResult binding) {
				ModelAndView res = null;

				if (binding.hasErrors()){
					res = this.createEditModelAndView(phase);
					res.addObject("fixUpTaskId", fixUpTaskId);
				}else{
						try {
							this.phaseService.save(phase,this.fixuptaskService.findOne(fixUpTaskId));
							int applicationId = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTaskId).getId();
							res = new ModelAndView("redirect:list.do?applicationId=" + applicationId);
						} catch (final Throwable oops) {
							res = this.createEditModelAndView(phase, oops.getMessage());
							res.addObject("fixUpTaskId", fixUpTaskId);
						}
				}
				return res;

			}
			
			//Delete -------------------------------------------------------------
			@RequestMapping(value = "delete", method = RequestMethod.GET)
			public ModelAndView delete(@RequestParam int phaseId,@RequestParam int fixUpTaskId) {
				ModelAndView result;

				Phase phase = this.phaseService.findOne(phaseId);
				try {
					this.phaseService.delete(phase, this.fixuptaskService.findOne(fixUpTaskId));
					result = new ModelAndView("redirect:/application/handyworker/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(phase,"phase.error");
				}

				return result;
			}
		//others---------------------

		protected ModelAndView createEditModelAndView(final Phase phase) {
			ModelAndView res;

			res = this.createEditModelAndView(phase, null);

			return res;
		}
		protected ModelAndView createEditModelAndView(final Phase phase, final String message) {
			final ModelAndView result;

			result = new ModelAndView("workplan/edit");
			result.addObject("phase", phase);
			result.addObject("phaseId", phase.getId());
			result.addObject("message", message);

			return result;
		}
	
}
