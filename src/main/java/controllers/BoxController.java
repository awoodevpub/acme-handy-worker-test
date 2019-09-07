
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	@Autowired
	BoxService		boxService;

	@Autowired
	ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Box> boxes;

		boxes = this.boxService.findAllBoxByActorLogged();

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBox(@RequestParam final int boxId) {
		ModelAndView res;
		final Box box = this.boxService.findOne(boxId);

		final Actor actorLogged = this.actorService.findActorLogged();
		final Collection<Box> boxesActorLogged = actorLogged.getBoxes();

		if (!boxesActorLogged.contains(box))
			res = new ModelAndView("redirect:/misc/403.jsp");
		else
			res = this.createEditModelAndView(box);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editBox(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(box, "box.params.error");
		else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("box/edit");
				result.addObject("box", box);
				result.addObject("message", "box.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBox(Box box, final BindingResult binding, @RequestParam("boxId") final int boxId) {
		ModelAndView result;
		box = this.boxService.findOne(boxId);

		try {
			this.boxService.delete(box);
			Collection<Box> boxes;

			boxes = this.boxService.findAllBoxByActorLogged();

			result = new ModelAndView("box/list");
			result.addObject("boxes", boxes);
			result = new ModelAndView("redirect:/box/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("box/list");
			result.addObject("box", box);
			result.addObject("message", "box.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createBox() {
		ModelAndView result;
		Box box;

		box = this.boxService.create();

		result = this.createEditModelAndView(box);

		result.addObject("actionURL", "box/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createBox(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(box, "box.params.error");
		else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("box/edit");
				result.addObject("box", box);
				result.addObject("message", "box.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int boxId) {
		ModelAndView result;
		Collection<Message> messages;

		final Box box = this.boxService.findOne(boxId);
		messages = box.getMessages();

		final Actor actorLogged = this.actorService.findActorLogged();
		final Collection<Box> boxesActorLogged = actorLogged.getBoxes();

		if (!boxesActorLogged.contains(box))
			result = new ModelAndView("redirect:/misc/403.jsp");
		else
			result = new ModelAndView("box/show");

		result.addObject("messages", messages);
		result.addObject("box", box);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView res;
		res = this.createEditModelAndView(box, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("box/edit");
		res.addObject("box", box);
		res.addObject("errorMessage", messageCode);

		return res;
	}
}
