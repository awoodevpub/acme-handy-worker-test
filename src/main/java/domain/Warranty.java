
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	// Attributes
	private String				title;
	private String				terms;
	private Collection<String>	laws;
	private Boolean				isFinalMode;


	// Getters and Setters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final Collection<String> laws) {
		this.laws = laws;
	}

	@NotNull
	public Boolean getIsFinalMode() {
		return this.isFinalMode;
	}

	public void setIsFinalMode(final Boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}
}
