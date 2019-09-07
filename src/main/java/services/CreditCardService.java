
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Managed repository
	@Autowired
	private CreditCardRepository		creditCardRepository;

	// Supporting services
	@Autowired
	private SystemConfigurationService	service;


	// Simple CRUD methods
	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		CreditCard result;
		final Date currentDate = new Date(System.currentTimeMillis() - 1);
		Assert.isTrue((2000 + creditCard.getExpirationYear()) > currentDate.getYear() || ((2000 + creditCard.getExpirationYear()) == currentDate.getYear() && creditCard.getExpirationMonth() > currentDate.getMonth()));
		Assert.isTrue(this.service.getConfiguration().getCreditCardNames().contains(creditCard.getBrandName()));

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	// Other business methods

}
