
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Actor;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository
	@Autowired
	private FixUpTaskRepository			fixUpTaskRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private FinderService				finderService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	// R10.1
	public FixUpTask create() {
		FixUpTask result;

		result = new FixUpTask();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Collection<Phase> phases = new HashSet<>();
		final Collection<Application> applications = new HashSet<>();
		final Collection<Complaint> complaints = new HashSet<>();
		final Warranty warranty = new Warranty();
		result.setPhases(phases);
		result.setApplications(applications);
		result.setComplaints(complaints);
		result.setWarranty(warranty);
		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final Date date = new Date(System.currentTimeMillis() - 1);
		result.setMomentPublished(date);
		// R21
		final String ticker = dateFormat.format(date).toString() + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		result.setTicker(ticker);

		return result;
	}

	// R10.1
	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// R10.1
	public FixUpTask findOne(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	public FixUpTask save2(final FixUpTask fixUpTask) {
		final FixUpTask result = this.fixUpTaskRepository.save(fixUpTask);
		return result;
	}

	// R10.1
	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		FixUpTask result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		if (fixUpTask.getId() != 0) {
			final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTask.getId());
			Assert.isTrue(actorLogged.equals(customerOwner));
			Assert.isTrue(fixUpTask.getWarranty().getIsFinalMode(), "fixUpTask.commit.warrantyError"); // R12.2: Only warranties that are saved in final mode can be referenced by fix-up tasks.
			final Date date = new Date(System.currentTimeMillis() - 1);
			fixUpTask.setMomentPublished(date);

			final Application applicationAccepted = this.applicationService.findApplicationAcceptedByFixUpTaskId(fixUpTask.getId());
			Assert.isNull(applicationAccepted, "fixUpTask.commit.applicationError"); // No se puede editar una fixUpTask que tenga una application aceptada
		}
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		result = this.fixUpTaskRepository.save(fixUpTask);

		if (fixUpTask.getId() == 0) {
			final Customer customerlogged = (Customer) this.actorService.findActorLogged();
			final Collection<FixUpTask> fix = customerlogged.getFixUpTasks();
			fix.add(result);
			customerlogged.setFixUpTasks(fix);
			this.actorService.save(customerlogged);
		}

		// R38.2: An actor is considered suspicious if he or she publishes some data that includes spam words
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getAddress().contains(sw) || result.getDescription().contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}
		return result;
	}

	// R10.1
	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTask.getId());
		Assert.isTrue(actorLogged.equals(customerOwner));

		System.out.println(fixUpTask.getApplications().isEmpty());
		Assert.isTrue(fixUpTask.getApplications().isEmpty()); //Si un HandyWorker hizo una application sobre el fixUpTask, entonces no se puede borrar

		final Collection<Category> categories = this.categoryService.findAll();
		for (final Category c : categories) {
			final Collection<FixUpTask> fixUpTasksCategories = c.getFixUpTasks();
			fixUpTasksCategories.remove(fixUpTask);
			c.setFixUpTasks(fixUpTasksCategories);
			this.categoryService.saveForFixUpTask(c);
		}
		final Collection<Finder> finders = this.finderService.findAll();
		for (final Finder f : finders) {
			final Collection<FixUpTask> fixUpTasksFinders = f.getFixUpTasks();
			fixUpTasksFinders.remove(fixUpTask);
			f.setFixUpTasks(fixUpTasksFinders);
			this.finderService.save(f);
		}
		final Collection<FixUpTask> fixUpTasksCustomerLogged = customerOwner.getFixUpTasks();
		fixUpTasksCustomerLogged.remove(fixUpTask);
		customerOwner.setFixUpTasks(fixUpTasksCustomerLogged);
		this.customerService.save(customerOwner);

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	// Other business methods
	// R10.1
	public Collection<FixUpTask> findFixUpTasksByCustomerLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		Collection<FixUpTask> result;

		final Customer customerLogged = (Customer) actorLogged;

		result = customerLogged.getFixUpTasks();
		Assert.notNull(result);

		return result;
	}

	// R10.1
	public FixUpTask findFixUpTaskByCustomerLoggedId(final int fixUpTaskId) {
		Assert.isTrue(fixUpTaskId != 0);

		FixUpTask result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);

		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTaskId);
		Assert.isTrue(actorLogged.equals(customerOwner));

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	// R11.1 (personal data from customer will be showed in views)
	public Collection<FixUpTask> findFixUpTasksByCustomerId(final int customerId) {
		Assert.isTrue(customerId != 0);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFixUpTasksByCustomerId(customerId);
		return result;
	}

	// R11.2
	public Collection<FixUpTask> findFilterKeyWordFixUpTask(final String keyWord) {
		Assert.notNull(keyWord);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterKeyWordFixUpTasks(keyWord.toLowerCase());
		return result;
	}

	// R11.2
	public Collection<FixUpTask> findFilterCategoryNameFixUpTasks(final String categoryName) {
		Assert.notNull(categoryName);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterCategoryNameFixUpTasks(categoryName.toLowerCase());
		return result;
	}

	// R11.2
	public Collection<FixUpTask> findFilterPriceFixUpTasks(final Double minPrice, final Double maxPrice) {
		Assert.notNull(minPrice);
		Assert.notNull(maxPrice);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterPriceFixUpTasks(minPrice, maxPrice);
		return result;
	}

	// R11.2
	public Collection<FixUpTask> findFilterDateFixUpTasks(final Date startDate, final Date endDate) {
		Assert.notNull(startDate);
		Assert.notNull(endDate);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterDateFixUpTasks(startDate, endDate);
		return result;
	}

	// R11.2
	public Collection<FixUpTask> findFilterWarrantyIdFixUpTasks(final int warrantyId) {
		Assert.notNull(warrantyId);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterWarrantyIdFixUpTasks(warrantyId);
		return result;
	}

	public Collection<FixUpTask> findFilterCategoryIdFixUpTasks(final int categoryId) {
		Assert.notNull(categoryId);

		Collection<FixUpTask> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.fixUpTaskRepository.findFilterCategoryIdFixUpTasks(categoryId);
		return result;
	}

	public FixUpTask saveForPhases(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		FixUpTask result;

		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		result = this.fixUpTaskRepository.save(fixUpTask);

		return result;
	}

	// R12.5
	public String getCustomerFixUpTasksStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.fixUpTaskRepository.getCustomerFixUpTasksStatistics();

		return result;
	}

	// R12.5
	public String getMaximumPricesFixUpTasksStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.fixUpTaskRepository.getMaximumPricesFixUpTasksStatistics();

		return result;
	}

	//R37.2
	public Collection<FixUpTask> findFixUpTasksFromFinderHandyWorkedLogged(final String keyWord, final Double minPrice, final Double maxPrice, final Date minDate, final Date maxDate, final int categoryId, final int warrantyId) {
		Collection<FixUpTask> result = new HashSet<>();

		final Collection<FixUpTask> byKeyWord = this.findFilterKeyWordFixUpTask(keyWord);
		final Collection<FixUpTask> byPriceRange = this.findFilterPriceFixUpTasks(minPrice, maxPrice);
		final Collection<FixUpTask> byDateRange = this.findFilterDateFixUpTasks(minDate, maxDate);
		final Collection<FixUpTask> byCategory = this.findFilterCategoryIdFixUpTasks(categoryId);
		final Collection<FixUpTask> byWarranty = this.findFilterWarrantyIdFixUpTasks(warrantyId);

		result.addAll(byKeyWord);
		result.addAll(byPriceRange);
		result.addAll(byDateRange);
		result.addAll(byWarranty);
		result.addAll(byCategory);

		result.retainAll(byKeyWord);
		result.retainAll(byPriceRange);
		result.retainAll(byDateRange);
		if (!byCategory.isEmpty())
			result.retainAll(byCategory);

		if (!byWarranty.isEmpty())
			result.retainAll(byWarranty);

		final Collection<FixUpTask> fix = new HashSet<>(result);

		for (final FixUpTask f : result)
			for (final Application a : f.getApplications())
				if (a.getStatus().equals("ACCEPTED"))
					fix.remove(f);

		result = fix;
		System.out.println("Al final queda:" + result);

		final Integer maxResults = this.systemConfigurationService.getConfiguration().getMaxResultsFinder();

		// R41
		if (result.size() > maxResults) {
			final List<FixUpTask> resultList = new ArrayList<>(result);
			result = new HashSet<>(resultList.subList(0, maxResults));
		}

		return result;
	}
	// R38.5
	public String getRatioOfFixUpTaskWithComplaint() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.fixUpTaskRepository.getRatioOfFixUpTaskWithComplaint();

		return result;
	}

	public Collection<FixUpTask> findFixUpTasksByHandyWorkerId(final int handyWorkerId) {
		Assert.isTrue(handyWorkerId != 0);

		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findFixUpTasksByHandyWorkerId(handyWorkerId);
		return result;
	}

}
