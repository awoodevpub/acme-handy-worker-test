
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select ref from Referee ref join ref.reports r where r.id = ?1")
	Referee findRefereeByReportId(int reportId);

}
