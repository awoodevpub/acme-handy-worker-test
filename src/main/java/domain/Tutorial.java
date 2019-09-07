
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	// Attributes
	private String				tittle;
	private Date				lastUpdated;
	private String				summary;
	private Collection<String>	pictures;


	// Getters and Setters
	@NotBlank
	public String getTittle() {
		return this.tittle;
	}

	public void setTittle(final String tittle) {
		this.tittle = tittle;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@ElementCollection
	@EachNotBlank
	@EachURL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}


	// Relationships	
	private Collection<Sponsorship>	sponsorships;
	private Collection<Section>		sections;
	private HandyWorker				handyWorker;


	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "tutorial")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}
	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@Valid
	@EachNotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSections() {
		return this.sections;
	}
	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}
}
