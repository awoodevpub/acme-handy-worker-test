
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Actor;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services
	@Autowired
	private CurriculumService				curriculumService;
	@Autowired
	private HandyWorkerService				handyWorkerService;
	@Autowired
	private ActorService					actorService;


	// Simple CRUD methods
	public MiscellaneousRecord create() {
		MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		Assert.isTrue(miscellaneousRecordId != 0);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord, final int curricumlumId) {
		Assert.notNull(miscellaneousRecord);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		MiscellaneousRecord result;

		final Curriculum curriculum = this.curriculumService.findOne(curricumlumId);

		if (miscellaneousRecord.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		final Collection<MiscellaneousRecord> miscellaneousRecords = curriculum.getMiscellaneousRecords();
		if (miscellaneousRecords.contains(miscellaneousRecord))
			miscellaneousRecords.remove(miscellaneousRecord);
		miscellaneousRecords.add(result);
		curriculum.setMiscellaneousRecords(miscellaneousRecords);
		this.curriculumService.save(curriculum, curriculum.getPersonalRecord());

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods

}
