package controllers.customer;

import java.util.Calendar;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.OblemicService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Oblemic;

@Controller
@RequestMapping("/oblemic/customer")
public class CustomerOblemicController extends AbstractController{
	
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	

	
	@Autowired
	private OblemicService oblemicService;
	
		
		// LIST
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam final int fixUpTaskId){
			ModelAndView result;
			Collection<Oblemic> oblemics;
			oblemics = this.fixUpTaskService.findOne(fixUpTaskId).getOblemics();
			final String language = LocaleContextHolder.getLocale().getLanguage();
			
			
			long now = Calendar.getInstance().getTimeInMillis();
			System.out.println(now);
			
			
			result = new ModelAndView("oblemic/list");
			result.addObject("language", language);
			result.addObject("now",now);
			result.addObject("oblemics", oblemics);
			result.addObject("requestURI", "oblemic/list.do");
			
			return result;
		}
		
		//SHOW
		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int oblemicId){
			ModelAndView result;
			Oblemic oblemic;
			final String language = LocaleContextHolder.getLocale().getLanguage();
			oblemic = this.oblemicService.findOne(oblemicId);
			int fixUpTaskId = this.oblemicService.findFixUpTaskByOblemicsId(oblemicId).getId();
			
			result = new ModelAndView("oblemic/show");
			result.addObject("language", language);
			result.addObject("oblemic",oblemic);
			result.addObject("taskId", fixUpTaskId);
			return result;
		}
		
		//CREATE
		@RequestMapping(value="/create", method = RequestMethod.GET)
		public ModelAndView create(){
			ModelAndView result;
			Oblemic oblemic;
			
			
			oblemic = this.oblemicService.create();
			result = this.createEditModelAndView(oblemic);
			return result;
		}
		
		//SAVE
		@RequestMapping(value="/edit", method= RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Oblemic oblemic, final BindingResult binding, @RequestParam final int fixUpTaskId){
			ModelAndView result;
			if(binding.hasErrors()){
				
				result = createEditModelAndView(oblemic);
			}else{
				try{
					
					Oblemic oblemicAux = oblemicService.save(oblemic, fixUpTaskId);
					
					if(oblemic.getId() == 0){ // is new
						// assign to some fixUpTask
						FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
						Collection<Oblemic> oblemics= fixUpTask.getOblemics();
						oblemics.add(oblemicAux);
						fixUpTask.setOblemics(oblemics);
						this.fixUpTaskService.saveForPhases(fixUpTask);					
					}
					
					result = new ModelAndView("redirect:list.do?fixUpTaskId="+fixUpTaskId);
				} catch (Throwable oops){
					result = this.createEditModelAndView(oblemic, "oblemic.commit.error");
				}
			}
			
			return result;
		}
		
		// EDIT
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int oblemicId, @RequestParam final int fixUpTaskId) {
			ModelAndView result;
			Oblemic oblemic;

			oblemic = oblemicService.findOne(oblemicId);
			if(oblemic.getIsFinalMode()){
				result = this.createEditModelAndView(oblemic, "Final Mode = True");
			}else{
				result = this.createEditModelAndView(oblemic);
			}
			
			return result;
		}
		
		// DELETE
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(final Oblemic oblemic, final BindingResult binding, @RequestParam final int fixUpTaskId) {
			ModelAndView result;

			try {
				final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
				this.oblemicService.delete(oblemic, fixUpTask);
				result = new ModelAndView("redirect:list.do?fixUpTaskId="+fixUpTaskId);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(oblemic);
			}

			return result;
		}
		// ancillary methods
		
		protected ModelAndView createEditModelAndView(final Oblemic oblemic){
			return this.createEditModelAndView(oblemic, null);
		}
		
		protected ModelAndView createEditModelAndView(final Oblemic oblemic, final String messageCode){
			ModelAndView result;
			
			result = new ModelAndView("oblemic/customer/edit");
			result.addObject("oblemic", oblemic);
			result.addObject("message", messageCode);
			
			return result;
		}
}