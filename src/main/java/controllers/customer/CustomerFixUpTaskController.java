
package controllers.customer;


import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixuptask/customer")
public class CustomerFixUpTaskController extends AbstractController {

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	// Listing ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		String language = LocaleContextHolder.getLocale().getLanguage();

		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findFixUpTasksByCustomerLogged();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("language", language);
		result.addObject("allcategorys", this.categoryService.findAll());
		result.addObject("requestURI", "fixuptask/customer/list.do");

		return result;
	}

	// Creating -------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		final String language = LocaleContextHolder.getLocale().getLanguage();
		final FixUpTask fix = this.fixUpTaskService.create();

		result = new ModelAndView("fixuptask/edit");
		result.addObject("fixUpTask", fix);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("language", language);
		result.addObject("warrantys", this.warrantyService.findAll());
		result.addObject("requestURI", "fixuptask/customer/create.do");

		return result;
	}

	// Edit-------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;

		final Actor actorLogged = this.actorService.findActorLogged();
		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixuptaskId);
		if(!actorLogged.equals(customerOwner)){
			result = new ModelAndView("redirect:/misc/403.jsp");
		}else{
			final String language = LocaleContextHolder.getLocale().getLanguage();

			fixUpTask = this.fixUpTaskService.findOne(fixuptaskId);

			result = this.createEditModelAndView(fixUpTask);
			result.addObject("fixUpTask", fixUpTask);
			result.addObject("language", language);
			result.addObject("warrantys", this.warrantyService.findAll());
			result.addObject("categories", this.categoryService.findAll());
		}

		return result;
	}

	//save----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam List<Integer> categoryID, @Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(fixUpTask);
		else
			try {
				System.out.println("a");
				final FixUpTask fixUpTask2 = this.fixUpTaskService.save(fixUpTask);
				System.out.println("b");
				
				if(fixUpTask.getId() != 0){
					this.categoryService.clearCategoryToFixuptask(fixUpTask2.getId()); //Limpio las categorias
					System.out.println("P");
				}
				//Añado las nuevas categorias
				System.out.println("c");
				for(Integer a : categoryID){
					this.categoryService.addCategoryToFixuptask(fixUpTask2.getId(), a);
				}
				System.out.println("d");
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String errorMessage = "fixUpTask.commit.error";
				
				if(oops.getMessage().contains("fixUpTask.commit")){
					errorMessage = oops.getMessage();
				}
				res = this.createEditModelAndView(fixUpTask, errorMessage);
			}
		return res;

	}

	//Delete -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam List<Integer> categoryID, @Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		try {
			this.fixUpTaskService.delete(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
		}

		return result;
	}

	// Show -----------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fix;

		fix = this.fixUpTaskService.findOne(fixUpTaskId);

		result = new ModelAndView("fixuptask/show");
		result.addObject("fixUpTask", fix);

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

		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("fixuptask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("language", language);
		result.addObject("warrantys", this.warrantyService.findAll());
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("message", message);

		return result;
	}

}
