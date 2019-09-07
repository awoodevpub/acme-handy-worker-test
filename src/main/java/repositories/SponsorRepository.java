
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	@Query("select ss.sponsor from Sponsorship ss where ss.id = ?1")
	Sponsor findSponsorBySponsorshipId(int sponsorshipId);

}
