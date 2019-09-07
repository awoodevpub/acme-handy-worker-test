package controllers.admin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class AdminCategoryController extends AbstractController {
	@Autowired
	private CategoryService categoryService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		String language = LocaleContextHolder.getLocale().getLanguage();
		Collection<Category> category;

		category = this.categoryService.findAll();
		result = new ModelAndView("category/list");
		System.out.println(category);
		System.out.println(language);
		result.addObject("category", category);
		result.addObject("language", language);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);

		result = new ModelAndView("category/list");
		result.addObject("category", category);
		result.addObject("requestURI", "category/administrator/show.do");

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) Integer parentId) {
		ModelAndView result;
		Category category;
		category = categoryService.create();
		Category parent = categoryService.findOne(parentId);
		category.setParentCategory(parent);
		result = this.createEditModelAndView(category);
		return result;

	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false,value="parentId") Integer parentId,
			@RequestParam(required = false,value= "categoryId") Integer categoryId) {
		ModelAndView result;
		Category category;
		category = categoryService.findOne(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);
		result.addObject("parentId", parentId);
		result.addObject("categoryId", categoryId);
		return result;

	}

	// SAVE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam(required = false,value="parentId") Integer parentId,
			@RequestParam(required = false,value= "categoryId") Integer categoryId,
			@NotNull String nombreEn, @NotNull String nombreEs) {
		ModelAndView result;
		Category category = new Category();
		if(nombreEn == null || nombreEn.isEmpty() || nombreEs==null || nombreEs.isEmpty()){
			result =this.createEditModelAndView(category,
					"administrator.blank.error");
		}else{
			try {
				if (categoryId == null ) {

					category = categoryService.create();

					Category parent = categoryService.findOne(parentId);
					category.setParentCategory(parent);
				} else {
					category = categoryService.findOne(categoryId);
				}
				Map<String, String> nameMap = new HashMap<String, String>();
				nameMap.put(nombreEn, nombreEs);

				category.setName(nameMap);

				categoryService.save(category);

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {

				result = this.createEditModelAndView(category,
						"administrator.commit.error");
			}
		}
		return result;

	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBox(Category category, final BindingResult binding, @RequestParam("categoryId") final int categoryId) {
		ModelAndView result;
		category = this.categoryService.findOne(categoryId);
		String language = LocaleContextHolder.getLocale().getLanguage();
		try {
			this.categoryService.delete(category);
			Collection<Category> categorys;

			categorys = this.categoryService.findAll();

			result = new ModelAndView("category/administrator/list");
			result.addObject("category", categorys);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("category/administrator/list");
			result.addObject("category", category);
			result.addObject("language", language);
			result.addObject("message", "administrator.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("category/edit");

		result.addObject("category", category);
		result.addObject("message", messageCode);

		return result;
	}
}
