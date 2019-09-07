
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/configuration/administrator")
public class AdminConfigurationController {

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// LAUNCH SCORE
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public ModelAndView score() {
		ModelAndView result;
		try {

			this.systemConfigurationService.computeScores();
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			
			result = new ModelAndView("welcome/index");
		}
		return result;
	}

	// MANAGE POSITIVE/NEGATIVE WORDS
	// LIST POSITIVE

	@RequestMapping(value = "/listPositive", method = RequestMethod.GET)
	public ModelAndView listPositive() {
		ModelAndView result;
		Collection<String> positiveWords;

		positiveWords = this.systemConfigurationService.findPositiveWords();
		result = new ModelAndView("system/list");
		result.addObject("words", positiveWords);

		result.addObject("requestURI", "configuration/administrator/list.do");

		return result;
	}

	// LIST NEGATIVE

	@RequestMapping(value = "/listNegative", method = RequestMethod.GET)
	public ModelAndView listNegative() {
		ModelAndView result;
		Collection<String> negativeWords;

		negativeWords = this.systemConfigurationService.findNegativeWords();
		result = new ModelAndView("system/list");
		result.addObject("words", negativeWords);

		result.addObject("requestURI", "configuration/administrator/list.do");

		return result;
	}

	// SAVE Positive
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePositive")
	public ModelAndView savePositive(final String positiveWord) {
		ModelAndView result;

		if (positiveWord == null || positiveWord.isEmpty()) {
			result = new ModelAndView("redirect:listPositive.do");
			result.addObject("message", "error");
		} else
			try {
				this.systemConfigurationService.createPositiveWord(positiveWord);

				result = new ModelAndView("redirect:listPositive.do");
			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:listPositive.do");

			}
		final String language = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("language", language);
		return result;

	}

	// SAVE Negative
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveNegative")
	public ModelAndView saveNegative(final String negativeWord) {
		ModelAndView result;

		if (negativeWord == null || negativeWord.isEmpty()) {
			result = new ModelAndView("redirect:listNegative.do");
			result.addObject("message", "error");
		} else
			try {
				this.systemConfigurationService.createNegativeWord(negativeWord);
				result = new ModelAndView("redirect:listNegative.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:listNegative.do");
			}
		final String language = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("language", language);
		return result;

	}

	// DELETE Positive
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deletePositive")
	public ModelAndView deletePositive(final String positiveWord) {
		ModelAndView result;

		if (positiveWord == null || positiveWord.isEmpty()) {
			result = new ModelAndView("redirect:listPositive.do");
			result.addObject("message", "error");
		} else
			try {
				this.systemConfigurationService.deletePositiveWord(positiveWord);

				result = new ModelAndView("redirect:listPositive.do");
			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:listPositive.do");

			}
		final String language = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("language", language);

		return result;
	}

	// DELETE Negative
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteNegative")
	public ModelAndView deleteNegative(final String negativeWord) {
		ModelAndView result;

		if (negativeWord == null || negativeWord.isEmpty()) {
			result = new ModelAndView("redirect:listNegative.do");
			result.addObject("message", "error");
		} else
			try {
				this.systemConfigurationService.deleteNegativeWord(negativeWord);
				result = new ModelAndView("redirect:listNegative.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:listNegative.do");
			}
		final String language = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("language", language);
		return result;

	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration system, final Boolean b) {
		ModelAndView result;

		result = this.createEditModelAndView(system, null, b);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration system, final String messageCode, final Boolean b) {
		ModelAndView result;
		if (b == false)
			result = new ModelAndView("redirect:listNegative.do");
		else
			result = new ModelAndView("redirect:listPositive.do");
		result.addObject("system", system);
		result.addObject("message", messageCode);

		return result;
	}
}
