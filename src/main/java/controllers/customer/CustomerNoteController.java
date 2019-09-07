package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.ReportService;
import controllers.AbstractController;
import domain.Note;
import domain.Report;


@Controller
@RequestMapping("/note/customer")
public class CustomerNoteController extends AbstractController{

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private ReportService reportService;
	
	
	//CREATE
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId){
		ModelAndView result;
		Note note;
		note = this.noteService.create();
		result = this.createEditModelAndView(note);
		return result;
	}

	// LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId){
		ModelAndView result;
		Collection<Note> notes;
		Report report;
		report = this.reportService.findOne(reportId);
		
		notes = report.getNotes();
		
		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/list.do");
		
		return result;
	}
	
	//EDIT -> crear comentarios en una nota existente
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId){
		ModelAndView result;
		Note note;
		note = noteService.findOne(noteId);
		
		result = this.createEditModelAndView(note);
		return result;
		
	}
	//SAVE 
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Note note, @RequestParam final int reportId, BindingResult binding){
		ModelAndView result;
		Report report = reportService.findOne(reportId);
	
		if(binding.hasErrors()){
			result = createEditModelAndView(note);
		}else{
			if(report.getIsFinalMode()){
				try{
					noteService.save(note,report);
					result = new ModelAndView("redirect:list.do?reportId="+reportId);
				}catch (Throwable oops){
					result = this.createEditModelAndView(note,
							"customer.commit.error");
				}
			}else{
				result = this.createEditModelAndView(note, "customer.commit.error");
			}
		}
		return result;
	}
	
	
	
	// additional methods
	protected ModelAndView createEditModelAndView(final Note note){
		return this.createEditModelAndView(note,null);
	}
	protected ModelAndView createEditModelAndView(final Note note, final String messageCode){
		ModelAndView result;
		
		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message",messageCode);
		return result;
	}
	
}
