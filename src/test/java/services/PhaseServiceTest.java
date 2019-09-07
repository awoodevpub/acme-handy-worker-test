
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;
import domain.Phase;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		final Phase phase = this.phaseService.create();
		Assert.notNull(phase);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Phase> phases = this.phaseService.findAll();
		Assert.notNull(phases);
	}

	@Test
	public void testFindOne() {
		Phase phase;
		phase = this.phaseService.findOne(super.getEntityId("phase1"));
		Assert.notNull(phase);
	}

	@Test
	public void testFindWorkPlanByFixUpTaskId() {
		super.authenticate("handyWorker1");
		Collection<Phase> phases;
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		phases = this.phaseService.findWorkPlanByFixUpTaskId(fixUpTask.getId());
		Assert.notNull(phases);
		super.unauthenticate();
	}

	@Test
	public void testCreateSavePhase() {
		super.authenticate("handyWorker1");
		Phase phase;
		Collection<Phase> phases;
		phase = this.phaseService.create();
		phase.setTitle("Phase Test");
		phase.setDescription("Fix the door");
		final Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 2, 10, 30);
		final Date startMoment = cal.getTime();
		phase.setStartMoment(startMoment);
		cal.set(2018, 10, 13, 11, 45);
		final Date endMoment = cal.getTime();
		phase.setEndMoment(endMoment);
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		phase = this.phaseService.save(phase, fixUpTask);
		phases = this.phaseService.findAll();
		Assert.isTrue(phases.contains(phase));
		super.unauthenticate();
	}

	@Test
	public void testSaveWorkPlan() {
		super.authenticate("handyWorker1");
		Collection<Phase> workPlanOld, workPlanNew;
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		workPlanOld = fixUpTask.getPhases();
		for (final Phase p : workPlanOld)
			p.setDescription("Paused until further notice");
		workPlanOld = this.phaseService.saveWorkPlan(workPlanOld, fixUpTask);
		workPlanNew = fixUpTask.getPhases();
		for (final Phase p : workPlanNew)
			Assert.isTrue(p.getDescription().equals("Paused until further notice"));
		Assert.isTrue(workPlanNew.containsAll(workPlanOld));
		super.unauthenticate();
	}

	@Test
	public void testDeletePhase() {
		super.authenticate("handyWorker1");
		Phase phase;
		Collection<Phase> phases;
		phase = this.phaseService.findOne(super.getEntityId("phase1"));
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		this.phaseService.delete(phase, fixUpTask);
		phases = this.phaseService.findAll();
		Assert.isTrue(!phases.contains(phase));
		super.unauthenticate();
	}

	@Test
	public void testDeleteWorkPlan() {
		super.authenticate("handyWorker1");
		Collection<Phase> workPlanNew;
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask2"));
		this.phaseService.deleteWorkPlan(fixUpTask.getPhases(), fixUpTask);
		workPlanNew = fixUpTask.getPhases();
		Assert.isTrue(workPlanNew.isEmpty());
		super.unauthenticate();
	}
}
