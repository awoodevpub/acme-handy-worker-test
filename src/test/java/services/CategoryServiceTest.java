
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private CategoryService	categoryService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");
		final Category category = this.categoryService.create();
		Assert.notNull(category);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Category> categories = this.categoryService.findAll();
		Assert.notNull(categories);
	}

	@Test
	public void testFindOne() {
		Category category;
		category = this.categoryService.findOne(super.getEntityId("category1"));
		Assert.notNull(category);
	}

	@Test
	public void testFindCatalogueCategories() {
		super.authenticate("admin1");
		Collection<Category> categories;
		categories = this.categoryService.findCatalogueCategories();
		Assert.notNull(categories);
		super.unauthenticate();
	}

	@Test
	public void testFindCategory() {
		super.authenticate("admin1");
		Category category;
		category = this.categoryService.findCategory(super.getEntityId("category1"));
		Assert.notNull(category);
		super.unauthenticate();
	}

	@Test
	public void testCreateSave() {
		super.authenticate("admin1");
		Category category;
		Collection<Category> categories;
		category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("Test category", "Categoría de prueba");
		category.setName(name);
		final Category parentCategory = this.categoryService.findCategory(super.getEntityId("category1"));
		category.setParentCategory(parentCategory);
		category = this.categoryService.save(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(category));
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("admin1");
		Category category;
		category = this.categoryService.findOne(super.getEntityId("category2"));
		final Map<String, String> name = new HashMap<>();
		name.put("Test category", "Categoría de prueba");
		category.setName(name);
		category = this.categoryService.save(category);
		Assert.isTrue(category.getName().containsKey("Test category"));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("admin1");
		Category category;
		Collection<Category> categories;
		category = this.categoryService.findOne(super.getEntityId("category3"));
		this.categoryService.delete(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.contains(this.categoryService));
		super.unauthenticate();
	}
}
