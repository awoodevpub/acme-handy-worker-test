
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	@Query("select rep from Report rep where rep.complaint.id = ?1")
	Collection<Report> findAllByComplaint(int complaintId);
}
