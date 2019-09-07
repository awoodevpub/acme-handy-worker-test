package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class AdminWarrantyController extends AbstractController {
	@Autowired
	private WarrantyService warrantyService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Warranty> warranties;

		warranties = this.warrantyService.findAll();
		result = new ModelAndView("warranty/list");
		result.addObject("warranty", warranties);
		result.addObject("requestURI", "warranty/administrator/list.do");

		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);

		result = new ModelAndView("warranty/list");
		result.addObject("warranties", warranty);
		result.addObject("requestURI", "warranty/administrator/show.do");

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Warranty warranty;
		warranty = warrantyService.create();
		System.out.println("A");
		result = this.createEditModelAndView(warranty);
		System.out.println("A");
		return result;

	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		warranty = warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);
		result = this.createEditModelAndView(warranty);
		return result;

	}

	// SAVE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Warranty warranty, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(warranty);
		} else {
			try {
				System.out.println("G");
				warrantyService.save(warranty);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(warranty,"administrator.commit.error");
			}
		}

		return result;

	}
	
	// CHANGE MODE
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "change")
		public ModelAndView change(@Valid Warranty warranty, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				result = createEditModelAndView(warranty);
			} else {
				try {
					warrantyService.changeMode(warranty);
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = this.createEditModelAndView(warranty,"administrator.commit.error");
				}
			}

			return result;

		}
	
	// DELETE
		@RequestMapping(value = "/edit", method = RequestMethod.POST,params="delete")
		public ModelAndView delete(Warranty warranty,BindingResult binding) {
			ModelAndView result;
			try{
			this.warrantyService.delete(warranty);
			
			result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(warranty,
						"administrator.commit.error");
			}
			return result;
		}

	protected ModelAndView createEditModelAndView(final Warranty warranty) {
		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("warranty/edit");

		result.addObject("warranty", warranty);
		result.addObject("message", messageCode);

		return result;
	}
}
