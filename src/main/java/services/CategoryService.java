
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Managed repository
	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Simple CRUD methods
	// R12.3
	public Category create() {
		Category result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Category();
		final Collection<Category> childCategories = new HashSet<>();
		final Collection<FixUpTask> fixUpTasks = new HashSet<>();
		result.setChildsCategory(childCategories);
		result.setFixUpTasks(fixUpTasks);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Category findOne(final int categoryId) {
		Assert.isTrue(categoryId != 0);

		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	// R12.3
	public Category save(final Category category) {
		Assert.notNull(category);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Category result;

		if (category.getId() == 0) {
			final Collection<Category> categories = this.categoryRepository.findAll();
			Assert.isTrue(categories.contains(category.getParentCategory())); //Every category belongs to a parent category, except root category
		} else if (category.getParentCategory() == null)
			Assert.isTrue(category.getName().containsKey("CATEGORY"));

		result = this.categoryRepository.save(category);

		return result;
	}

	// R12.3
	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!category.getName().containsKey("CATEGORY")); // La categoría no puede ser la root category
		Assert.isTrue(category.getChildsCategory().isEmpty()); // La categoría no puede tener categorías hijas para poder ser borrada

		final Category parentCategory = category.getParentCategory();
		final Collection<Category> childsParentCategory = parentCategory.getChildsCategory();
		childsParentCategory.remove(category);
		parentCategory.setChildsCategory(childsParentCategory);
		this.categoryRepository.save(parentCategory);

		final Collection<Finder> finders = this.finderService.findAll();
		for (final Finder f : finders)
			if (f.getCategory() != null && f.getCategory().equals(category)) {
				f.setCategory(null);
				this.finderService.save(f);
			}

		this.categoryRepository.delete(category);
	}

	// Other business methods
	// R12.3
	public Collection<Category> findCatalogueCategories() {
		Collection<Category> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// R12.3
	public Category findCategory(final int categoryId) {
		Assert.isTrue(categoryId != 0);

		Category result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public void addCategoryToFixuptask(final int fixuptaskId, final int categoryId) {

		final FixUpTask fix = this.fixUpTaskService.findOne(fixuptaskId);
		Assert.notNull(fix);

		final Category cat = this.categoryRepository.findOne(categoryId);
		Assert.notNull(cat);

		final Collection<FixUpTask> fixs = cat.getFixUpTasks();
		System.out.println(fixs);
		fixs.add(fix);

		cat.setFixUpTasks(fixs);

		this.categoryRepository.save(cat);
	}

	public void clearCategoryToFixuptask(final int fixuptaskId) {

		final FixUpTask fix = this.fixUpTaskService.findOne(fixuptaskId);
		Assert.notNull(fix);

		final List<Category> cat = this.categoryRepository.findAll();
		Assert.notNull(cat);

		for(Category a : cat){
			if(a.getFixUpTasks().contains(fix)){
				a.getFixUpTasks().remove(fix);
				a.setFixUpTasks(a.getFixUpTasks());
			}
			this.categoryRepository.save(a);
		}
	}

	public Category saveForFixUpTask(final Category category) {
		Assert.notNull(category);

		Category result;

		result = this.categoryRepository.save(category);

		return result;
	}

	public int findIdFromCategory(final Category category) {
		int result;

		result = this.categoryRepository.findIdFromCategory(category);
		return result;
	}

}
