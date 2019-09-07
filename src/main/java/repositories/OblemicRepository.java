package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;
import domain.Oblemic;

@Repository
public interface OblemicRepository extends JpaRepository<Oblemic, Integer> {

	@Query("select o from Customer c join c.fixUpTasks fx join fx.oblemics o where c.id = ?1")
	Collection<Oblemic> findOblemicsByCustomerLoggedId(int customerId);
	
	// select t from Customer c join c.fixUpTasks fx join fx.traneuts t where c.id = 17573;
	// select t from Traneut t join t.fixUpTasks fx join fx.traneuts t where c.id = ?1 - OLD
	
	// DASHBOARD ------------------------------------
	@Query("select avg(1.0*(select count(o) from Oblemic o where o.isFinalMode = 1 and o member of fx.oblemics)), stddev(1.0*(select count(o) from Oblemic o where o.isFinalMode = 1 and o member of fx.oblemics)) from FixUpTask fx")
	String getOblemicsFixUpTasksStatistics();

	@Query("select 1.0*count(o1)/(select count(o2) from Oblemic o2) from Oblemic o1 where o1.isFinalMode = 1")
	Double getRatioPublishedOblemics();

	@Query("select 1.0*count(o1)/(select count(o2) from Oblemic o2) from Oblemic o1 where o1.isFinalMode = 0")
	Double getRatioUnpublishedOblemics();
	//--------------------------------------------------
	
	@Query("select f from FixUpTask f join f.oblemics o where o.id = ?1")
	FixUpTask getTaskByOblemicId(int oblemicId);
}
