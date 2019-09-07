
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Attributes
	private Date	momentOfRegistry;
	private String	status;
	private Double	offeredPrice;
	private String	comment;
	private String	stateReason;


	// Getters and Setters
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMomentOfRegistry() {
		return this.momentOfRegistry;
	}

	public void setMomentOfRegistry(final Date momentOfRegistry) {
		this.momentOfRegistry = momentOfRegistry;
	}

	@NotBlank
	@Pattern(regexp = "^PENDING|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public Double getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotBlank
	public String getStateReason() {
		return this.stateReason;
	}

	public void setStateReason(final String stateReason) {
		this.stateReason = stateReason;
	}


	// Relationships
	private FixUpTask	fixUpTask;
	private CreditCard	creditCard;
	private HandyWorker	handyWorker;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Valid
	@ManyToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
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
