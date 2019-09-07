
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

	@Query("select w.id from Warranty w where w = ?1")
	int findIdFromWarranty(Warranty c);

}
