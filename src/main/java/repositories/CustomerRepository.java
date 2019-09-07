
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c join c.fixUpTasks fx where fx.id = ?1")
	Customer findCustomerByFixUpTaskId(int fixUpTaskId);

	@Query("select c from Customer c join c.fixUpTasks fx join fx.applications a where a.id = ?1")
	Customer findCustomerByApplicationId(int applicationId);

	@Query("select distinct c from Customer c join c.fixUpTasks fx where (c.fixUpTasks.size) >= 1.1*(select avg(c.fixUpTasks.size) from Customer c ) order by fx.applications.size desc")
	Collection<Customer> getCustomersTenPercentFixUpTasksThanAverage();

	@Query("select c from Customer c join c.fixUpTasks fx join fx.complaints com where com.id = ?1")
	Customer findCustomerByComplaintId(int complaintId);

	@Query("select c from Customer c join c.fixUpTasks fx join fx.complaints com join com.reports r where r.id = ?1")
	Customer findCustomerByReportId(int reportId);

	@Query("select c from Customer c join c.fixUpTasks fx join fx.complaints com group by c order by sum(fx.complaints.size)")
	Collection<Customer> findTopThreeCustomersByComplaints();

	@Query("select e.endorserCustomer from Endorsement e where e.id = ?1")
	Customer findCustomerByEndorsementGivenId(int endorsementId);

	@Query("select distinct c from Customer c join c.fixUpTasks fx join fx.applications a join a.handyWorker hw where hw.id = ?1 and a.status like 'ACCEPTED'")
	Collection<Customer> getCustomersOfHandyWorker(int handyWorkerId);
}
