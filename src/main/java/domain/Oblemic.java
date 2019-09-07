package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class Oblemic extends DomainEntity{
	
	// Attributes
	private String keylet;
	private String status;
	private String body;
	private String picture;
	private Boolean isFinalMode;
	
	
	// ^\\w{2}\\d{2}-[0-3]?[0-9][0-3]?[0-9](?:[0-9]{2})?[0-9]{2}$
	// ^\w{2,4}:\d{2}:\b(0?[1-9]|1[0-2])(0?[1-9]|[12][0-9]|3[01])\b$
	// ^\[a-zA-Z0-9]{6}-\\w{6}$
	@NotBlank
	@Pattern(regexp = "^\\w{2,4}:\\d{2}:\\b(0?[1-9]|1[0-2])(0?[1-9]|[12][0-9]|3[01])\\b$")
	@Column(unique = true)
	public String getKeylet() {
		return keylet;
	}
	public void setKeylet(String keylet) {
		this.keylet = keylet;
	}
	
	@NotNull
	@Pattern(regexp = "^GUSTRO|RENTOL|BLOWTIM|TOSK$")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotBlank
	@Length(min = 1, max = 100)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	@NotNull
	public Boolean getIsFinalMode() {
		return this.isFinalMode;
	}

	public void setIsFinalMode(final Boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}
	
}