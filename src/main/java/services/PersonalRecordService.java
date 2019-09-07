
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Actor;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods
	public PersonalRecord create() {
		PersonalRecord result;

		result = new PersonalRecord();

		return result;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result;

		result = this.personalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		Assert.isTrue(personalRecordId != 0);

		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);
		Assert.notNull(result);

		return result;
	}

	public PersonalRecord save(final PersonalRecord personalRecord, final int curricumlumId) {
		Assert.notNull(personalRecord);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		PersonalRecord result;

		final Curriculum curriculum = this.curriculumService.findOne(curricumlumId);

		if (personalRecord.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.personalRecordRepository.save(personalRecord);

		this.curriculumService.save(curriculum, result);

		return result;
	}

	public PersonalRecord saveAux(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		return result;
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);
		Assert.isTrue(this.personalRecordRepository.exists(personalRecord.getId()));

		this.personalRecordRepository.delete(personalRecord);
	}

	// Other business methods

}
