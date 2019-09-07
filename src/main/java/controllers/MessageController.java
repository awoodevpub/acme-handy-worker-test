
package controllers;

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

import services.ActorService;
import services.BoxService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	MessageService	messageService;

	@Autowired
	BoxService		boxService;

	@Autowired
	ActorService	actorService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showMessage(@RequestParam final int boxId, @RequestParam final int messageId) {

		ModelAndView result;
		Message m;
		Box box;

		m = this.messageService.findOne(messageId);
		box = this.boxService.findOne(boxId);

		final Actor actorLogged = this.actorService.findActorLogged();
		final Collection<Box> boxesActorLogged = actorLogged.getBoxes();

		if (!boxesActorLogged.contains(box))
			result = new ModelAndView("redirect:/misc/403.jsp");
		else
			result = new ModelAndView("message/show");

		result.addObject("m", m);
		result.addObject("box", box);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam final int boxId, @RequestParam final int messageId) {
		ModelAndView result;
		Message m;

		m = this.messageService.findOne(messageId);

		try {
			this.messageService.delete(m);
			result = new ModelAndView("redirect:/box/show.do?boxId=" + boxId);
		} catch (final Throwable oops) {
			result = new ModelAndView("message/show");
			result.addObject("m", m);
			result.addObject("message", "m.commit.error");
			result = new ModelAndView("redirect:/message/show.do?boxId=" + boxId + "&messageId=" + messageId);
		}
		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView moveMessage(@RequestParam final int boxId, @RequestParam final int messageId) {
		ModelAndView result;
		Message m;
		Box box;

		m = this.messageService.findOne(messageId);
		box = this.boxService.findOne(boxId);
		final Collection<Box> boxes = this.boxService.findAllBoxByActorLogged();
		result = new ModelAndView("message/move");
		result.addObject("m", m);
		result.addObject("box", box);
		result.addObject("boxes", boxes);

		return result;
	}

	@RequestMapping(value = "/saveMove", method = RequestMethod.GET)
	public ModelAndView saveMove(@RequestParam final int boxId, @RequestParam final int messageId) {
		ModelAndView result;
		Message m;
		Box box;

		m = this.messageService.findOne(messageId);
		box = this.boxService.findOne(boxId);

		try {
			this.messageService.saveToMove(m, box);
			result = new ModelAndView("redirect:/box/show.do?boxId=" + boxId);
		} catch (final Throwable oops) {
			result = new ModelAndView("message/move");
			result.addObject("m", m);
			result.addObject("message", "m.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createMessage() {
		ModelAndView result;
		Message m;

		m = this.messageService.create();

		result = this.createEditModelAndView(m);

		result.addObject("actionURL", "message/create.do");

		return result;
	}

	@RequestMapping(value = "/createBroadcast", method = RequestMethod.GET)
	public ModelAndView createBroadcast() {
		ModelAndView result;
		Message m;

		m = this.messageService.create();

		result = this.createEditModelAndView(m);

		result.addObject("actionURL", "message/createBroadcast.do");
		result.addObject("broadcast", "broadcast");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createMessage(@ModelAttribute("m") @Valid final domain.Message m, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(m);
		else
			try {
				this.messageService.save(m);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "m.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/createBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView createBroadcast(@ModelAttribute("m") @Valid final domain.Message m, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(m);
		else
			try {
				this.messageService.broadcastMessage(m);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "m.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m) {
		ModelAndView result;

		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m, final String messageCode) {
		ModelAndView result;
		final Collection<Actor> actors = this.actorService.findAll();
		result = new ModelAndView("message/create");
		result.addObject("m", m);
		result.addObject("errorMessage", messageCode);
		result.addObject("actors", actors);

		return result;
	}

}
