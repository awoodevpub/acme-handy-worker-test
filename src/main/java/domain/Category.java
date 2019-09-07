
package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes
	private Map<String, String>	name;


	// Getters and Setters

	@ElementCollection
	@Column(unique = true)
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}


	// Relationships	
	private Category				parentCategory;
	private Collection<Category>	childsCategory;
	private Collection<FixUpTask>	fixUpTasks;


	@Valid
	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "parentCategory")
	public Collection<Category> getChildsCategory() {
		return this.childsCategory;
	}
	public void setChildsCategory(final Collection<Category> childsCategory) {
		this.childsCategory = childsCategory;
	}

	@Valid
	@EachNotNull
	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
