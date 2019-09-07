
package controllers.handyWorker;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.FinderService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/finder/handyworker")
public class HandyWorkerFinderController extends AbstractController {

	//The services that this controller works with
	@Autowired
	FinderService		finderService;

	@Autowired
	FixUpTaskService	fixUpTaskService;

	@Autowired
	WarrantyService		warrantyService;

	@Autowired
	CategoryService		categoryService;

	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	ActorService		actorService;


	//Methods of the controller

	/*
	 * Este método devuelve a la vista de show la lista de fixUpTasks del finder, y comprueba el estado de la caché antes de devolverla.
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;
		Boolean debeaActualizar;

		final Finder finder = this.finderService.getFinderFromHandyWorkerLogged();
		debeaActualizar = this.finderService.cleanCacheFinder(finder); //Comprobamos si aun se mantiene en caché
		if (debeaActualizar) {
			//En caso de haber superado el tiempo de caché,se realiza búqueda de nuevo con los mismos parámetros antes de devolverlo a la vista
			final String keyword = finder.getKeyWord();
			final Double minPrice = finder.getMinPrice();
			final Double maxPrice = finder.getMaxPrice();
			final Date minDate = finder.getMinDate();
			final Date maxDate = finder.getMaxDate();
			final Category category = finder.getCategory();
			final Warranty warranty = finder.getWarranty();
			this.finderService.changeFiltersFinder(keyword, minPrice, maxPrice, minDate, maxDate, category, warranty);
		}

		fixUpTasks = finder.getFixUpTasks();

		result = new ModelAndView("finder/show"); //Esto es ruta de carpeta 
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("finder", finder);

		return result;
	}

	//Método que devuelve un objeto tipo "finder" a la vista de "finder/search" tras
	//clickear en editar en "results/show"
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {

		ModelAndView result;
		Finder finder;
		Collection<Warranty> warranties;
		Collection<Category> categories;
		Finder finderLogged;
		boolean advise = false;
		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);
		finderLogged = this.finderService.getFinderFromHandyWorkerLogged();
		if (finderLogged.getId() == finderId) {
			result = new ModelAndView("finder/search");
			result.addObject("finder", finder);
			result.addObject("warranties", warranties);
			result.addObject("categories", categories);

		} else {
			advise = true;
			final Collection<FixUpTask> fixUpTasks = finderLogged.getFixUpTasks();
			result = new ModelAndView("finder/show");//Redirige a su propia página de resultados,(no a la de otro)
			result.addObject("advise", advise);
			result.addObject("finder", finderLogged);
			result.addObject("fixUpTasks", fixUpTasks);
		}

		return result;
	}

	//Método que guarda los cambios en los parámetros de búsqueda del finder al darle al botón
	//"save" en la vista "finder/edit.jsp"
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView save(@Valid Finder finder, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {

				finder = this.finderService.save(finder);//Hay que salvarlo para guardar los parámetros del form,antes de usar esos atributos como valores de búsqueda,sino,toma los antiguos en los get siguientes
				final String keyword = finder.getKeyWord();
				final Double minPrice = finder.getMinPrice();
				final Double maxPrice = finder.getMaxPrice();
				final Date minDate = finder.getMinDate();
				final Date maxDate = finder.getMaxDate();
				final Category category = finder.getCategory();
				final Warranty warranty = finder.getWarranty();
				this.finderService.changeFiltersFinder(keyword, minPrice, maxPrice, minDate, maxDate, category, warranty);//Si no se hiciese el primer save,este mñétodo realizaría la busqueda con los parámetros antiguos
				result = new ModelAndView("redirect:/finder/handyworker/show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	//createModelAndView methods
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		Collection<Warranty> warranties;
		Collection<Category> categories;
		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		return result;
	}

}
