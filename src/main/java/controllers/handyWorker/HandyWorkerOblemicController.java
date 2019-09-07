package controllers.handyWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Actor;
import domain.FixUpTask;
import domain.Oblemic;

@Controller
@RequestMapping("/oblemic/handyworker")
public class HandyWorkerOblemicController {

	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private ActorService actorService;
		
		// LIST
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(){
			ModelAndView result;
			Collection<Oblemic> oblemics = new ArrayList<Oblemic>();
			Collection<Oblemic> oblemicsAux;
			final Actor actorLogged = this.actorService.findActorLogged();
			Assert.notNull(actorLogged);
			Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
			this.actorService.checkUserLoginHandyWorker(actorLogged);

			Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findFixUpTasksByHandyWorkerId(actorLogged.getId());
			for (FixUpTask fixUpTask : fixUpTasks) {
				oblemicsAux = fixUpTask.getOblemics();
				for (Oblemic oblemic : oblemicsAux) {
					if(oblemic.getIsFinalMode()){
						oblemics.add(oblemic);
					}
				}
			}
			
			final String language = LocaleContextHolder.getLocale().getLanguage();
			
//			Collection<Application> applications = fixUpTask.getApplications();
//			for (Application application : applications) {
//				final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(application.getId());
//			}
//			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
//		
			
			long now = Calendar.getInstance().getTimeInMillis();
			
			
			result = new ModelAndView("oblemic/list");
			result.addObject("language", language);
			result.addObject("now",now);
			result.addObject("oblemics", oblemics);
			result.addObject("requestURI", "oblemic/list.do");
			
			return result;
		}
}
