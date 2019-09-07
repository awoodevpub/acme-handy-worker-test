package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
@Indexed
// D03 A+
public class FixUpTask extends DomainEntity {

	// Attributes
	private String	ticker;
	private Date	momentPublished;
	private String	description;
	private String	address;
	private Double	maximumPrice;
	private Date	startDate;
	private Date	endDate;


	// Getters and Setters
	@NotBlank
	@Pattern(regexp = "^\\d\\d[0-1]\\d[0-3]\\d[-][A-Z0-9]{6}$")
	@Column(unique = true)
	@Field
	// D03 A+
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getMomentPublished() {
		return this.momentPublished;
	}

	public void setMomentPublished(final Date momentPublished) {
		this.momentPublished = momentPublished;
	}

	@NotBlank
	@Field
	// D03 A+
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@Field
	// D03 A+
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public Double getMaximumPrice() {
		return this.maximumPrice;
	}

	public void setMaximumPrice(final Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	// Relationships
	private Collection<Phase>		phases;
	private Collection<Oblemic>  	oblemics;
	private Collection<Application>	applications;
	private Collection<Complaint>	complaints;
	private Warranty				warranty;


	@Valid
	@EachNotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Oblemic> getOblemics(){
		return this.oblemics;
	}
	
	public void setOblemics(Collection<Oblemic> oblemics){
		this.oblemics = oblemics;
	}
	
	@Valid
	@EachNotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Phase> getPhases() {
		return this.phases;
	}

	public void setPhases(final Collection<Phase> phases) {
		this.phases = phases;
	}

	@EachNotNull
	@Valid
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@EachNotNull
	@Valid
	@OneToMany
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

}