
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	// Attributes
	private Date				momentWritten;
	private Collection<String>	comments;


	// Getters and Setters
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMomentWritten() {
		return this.momentWritten;
	}

	public void setMomentWritten(final Date momentWritten) {
		this.momentWritten = momentWritten;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}


	// Relationships
	private HandyWorker	endorserHandyWorker;
	private Customer	endorsedCustomer;
	private Customer	endorserCustomer;
	private HandyWorker	endorsedHandyWorker;


	@Valid
	@ManyToOne(optional = true)
	public HandyWorker getEndorserHandyWorker() {
		return this.endorserHandyWorker;
	}

	public void setEndorserHandyWorker(final HandyWorker endorserHandyWorker) {
		this.endorserHandyWorker = endorserHandyWorker;
	}

	@Valid
	@ManyToOne(optional = true)
	public Customer getEndorsedCustomer() {
		return this.endorsedCustomer;
	}

	public void setEndorsedCustomer(final Customer endorsedCustomer) {
		this.endorsedCustomer = endorsedCustomer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Customer getEndorserCustomer() {
		return this.endorserCustomer;
	}

	public void setEndorserCustomer(final Customer endorserCustomer) {
		this.endorserCustomer = endorserCustomer;
	}

	@Valid
	@ManyToOne(optional = true)
	public HandyWorker getEndorsedHandyWorker() {
		return this.endorsedHandyWorker;
	}

	public void setEndorsedHandyWorker(final HandyWorker endorsedHandyWorker) {
		this.endorsedHandyWorker = endorsedHandyWorker;
	}

}
