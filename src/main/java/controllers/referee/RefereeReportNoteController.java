
package controllers.referee;

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
@RequestMapping("/note/referee")
public class RefereeReportNoteController extends AbstractController {

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ReportService	reportService;


	//CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note);
		return result;
	}

	//EDIT -> crear comentarios en una nota existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;
		note = this.noteService.findOne(noteId);

		result = this.createEditModelAndView(note);
		return result;

	}

	//SAVE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, @RequestParam final int reportId, final BindingResult binding) {
		ModelAndView result;
		final Report report = this.reportService.findOne(reportId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else if (report.getIsFinalMode())
			try {
				this.noteService.save(note, report);
				result = new ModelAndView("redirect:list.do?reportId=" + reportId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "referee.commit.error");
			}
		else
			result = this.createEditModelAndView(note, "referee.commit.error");
		return result;
	}

	// LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId) {
		ModelAndView result;
		Collection<Note> notes;

		notes = this.reportService.findOne(reportId).getNotes();

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "referee/note/list.do");

		return result;
	}

	// additional methods 
	protected ModelAndView createEditModelAndView(final Note note) {
		return this.createEditModelAndView(note, null);
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", messageCode);

		return result;
	}
}
