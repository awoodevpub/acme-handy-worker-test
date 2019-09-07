
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Actor;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private CurriculumService				curriculumService;
	@Autowired
	private HandyWorkerService				handyWorkerService;
	@Autowired
	private ActorService					actorService;


	// Simple CRUD methods
	public ProfessionalRecord create() {
		ProfessionalRecord result;

		result = new ProfessionalRecord();

		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;

		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		Assert.isTrue(professionalRecordId != 0);

		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord, final int curricumlumId) {
		Assert.notNull(professionalRecord);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginHandyWorker(actorLogged);

		ProfessionalRecord result;

		final Curriculum curriculum = this.curriculumService.findOne(curricumlumId);

		if (professionalRecord.getId() != 0) {
			final HandyWorker handyWorkerOwner = this.handyWorkerService.findHandyWorkerByCurriculumId(curriculum.getId());
			Assert.isTrue(actorLogged.equals(handyWorkerOwner));
		}

		if (professionalRecord.getEndDateWork() != null)
			Assert.isTrue(professionalRecord.getStartDateWork().before(professionalRecord.getEndDateWork()));

		result = this.professionalRecordRepository.save(professionalRecord);

		final Collection<ProfessionalRecord> professionalRecords = curriculum.getProfessionalRecords();
		if (professionalRecords.contains(professionalRecord))
			professionalRecords.remove(professionalRecord);
		professionalRecords.add(result);
		curriculum.setProfessionalRecords(professionalRecords);
		this.curriculumService.save(curriculum, curriculum.getPersonalRecord());

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		Assert.isTrue(this.professionalRecordRepository.exists(professionalRecord.getId()));

		this.professionalRecordRepository.delete(professionalRecord);
	}

	// Other business methods

}
