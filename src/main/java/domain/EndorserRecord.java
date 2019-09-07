
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	// Attributes
	private String				fullName;
	private String				email;
	private String				phoneNumber;
	private String				linkLinkedIn;
	private Collection<String>	comments;


	// Getters and Setters
	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Pattern(regexp = ("^[a-zA-Z0-9 ]*[<]?[a-zA-Z0-9]+[@][a-zA-Z0-9.]+[>]?$"))
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@URL
	public String getLinkLinkedIn() {
		return this.linkLinkedIn;
	}

	public void setLinkLinkedIn(final String linkLinkedIn) {
		this.linkLinkedIn = linkLinkedIn;
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
