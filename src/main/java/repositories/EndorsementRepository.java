
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.endorserCustomer.id = ?1")
	Collection<Endorsement> findEndorsementsGivenByCustomerLoggedId(int customerId);

	@Query("select e from Endorsement e where  e.endorsedCustomer.id = ?1")
	Collection<Endorsement> findEndorsementsReceivedByCustomerLoggedId(int customerId);

	@Query("select e from Endorsement e where e.endorserHandyWorker.id = ?1")
	Collection<Endorsement> findEndorsementsGivenByHandyWorkerLoggedId(int handyWorkerId);

	@Query("select e from Endorsement e where e.endorsedHandyWorker.id = ?1")
	Collection<Endorsement> findEndorsementsReceivedByHandyWorkerLoggedId(int handyWorkerId);
}
