
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods
	public EducationRecord create() {
		EducationRecord result;

		result = new EducationRecord();

		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);

		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord, final int curricumlumId) {
		Assert.notNull(educationRecord);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		EducationRecord result;

		final Curriculum curriculum = this.curriculumService.findOne(curricumlumId);

		if (educationRecord.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		if (educationRecord.getEndDateStudy() != null)
			Assert.isTrue(educationRecord.getStartDateStudy().before(educationRecord.getEndDateStudy()));

		result = this.educationRecordRepository.save(educationRecord);

		final Collection<EducationRecord> educationRecords = curriculum.getEducationRecords();
		if (educationRecords.contains(educationRecord))
			educationRecords.remove(educationRecord);
		educationRecords.add(result);
		curriculum.setEducationRecords(educationRecords);
		this.curriculumService.save(curriculum, curriculum.getPersonalRecord());

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);
		Assert.isTrue(this.educationRecordRepository.exists(educationRecord.getId()));

		this.educationRecordRepository.delete(educationRecord);
	}

	// Other business methods

}
