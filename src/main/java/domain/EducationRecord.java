
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
public class EducationRecord extends DomainEntity {

	// Attributes
	private String				diplomaTitle;
	private Date				startDateStudy;
	private Date				endDateStudy;
	private String				institution;
	private String				linkAttachment;
	private Collection<String>	comments;


	// Getters and Setters
	@NotBlank
	public String getDiplomaTitle() {
		return this.diplomaTitle;
	}

	public void setDiplomaTitle(final String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDateStudy() {
		return this.startDateStudy;
	}

	public void setStartDateStudy(final Date startDateStudy) {
		this.startDateStudy = startDateStudy;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDateStudy() {
		return this.endDateStudy;
	}

	public void setEndDateStudy(final Date endDateStudy) {
		this.endDateStudy = endDateStudy;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
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
