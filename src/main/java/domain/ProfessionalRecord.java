
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import utilities.NullOrNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	// Attributes
	private String				companyName;
	private Date				startDateWork;
	private Date				endDateWork;
	private String				rolePlayed;
	private String				linkAttachment;
	private Collection<String>	comments;


	// Getters and Setters
	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDateWork() {
		return this.startDateWork;
	}

	public void setStartDateWork(final Date startDateWork) {
		this.startDateWork = startDateWork;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDateWork() {
		return this.endDateWork;
	}

	public void setEndDateWork(final Date endDateWork) {
		this.endDateWork = endDateWork;
	}

	@NotBlank
	public String getRolePlayed() {
		return this.rolePlayed;
	}

	public void setRolePlayed(final String rolePlayed) {
		this.rolePlayed = rolePlayed;
	}

	@NullOrNotBlank
	@URL
	public String getLinkAttachment() {
		return this.linkAttachment;
	}

	public void setLinkAttachment(final String linkAttachment) {
		this.linkAttachment = linkAttachment;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}
}
