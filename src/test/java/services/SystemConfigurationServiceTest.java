
package services;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.SystemConfiguration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// Services under test
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@Test
	public void testComputeScores() {
		super.authenticate("admin1");
		final Map<Actor, Double> scores = this.systemConfigurationService.computeScores();
		for (final Actor a : scores.keySet()) {
			final Double printScore = scores.get(a);
			System.out.println(a + " --> " + printScore);
		}
	}

	@Test
	public void testCreatePositiveWord() {
		super.authenticate("admin1");
		SystemConfiguration systemConfiguration;
		systemConfiguration = this.systemConfigurationService.findOne(super.getEntityId("systemConfiguration1"));
		this.systemConfigurationService.createPositiveWord("Aprobado");
		systemConfiguration = this.systemConfigurationService.findOne(systemConfiguration.getId());
		Assert.isTrue(systemConfiguration.getPositiveWords().contains("aprobado"));
		super.unauthenticate();
	}

	@Test
	public void testCreateNegativeWord() {
		super.authenticate("admin1");
		SystemConfiguration systemConfiguration;
		systemConfiguration = this.systemConfigurationService.findOne(super.getEntityId("systemConfiguration1"));
		this.systemConfigurationService.createNegativeWord("Suspenso");
		systemConfiguration = this.systemConfigurationService.findOne(systemConfiguration.getId());
		Assert.isTrue(systemConfiguration.getNegativeWords().contains("suspenso"));
		super.unauthenticate();
	}

	@Test
	public void testDeletePositiveWord() {
		super.authenticate("admin1");
		SystemConfiguration systemConfiguration;
		systemConfiguration = this.systemConfigurationService.findOne(super.getEntityId("systemConfiguration1"));
		this.systemConfigurationService.deletePositiveWord("good");
		systemConfiguration = this.systemConfigurationService.findOne(systemConfiguration.getId());
		Assert.isTrue(!systemConfiguration.getPositiveWords().contains("good"));
		super.unauthenticate();
	}

	@Test
	public void testDeleteNegativeWord() {
		super.authenticate("admin1");
		SystemConfiguration systemConfiguration;
		systemConfiguration = this.systemConfigurationService.findOne(super.getEntityId("systemConfiguration1"));
		this.systemConfigurationService.deleteNegativeWord("bad");
		systemConfiguration = this.systemConfigurationService.findOne(systemConfiguration.getId());
		Assert.isTrue(!systemConfiguration.getNegativeWords().contains("bad"));
		super.unauthenticate();
	}
}
