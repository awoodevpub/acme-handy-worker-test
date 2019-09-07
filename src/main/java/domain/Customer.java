
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Relationships
	private Collection<FixUpTask>	fixUpTasks;

	@Valid
	@EachNotNull
	@OneToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}
	
	
}
