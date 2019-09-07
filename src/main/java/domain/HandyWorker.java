
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	// Attributes
	private String	make;


	// Getters and Setters
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}


	// Relationships
	private Finder					finder;
	private Collection<Application>	applications;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "handyWorker")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

}
