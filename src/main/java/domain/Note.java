
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

import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Attributes
	private Date				mommentWritten;
	private Collection<String>	refereeComments;
	private Collection<String>	customerComments;
	private Collection<String>	handyWorkerComments;


	// Getters and Setters
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMommentWritten() {
		return this.mommentWritten;
	}

	public void setMommentWritten(final Date mommentWritten) {
		this.mommentWritten = mommentWritten;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getRefereeComments() {
		return this.refereeComments;
	}

	public void setRefereeComments(final Collection<String> refereeComments) {
		this.refereeComments = refereeComments;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final Collection<String> customerComments) {
		this.customerComments = customerComments;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getHandyWorkerComments() {
		return this.handyWorkerComments;
	}

	public void setHandyWorkerComments(final Collection<String> handyWorkerComments) {
		this.handyWorkerComments = handyWorkerComments;
	}
}
