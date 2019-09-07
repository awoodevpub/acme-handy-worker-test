
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Service
@Transactional
public class FinderService {

	// Managed repository
	@Autowired
	private FinderRepository			finderRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private WarrantyService				warrantyService;

	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	public Finder create() {
		Finder result;

		result = new Finder();

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Assert.isTrue(finderId != 0);

		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Finder result;

		final Date searchMoment = new Date(System.currentTimeMillis() - 1);
		finder.setSearchMoment(searchMoment);

		result = this.finderRepository.save(finder);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	// Other business methods
	// R37.1
	public void changeFiltersFinder(String keyWord, Double minPrice, Double maxPrice, Date minDate, Date maxDate, final Category category, final Warranty warranty) {
		Finder result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorker = (HandyWorker) actorLogged;
		result = handyWorker.getFinder();
		keyWord = keyWord.toLowerCase();
		if (keyWord != "")
			result.setKeyWord(keyWord);

		if (minPrice == null) {
			minPrice = 0.0;
			result.setMinPrice(minPrice);
		}
		if (maxPrice == null) {
			maxPrice = 9999.99;
			result.setMaxPrice(maxPrice);
		}
		if (minDate == null) {
			final Calendar cal = Calendar.getInstance();
			cal.set(1000, 0, 1);
			minDate = cal.getTime();
			result.setMinDate(minDate);
		}
		if (maxDate == null) {
			final Calendar cal = Calendar.getInstance();
			cal.set(3000, 0, 1);
			maxDate = cal.getTime();
			result.setMaxDate(maxDate);
		}

		int categoryId = 0;
		int warrantyId = 0;
		if (category == null)
			result.setCategory(null);
		else
			categoryId = this.categoryService.findIdFromCategory(category);

		if (warranty == null)
			result.setWarranty(null);
		else
			warrantyId = this.warrantyService.findIdFromWarranty(warranty);

		final Collection<FixUpTask> fixUpTaskFinder = this.fixUpTaskService.findFixUpTasksFromFinderHandyWorkedLogged(keyWord, minPrice, maxPrice, minDate, maxDate, categoryId, warrantyId);
		result.setFixUpTasks(fixUpTaskFinder);
		result = this.finderRepository.save(result);
		handyWorker.setFinder(result);
		this.handyWorkerService.save(handyWorker);
	}

	//R40
	public boolean cleanCacheFinder(final Finder finder) {
		Assert.notNull(finder);

		Long searchMoment, nowMomment;
		Boolean result;
		searchMoment = finder.getSearchMoment().getTime();
		nowMomment = new Date(System.currentTimeMillis()).getTime();
		Long passedTime = (nowMomment - searchMoment) / 1000; //De milisegundos a segundos
		passedTime = passedTime / 3600; //De segundos a horas
		final Integer periodFinder = this.systemConfigurationService.getConfiguration().getPeriodFinder();

		result = passedTime >= periodFinder;

		return result;
	}

	public Finder getFinderFromHandyWorkerLogged() {

		Finder result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		final HandyWorker handyWorker = (HandyWorker) actorLogged;
		result = handyWorker.getFinder();
		return result;
	}
}
