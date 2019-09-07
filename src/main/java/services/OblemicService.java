package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.OblemicRepository;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.Oblemic;

@Service
@Transactional
public class OblemicService {
	// Managed repository


	// Supporting services
	@Autowired
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;
	
	@Autowired
	private OblemicRepository 			traneutRepository;


	public Oblemic create(){
		Oblemic result;
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);
		
		result = new Oblemic();
		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final Date date = new Date(System.currentTimeMillis() - 1);
		final String dateF = dateFormat.format(date).toString();
		
		final String keylet = RandomStringUtils.randomAlphanumeric(3).toUpperCase() + ":" + dateF.substring(0, 2) + ":" + dateF.substring(2,6);
		result.setKeylet(keylet);
		result.setIsFinalMode(false);
		System.out.println("-------------");
		System.out.println(keylet);
		System.out.println(dateF);
		System.out.println("-------------");
		return result;
	}
	
	public Collection<Oblemic> findAll(){
		Collection<Oblemic> result; 
		result = this.traneutRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Oblemic> findAllByCustomerId(int customerId){
		Collection<Oblemic> result;
		result = this.traneutRepository.findOblemicsByCustomerLoggedId(customerId);
		return result;
	}
	
	public Oblemic findOne(final int traneutId) {
		Assert.isTrue(traneutId != 0);

		Oblemic result;

		result = this.traneutRepository.findOne(traneutId);
		Assert.notNull(result);

		return result;
	}

	public FixUpTask findFixUpTaskByOblemicsId(int oblemicId){
		Assert.notNull(oblemicId);
		Assert.isTrue(oblemicId != 0);
		
		FixUpTask result;
		result = this.traneutRepository.getTaskByOblemicId(oblemicId);
		Assert.notNull(result);
		return result;
	}
	
	public Oblemic save(final Oblemic traneut, final int fixUpTaskId) {
		Assert.notNull(traneut);
		Oblemic result;
		
		FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		Assert.isTrue(this.fixUpTaskService.findFixUpTasksByCustomerLogged().contains(fixUpTask));
		this.actorService.checkUserLoginCustomer(actorLogged);
		
		
		if(traneut.getId() == 0){
			result = traneut;
		}else{
			Oblemic auxTraneut = this.traneutRepository.findOne(traneut.getId());
			if(!auxTraneut.getIsFinalMode()){
				result = this.traneutRepository.save(traneut);
			}else{
				result = auxTraneut;
			}
		}
		
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (result.getBody().contains(sw)) ? true : false;
			if (flagSpam) {
				actorLogged.setIsSuspicious(flagSpam);
				this.actorService.saveForSuspiciusActor(actorLogged);
				break;
			}
		}

		return result;
	}

	
	public void delete(final Oblemic oblemic, final FixUpTask fixUpTask) {
		Assert.notNull(oblemic);
		Assert.isTrue(oblemic.getId() != 0);
		Assert.isTrue(this.traneutRepository.exists(oblemic.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginCustomer(actorLogged);
		Assert.isTrue(this.fixUpTaskService.findFixUpTasksByCustomerLogged().contains(fixUpTask));
		final Customer customerOwner = this.customerService.findCustomerByFixUpTaskId(fixUpTask.getId());
		Assert.isTrue(actorLogged.equals(customerOwner));

		final Collection<Oblemic> traneuts = fixUpTask.getOblemics();
		traneuts.remove(oblemic);

		if(!oblemic.getIsFinalMode()){
			this.traneutRepository.delete(oblemic);
			fixUpTask.getOblemics().remove(oblemic);
			this.fixUpTaskService.save2(fixUpTask);
		}
	}

	// DASHBOARD -------------------------------------------------------------------

	public String getOblemicsFixUpTasksStatistics() {
		String result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.traneutRepository.getOblemicsFixUpTasksStatistics();

		return result;
	}
	
	public Double getRatioPublishedOblemics(){
		Double result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.traneutRepository.getRatioPublishedOblemics();

		return result;
	}
	public Double getRatioUnpublishedOblemics(){
		Double result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.traneutRepository.getRatioUnpublishedOblemics();

		return result;
	}
}
