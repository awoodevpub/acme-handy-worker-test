
package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixuptask/handyworker")
public class HandyWorkerFixUpTaskController extends AbstractController {

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	
	@Autowired
	private CategoryService	categoryService;


	// Listing ---------------------------------------------------------------	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		String language = LocaleContextHolder.getLocale().getLanguage();

		fixUpTasks = this.fixUpTaskService.findAll();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("language", language);
		result.addObject("allcategorys", this.categoryService.findAll());
		result.addObject("requestURI", "fixuptask/handyworker/list.do");

		return result;
	}

	//others---------------------

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask) {
		ModelAndView res;

		res = this.createEditModelAndView(fixUpTask, null);

		return res;
	}
	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String message) {
		final ModelAndView result;

		result = new ModelAndView("fixuptask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("message", message);

		return result;
	}
}
