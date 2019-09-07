
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select com from Customer c join c.fixUpTasks fx join fx.complaints com where c.id = ?1")
	Collection<Complaint> findComplaintsByCustomerLoggedId(int customerId);

	@Query("select com from Complaint com where com.reports is empty")
	Collection<Complaint> findComplaintsWithoutReports();

	@Query("select com from Complaint com join com.reports r join r.referee ref where ref.id = ?1")
	Collection<Complaint> findComplaintsByRefereeId(int refereeId);

	@Query("select com from HandyWorker hw join hw.applications a join a.fixUpTask fx join fx.complaints com where hw.id = ?1 and a.status like 'ACCEPTED'")
	Collection<Complaint> findComplaintsByHandyWorkerLoggedId(int handyWorkerid);

	@Query("select min(fx.complaints.size), max(fx.complaints.size), avg(fx.complaints.size), stddev(fx.complaints.size) from FixUpTask fx")
	String getFixUpTaskComplaintsStatistics();

}
