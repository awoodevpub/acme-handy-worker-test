
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import utilities.NullOrNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	// Attributes
	private String				title;
	private String				linkAttachment;
	private Collection<String>	comments;


	// Getters and Setters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
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
