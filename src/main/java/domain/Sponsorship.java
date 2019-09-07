
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Attributes
	private String	bannerUrl;
	private String	targetPageLink;


	// Getters and Setters
	@NotBlank
	@URL
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	@NotBlank
	@URL
	public String getTargetPageLink() {
		return this.targetPageLink;
	}

	public void setTargetPageLink(final String targetPageLink) {
		this.targetPageLink = targetPageLink;
	}


	// Relationships
	private Sponsor		sponsor;
	private Tutorial	tutorial;
	private CreditCard	creditCard;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}
	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Tutorial getTutorial() {
		return this.tutorial;
	}
	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
