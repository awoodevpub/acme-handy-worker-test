
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a join a.fixUpTask fx where fx.id = ?1 and a.status like 'ACCEPTED'")
	Application findApplicationAcceptedByFixUpTaskId(int fixUpTaskId);

	@Query("select a from Application a join a.fixUpTask fx where fx.id = ?1")
	Collection<Application> findApplicationsByFixUpTaskId(int fixUpTaskId);

	@Query("select a from Application a join a.fixUpTask fx where fx.id in (select f.id from Customer c, in (c.fixUpTasks) f where c.id = ?1)")
	Collection<Application> findApplicationsByCustomerId(int customerId);

	@Query("select a from Application a join a.handyWorker hw where hw.id = ?1")
	Collection<Application> findApplicationsByHandyWorkerId(int handyWorkerId);

	@Query("select avg(fx.applications.size), min(fx.applications.size), max(fx.applications.size), stddev(fx.applications.size) from FixUpTask fx")
	String getFixUpTaskApplicationsStatistics();

	@Query("select avg(a.offeredPrice), min(a.offeredPrice), max(a.offeredPrice), stddev(a.offeredPrice) from Application a")
	String getOfferedPricesApplicationsStatistics();

	@Query("select 1.0*count(a1)/(select count(a2) from Application a2) from Application a1 where a1.status = 'PENDING'")
	String getRatioPendingApplications();

	@Query("select 1.0*count(a1)/(select count(a2) from Application a2) from Application a1 where a1.status = 'ACCEPTED'")
	String getRatioAcceptedApplications();

	@Query("select 1.0*count(a1)/(select count(a2) from Application a2) from Application a1 where a1.status = 'REJECTED'")
	String getRatioRejectedApplications();

	@Query("select 1.0*count(a1)/(select count(a2) from Application a2) from Application a1 where a1.status = 'PENDING' and a1.fixUpTask.endDate < CURRENT_DATE")
	String getRatioPendingApplicationsWithElapsedPeriod();

}
