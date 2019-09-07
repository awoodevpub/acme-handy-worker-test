
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Simple CRUD methods
	public Curriculum create() {
		Curriculum result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = new Curriculum();
		final PersonalRecord personalRecord = this.personalRecordService.create();
		final Collection<EducationRecord> educationRecords = new HashSet<>();
		final Collection<ProfessionalRecord> professionalRecords = new HashSet<>();
		final Collection<MiscellaneousRecord> miscellaneousRecords = new HashSet<>();
		final Collection<EndorserRecord> endorserRecords = new HashSet<>();

		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final Date date = new Date(System.currentTimeMillis() - 1);
		// R21
		final String ticker = dateFormat.format(date).toString() + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		result.setTicker(ticker);
		result.setHandyWorker((HandyWorker) actorLogged);
		result.setEducationRecords(educationRecords);
		result.setProfessionalRecords(professionalRecords);
		result.setMiscellaneousRecords(miscellaneousRecords);
		result.setEndorserRecords(endorserRecords);
		result.setPersonalRecord(personalRecord);

		return result;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Assert.isTrue(curriculumId != 0);

		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum, PersonalRecord personalRecord) {
		Assert.notNull(curriculum);

		Curriculum result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		if (curriculum.getId() == 0) {
			personalRecord = this.personalRecordService.saveAux(personalRecord);
			curriculum.setPersonalRecord(personalRecord);
		} else {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.curriculumRepository.save(curriculum);

		return result;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		Assert.isTrue(this.curriculumRepository.exists(curriculum.getId()));

		this.curriculumRepository.delete(curriculum);
	}

	// Other business methods

	public Curriculum findCurriculumByHandyWorkerLoggedId() {
		Curriculum result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		result = this.curriculumRepository.findCurriculumByHandyWorkerLoggedId(actorLogged.getId());
		return result;
	}
}
