
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select hw from HandyWorker hw join hw.applications a where a.id = ?1")
	HandyWorker findHandyWorkerByApplicationId(int applicationId);

	@Query("select hw from HandyWorker hw where (select count(a.handyWorker) from Application a where a.status = 'ACCEPTED' and a.handyWorker.id=hw.id) >= 1.1*(select avg(1.0*(select count(a.handyWorker) from Application a where a.status = 'ACCEPTED' and a.handyWorker.id=hw.id)) from HandyWorker h) order by hw.applications.size desc")
	Collection<HandyWorker> getHandyWorkerGotAcceptedTenPercentApplications();

	@Query("select hw from HandyWorker hw join hw.applications a join a.fixUpTask fx join fx.complaints com join com.reports r where r.id = ?1 and a.status like 'ACCEPTED'")
	HandyWorker findHandyWorkerByReportId(int reportId);

	@Query("select hw from HandyWorker hw join hw.applications a join a.fixUpTask fx join fx.complaints com where com.id = ?1 and a.status like 'ACCEPTED'")
	HandyWorker findHandyWorkedByComplaintId(int complaintId);

	@Query("select a.handyWorker from Application a where a.status='ACCEPTED' group by a.handyWorker order by sum(a.fixUpTask.complaints.size)")
	Collection<HandyWorker> findTopThreeHandyWorkerByComplaints();

	@Query("select t.handyWorker from Tutorial t where t.id = ?1")
	HandyWorker findHandyWorkerByTutorialId(int tutorialId);

	@Query("select e.endorserHandyWorker from Endorsement e where e.id = ?1")
	HandyWorker findHandyWorkerByEndorsementGivenId(int endorsementId);

	@Query("select c.handyWorker from Curriculum c where c.id = ?1")
	HandyWorker findHandyWorkerByCurriculumId(int curriculumId);

}
