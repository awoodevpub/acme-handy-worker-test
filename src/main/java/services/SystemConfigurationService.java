
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.Actor;
import domain.Endorsement;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	// Supporting services
	@Autowired
	private ActorService					actorService;

	@Autowired
	private EndorsementService				endorsementService;


	// Simple CRUD methods
	public SystemConfiguration create() {
		SystemConfiguration result;

		result = new SystemConfiguration();

		return result;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		Assert.isTrue(systemConfigurationId != 0);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	// Other business methods
	public SystemConfiguration getConfiguration() {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.getConfiguration();
		Assert.notNull(result);

		return result;
	}

	private Pattern patternPositiveWords() {
		final Collection<String> positiveWords = this.findPositiveWords();
		String pattern = "";
		for (final String pw : positiveWords)
			pattern = pattern + (pw + "|");
		pattern = pattern.substring(0, pattern.length() - 1);
		pattern = pattern + "$";
		final Pattern result = Pattern.compile(pattern);
		return result;
	}

	private Pattern patternNegativeWords() {
		final Collection<String> negativeWords = this.findNegativeWords();
		String pattern = "";
		for (final String nw : negativeWords)
			pattern = pattern + (nw + "|");
		pattern = pattern.substring(0, pattern.length() - 1);
		pattern = pattern + "$";
		final Pattern result = Pattern.compile(pattern);
		return result;
	}

	// R50.1
	public Map<Actor, Double> computeScores() {
		final Map<Actor, Double> result = new HashMap<>();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Endorsement> endorsements = this.endorsementService.findAll();

		final Map<Actor, Integer> scoreWordsActors = new HashMap<>();
		Matcher positiveWords, negativeWords;

		for (final Endorsement e : endorsements) {
			Integer countScoreWords = 0;
			if (e.getEndorsedCustomer() != null) {
				for (final String comment : e.getComments()) {
					positiveWords = this.patternPositiveWords().matcher(comment.toLowerCase());
					negativeWords = this.patternNegativeWords().matcher(comment.toLowerCase());
					while (positiveWords.find())
						countScoreWords++;
					while (negativeWords.find())
						countScoreWords--;
				}
				if (scoreWordsActors.containsKey(e.getEndorsedCustomer())) {
					final Integer scoreWordsCustomer = scoreWordsActors.get(e.getEndorsedCustomer());
					scoreWordsActors.put(e.getEndorsedCustomer(), scoreWordsCustomer + countScoreWords);
				} else
					scoreWordsActors.put(e.getEndorsedCustomer(), countScoreWords);
			}
			if (e.getEndorsedHandyWorker() != null) {
				for (final String comment : e.getComments()) {
					positiveWords = this.patternPositiveWords().matcher(comment.toLowerCase());
					negativeWords = this.patternNegativeWords().matcher(comment.toLowerCase());
					while (positiveWords.find())
						countScoreWords++;
					while (negativeWords.find())
						countScoreWords--;
				}
				if (scoreWordsActors.containsKey(e.getEndorsedHandyWorker())) {
					final Integer scoreWordsHandyWorker = scoreWordsActors.get(e.getEndorsedHandyWorker());
					scoreWordsActors.put(e.getEndorsedHandyWorker(), scoreWordsHandyWorker + countScoreWords);
				} else
					scoreWordsActors.put(e.getEndorsedHandyWorker(), countScoreWords);
			}
		}
		final Double maxScoreActors = Collections.max(scoreWordsActors.values()).doubleValue();
		final Double minScoreActors = Collections.min(scoreWordsActors.values()).doubleValue();

		for (final Actor a : scoreWordsActors.keySet()) {
			final Double scoreActor = scoreWordsActors.get(a).doubleValue();
			Double lht = 0.0;
			if ((maxScoreActors - minScoreActors) != 0)
				lht = -1 + (scoreActor - minScoreActors) * (1 - -1) / (maxScoreActors - minScoreActors);
			result.put(a, lht);
		}
		SystemConfiguration sys = getConfiguration();
		sys.setScore(result);
		System.out.println(result);
		systemConfigurationRepository.save(sys);
		System.out.println(sys);
		return result;
	}

	// R50.2
	public Collection<String> findPositiveWords() {
		Collection<String> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.getConfiguration().getPositiveWords();
		Assert.notNull(result);

		return result;
	}

	// R50.2
	public Collection<String> findNegativeWords() {
		Collection<String> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.getConfiguration().getNegativeWords();
		Assert.notNull(result);

		return result;
	}

	// R50.2
	public Collection<String> createPositiveWord(final String positiveWord) {
		Collection<String> result;

		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.getConfiguration();

		result = systemConfiguration.getPositiveWords();
		result.add(positiveWord.toLowerCase());
		systemConfiguration.setPositiveWords(result);

		this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	// R50.2
	public Collection<String> createNegativeWord(final String negativeWord) {
		Collection<String> result;

		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.getConfiguration();

		result = systemConfiguration.getNegativeWords();
		result.add(negativeWord.toLowerCase());
		systemConfiguration.setNegativeWords(result);

		this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	// R50.2
	public Collection<String> deletePositiveWord(final String positiveWord) {
		Collection<String> result;

		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.getConfiguration();

		result = systemConfiguration.getPositiveWords();
		result.remove(positiveWord.toLowerCase());
		systemConfiguration.setPositiveWords(result);

		this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	// R50.2
	public Collection<String> deleteNegativeWord(final String negativeWord) {
		Collection<String> result;

		final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.getConfiguration();

		result = systemConfiguration.getNegativeWords();
		result.remove(negativeWord.toLowerCase());
		systemConfiguration.setNegativeWords(result);

		this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

}
