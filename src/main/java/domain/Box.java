
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Attributes
	private String	name;
	private Boolean	isSystemBox;


	// Getters and Setters
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Boolean getIsSystemBox() {
		return this.isSystemBox;
	}

	public void setIsSystemBox(final Boolean isSystemBox) {
		this.isSystemBox = isSystemBox;
	}


	// Relationships
	private Collection<Message>	messages;


	@Valid
	@EachNotNull
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

}
