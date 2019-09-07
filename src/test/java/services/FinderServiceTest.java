
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Finder;
import domain.HandyWorker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private FinderService		finderService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void testChangeFiltersFinder() {
		super.authenticate("handyworker1");
		final Finder finder;
		final String keyWord = "Fix";
		final Double minPrice = 50.0;
		final Double maxPrice = 100.0;
		final Category category = this.categoryService.findOne(super.getEntityId("category2"));
		//finder = this.finderService.changeFiltersFinder(keyWord, minPrice, maxPrice, null, null, category, null);
		final HandyWorker handyworker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		//Assert.notNull(finder);
		Assert.isTrue(handyworker.getFinder().getKeyWord().equals(keyWord.toLowerCase()));
		super.unauthenticate();
	}
}
