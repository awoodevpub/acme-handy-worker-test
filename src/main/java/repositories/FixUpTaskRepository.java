
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select c.fixUpTasks from Customer c where c.id = ?1")
	Collection<FixUpTask> findFixUpTasksByCustomerId(int customerId);

	@Query("select fx from FixUpTask fx where fx.description like %?1% or fx.address like %?1% or fx.ticker like %?1%")
	Collection<FixUpTask> findFilterKeyWordFixUpTasks(String keyWord);

	@Query("select cat.fixUpTasks from Category cat where (KEY(cat.name) = ?1 or VALUE(cat.name) = ?1)")
	Collection<FixUpTask> findFilterCategoryNameFixUpTasks(String categoryName);

	@Query("select fx from FixUpTask fx where fx.maximumPrice >= ?1 and fx.maximumPrice <= ?2")
	Collection<FixUpTask> findFilterPriceFixUpTasks(Double minPrice, Double maxPrice);

	@Query("select fx from FixUpTask fx where fx.startDate between ?1 and ?2")
	Collection<FixUpTask> findFilterDateFixUpTasks(Date startDate, Date endDate);

	@Query("select fx from FixUpTask fx where fx.warranty.id = ?1")
	Collection<FixUpTask> findFilterWarrantyIdFixUpTasks(int id);

	@Query("select fx from FixUpTask fx where fx.id in (select f.id from Category c, in (c.fixUpTasks) f where c.id = ?1)")
	Collection<FixUpTask> findFilterCategoryIdFixUpTasks(int id);

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	String getCustomerFixUpTasksStatistics();

	@Query("select avg(fx.maximumPrice), min(fx.maximumPrice), max(fx.maximumPrice), stddev(fx.maximumPrice) from FixUpTask fx")
	String getMaximumPricesFixUpTasksStatistics();

	@Query("select sum(case when fx.complaints.size > 0 then 1.0 else 0.0 end)/count(distinct fx) from FixUpTask fx")
	String getRatioOfFixUpTaskWithComplaint();

	@Query("select fx from FixUpTask fx join fx.applications a join a.handyWorker hw where hw.id = ?1 and a.status like 'ACCEPTED'")
	Collection<FixUpTask> findFixUpTasksByHandyWorkerId(int handyWorkerId);
}
