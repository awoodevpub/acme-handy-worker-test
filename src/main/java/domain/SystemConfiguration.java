
package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	// Attributes
	private String				nameSystem;
	private String				bannerUrl;
	private String				welcomeMessageEnglish;
	private String				welcomeMessageSpanish;
	private Collection<String>	spamWords;
	private Double				VATPercentage;
	private String				phoneCountryCode;
	private Collection<String>	creditCardNames;
	private Integer				periodFinder;
	private Integer				maxResultsFinder;
	private Collection<String>	positiveWords;
	private Collection<String>	negativeWords;

	private Map<Actor, Double> score;

	// Getters and Setters
	@NotBlank
	public String getNameSystem() {
		return this.nameSystem;
	}
	@ElementCollection
	public Map<Actor, Double> getScore() {
		return score;
	}
	public void setScore(Map<Actor, Double> score) {
		this.score = score;
	}
	public void setNameSystem(final String nameSystem) {
		this.nameSystem = nameSystem;
	}

	@NotBlank
	@URL
	public String getBannerUrl() {
		return this.bannerUrl;
	}
	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	@NotBlank
	public String getWelcomeMessageEnglish() {
		return this.welcomeMessageEnglish;
	}

	public void setWelcomeMessageEnglish(final String welcomeMessageEnglish) {
		this.welcomeMessageEnglish = welcomeMessageEnglish;
	}

	@NotBlank
	public String getWelcomeMessageSpanish() {
		return this.welcomeMessageSpanish;
	}

	public void setWelcomeMessageSpanish(final String welcomeMessageSpanish) {
		this.welcomeMessageSpanish = welcomeMessageSpanish;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}
	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 2, fraction = 2)
	public Double getVATPercentage() {
		return this.VATPercentage;
	}

	public void setVATPercentage(final Double vATPercentage) {
		this.VATPercentage = vATPercentage;
	}

	@NotBlank
	@Pattern(regexp = "^[+]\\d+$")
	public String getPhoneCountryCode() {
		return this.phoneCountryCode;
	}

	public void setPhoneCountryCode(final String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getCreditCardNames() {
		return this.creditCardNames;
	}

	public void setCreditCardNames(final Collection<String> creditCardNames) {
		this.creditCardNames = creditCardNames;
	}

	@NotNull
	@Range(min = 1, max = 24)
	public Integer getPeriodFinder() {
		return this.periodFinder;
	}
	public void setPeriodFinder(final Integer periodFinder) {
		this.periodFinder = periodFinder;
	}

	@NotNull
	@Max(100)
	public Integer getMaxResultsFinder() {
		return this.maxResultsFinder;
	}
	public void setMaxResultsFinder(final Integer maxResultsFinder) {
		this.maxResultsFinder = maxResultsFinder;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}
	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}
	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}
}
