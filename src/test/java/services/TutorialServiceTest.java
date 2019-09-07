
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TutorialServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private TutorialService	tutorialService;

	@Autowired
	private SectionService	sectionService;


	@Test
	public void testCreate() {
		super.authenticate("handyworker1");
		final Tutorial tutorial = this.tutorialService.create();
		Assert.notNull(tutorial);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		Assert.notNull(tutorials);
	}

	@Test
	public void testFindOne() {
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Assert.notNull(tutorial);
	}

	@Test
	public void testFindTutorialsByHandyWorkerLoggedId() {
		super.authenticate("handyworker1");
		Collection<Tutorial> tutorials;
		tutorials = this.tutorialService.findTutorialsByHandyWorkerLoggedId();
		Assert.notNull(tutorials);
		super.unauthenticate();
	}

	@Test
	public void testFindTutorialByHandyWorkerLoggedId() {
		super.authenticate("handyworker1");
		Tutorial tutorial;
		tutorial = this.tutorialService.findTutorialByHandyWorkerLoggedId(super.getEntityId("tutorial1"));
		Assert.notNull(tutorial);
		super.unauthenticate();
	}

	@Test
	public void testCreateSave() {
		super.authenticate("handyworker1");
		Tutorial tutorial;
		tutorial = this.tutorialService.create();
		final Collection<Section> sections = tutorial.getSections();
		final Section section = this.sectionService.findOne(super.getEntityId("section1"));
		sections.add(section);
		tutorial.setSections(sections);
		tutorial.setTittle("Tutorial test");
		tutorial.setSummary("Sumary of tutorial");
		tutorial = this.tutorialService.save(tutorial);
		tutorial = this.tutorialService.findOne(tutorial.getId());
		Assert.notNull(tutorial);
		super.unauthenticate();
	}

	@Test
	public void testModifySave() {
		super.authenticate("handyworker1");
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		tutorial.setTittle("Test tittle");
		tutorial = this.tutorialService.save(tutorial);
		Assert.isTrue(tutorial.getTittle().equals("Test tittle"));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("handyworker1");
		Tutorial tutorial;
		Collection<Tutorial> tutorials;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.tutorialService.delete(tutorial);
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(!tutorials.contains(tutorial));
		super.unauthenticate();
	}
}
