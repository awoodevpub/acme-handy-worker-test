
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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	// Attributes
	private Date				momentWritten;
	private String				description;
	private Collection<String>	linkAttachments;
	private Boolean				isFinalMode;


	// Getters and Setters
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentWritten() {
		return this.momentWritten;
	}

	public void setMomentWritten(final Date momentWritten) {
		this.momentWritten = momentWritten;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	@EachURL
	public Collection<String> getLinkAttachments() {
		return this.linkAttachments;
	}

	public void setLinkAttachments(final Collection<String> linkAttachments) {
		this.linkAttachments = linkAttachments;
	}

	@NotNull
	public Boolean getIsFinalMode() {
		return this.isFinalMode;
	}

	public void setIsFinalMode(final Boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}


	// Relationships
	private Referee				referee;
	private Complaint			complaint;
	private Collection<Note>	notes;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@Valid
	@EachNotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Note> getNotes() {
		return this.notes;
	}
	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}
}
