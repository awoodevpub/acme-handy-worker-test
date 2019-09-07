
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Actor;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services

	@Autowired
	private CurriculumService			curriculumService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods
	public EndorserRecord create() {
		EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;

		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);

		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord, final int curricumlumId) {
		Assert.notNull(endorserRecord);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		EndorserRecord result;

		final Curriculum curriculum = this.curriculumService.findOne(curricumlumId);

		if (endorserRecord.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.endorserRecordRepository.save(endorserRecord);

		final Collection<EndorserRecord> endorserRecords = curriculum.getEndorserRecords();
		if (endorserRecords.contains(endorserRecord))
			endorserRecords.remove(endorserRecord);
		endorserRecords.add(result);
		curriculum.setEndorserRecords(endorserRecords);
		this.curriculumService.save(curriculum, curriculum.getPersonalRecord());

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		Assert.isTrue(this.endorserRecordRepository.exists(endorserRecord.getId()));

		this.endorserRecordRepository.delete(endorserRecord);
	}

	// Other business methods

}
