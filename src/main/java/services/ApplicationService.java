
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;

@Service
@Transactional
public class ApplicationService {

	// Managed repository
	@Autowired
	private ApplicationRepository		applicationRepository;

	@Autowired
	private CreditCardService			creditCardService;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R11.3
	public Application create() {
		Application result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = new Application();

		final FixUpTask fixUpTask = new FixUpTask();
		final Date date = new Date(System.currentTimeMillis() - 1);

		result.setHandyWorker((HandyWorker) actorLogged);
		result.setFixUpTask(fixUpTask);
		result.setMomentOfRegistry(date);
		result.setStatus("PENDING");

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int applicationId) {
		Assert.isTrue(applicationId != 0);

		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;

		final Date date = new Date(System.currentTimeMillis() - 1);
		System.out.println(date);
		if (application.getId() == 0) {
			// R11.3
			final Actor actorLogged = this.actorService.findActorLogged();
			Assert.notNull(actorLogged);
			Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
			this.actorService.checkUserLoginHandyWorker(actorLogged);

			Assert.isTrue(application.getStatus().equals("PENDING"));
			application.setMomentOfRegistry(date);
			result = this.applicationRepository.save(application);
		} else {
			// R10.2
			final Actor actorLogged = this.actorService.findActorLogged();
			Assert.notNull(actorLogged);
			Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
			this.actorService.checkUserLoginCustomer(actorLogged);
			final Customer customerOwner = this.customerService.findCustomerByApplicationId(application.getId());
			Assert.isTrue(actorLogged.equals(customerOwner));
			System.out.println(application.getFixUpTask().getEndDate());
			Assert.isTrue(application.getFixUpTask().getEndDate().after(date)); // Customer can't change its status if their time period's elapsed
			if (application.getStatus().equals("ACCEPTED")) {
				//Save CreditCard
				final CreditCard c = this.creditCardService.save(application.getCreditCard());
				Assert.notNull(c);
				application.setCreditCard(c);
			}
			result = this.applicationRepository.save(application);
			Assert.isTrue(application.getMomentOfRegistry().equals(result.getMomentOfRegistry()));
			Assert.isTrue(application.getOfferedPrice().equals(result.getOfferedPrice()));
			Assert.isTrue(result.getStatus().equals("ACCEPTED") || result.getStatus().equals("REJECTED"));
			if (result.getStatus().equals("ACCEPTED")) {
				Assert.notNull(result.getCreditCard());
				//El resto de applications deben rechazarse
				final Collection<Application> applicationsCustomer = this.applicationRepository.findApplicationsByCustomerId(customerOwner.getId());
				applicationsCustomer.remove(application);
				for (final Application a : applicationsCustomer) {
					a.setStatus("REJECTED");
					this.applicationRepository.save(a);
				}
			}

			// R19
			Message message = this.messageService.create();
			
			final Locale locale = LocaleContextHolder.getLocale();
			if (locale.getLanguage().equals("es")) {
				message.setSubject("Applicación cambiada de estado");
				message.setBody("Una de sus aplicaciones ha cambiado su estado.");
			} else {
				message.setSubject("Application changed of status");
				message.setBody("One of your applications have changed its status.");
			}
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(application.getId());
			final Collection<Actor> recipients = new HashSet<>();
			Collections.addAll(recipients, customerOwner, handyWorkerOwner);
			message.setRecipients(recipients);

			// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
			final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
			for (final String sw : spamWords) {
				final Boolean flagSpam = (result.getComment().contains(sw)) ? true : false;
				if (flagSpam) {
					actorLogged.setIsSuspicious(flagSpam);
					this.actorService.saveForSuspiciusActor(actorLogged);
					break;
				}
			}
			message = this.messageService.saveSystem(message);
		}

		return result;
	}
	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));

		this.applicationRepository.delete(application);
	}

	// Other business methods
	public Application findApplicationAcceptedByFixUpTaskId(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		Application result;

		result = this.applicationRepository.findApplicationAcceptedByFixUpTaskId(fixUpTaskId);
		return result;
	}

	// R10.2
	public Collection<Application> findApplicationsByFixUpTaskId(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		Collection<Application> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTaskId);
		Assert.isTrue(actorLogged.equals(customerOwner));

		result = this.applicationRepository.findApplicationsByFixUpTaskId(fixUpTaskId);
		return result;
	}

	// R11.3
	public Collection<Application> findApplicationsByHandyWorkerLoggedId() {
		Collection<Application> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.applicationRepository.findApplicationsByHandyWorkerId(actorLogged.getId());
		return result;
	}

	// R11.3
	public Application findApplicationByHandyWorkerLoggedId(final int applicationId) {
		Application result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByApplicationId(applicationId);
		Assert.isTrue(actorLogged.equals(handyWorkerOwner));

		result = this.applicationRepository.findOne(applicationId);
		return result;
	}

	// R12.5
	public String getFixUpTaskApplicationsStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getFixUpTaskApplicationsStatistics();

		return result;
	}

	// R12.5
	public String getOfferedPricesApplicationsStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getOfferedPricesApplicationsStatistics();

		return result;
	}

	// R12.5
	public String getRatioPendingApplications() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getRatioPendingApplications();

		return result;
	}

	// R12.5
	public String getRatioAcceptedApplications() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getRatioAcceptedApplications();

		return result;
	}

	// R12.5
	public String getRatioRejectedApplications() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getRatioRejectedApplications();

		return result;
	}

	// R12.5
	public String getRatioPendingApplicationsWithElapsedPeriod() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.applicationRepository.getRatioRejectedApplications();

		return result;
	}

}
