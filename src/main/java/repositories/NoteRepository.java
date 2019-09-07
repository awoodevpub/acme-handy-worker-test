
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), stddev(r.notes.size) from Report r where r.isFinalMode = 1")
	String getReportNotesStatistics();
}
