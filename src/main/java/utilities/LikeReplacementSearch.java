package utilities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Level;

import org.apache.log4j.LogManager;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import utilities.internal.ConsoleReader;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;
import domain.FixUpTask;

public class LikeReplacementSearch {

	private static String word;

	/**
	 * @param args
	 * @throws Throwable
	 */

	public static void main(final String[] args) throws Throwable {

		/**
		 * OFF WARNING LOGS
		 */
		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);

		while (true) {

			final EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("Acme-Handy-Worker");

			final EntityManager entityManager = entityManagerFactory
					.createEntityManager();

			System.out.println("Insert a search word");
			System.out.println("---------------------------");

			/**
			 * READ THE CONSOLE
			 */
			entityManager.getTransaction().begin();

			final ConsoleReader cr = new ConsoleReader();
			LikeReplacementSearch.word = cr.readLine();
			System.out.println("Your results =>");
			/**
			 * METHOD:
			 */
			LikeReplacementSearch.search(entityManager);
			System.out.println("Search finished");
			System.out.println("---------------------------");
		}

	}

	/**
	 * Search fix-up tasks that contain one word in their tickers, descriptions,
	 * or addresses
	 *
	 * @param entityManager
	 */

	public static void search(final EntityManager entityManager) {
		final FullTextEntityManager fullTextEntityManager = Search
				.getFullTextEntityManager(entityManager);

		final QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(FixUpTask.class).get();

		final Query querySearch = qb.keyword()
				.onFields("ticker", "description", "address")
				.matching(LikeReplacementSearch.word).createQuery();

		final javax.persistence.Query jpaQuery = fullTextEntityManager
				.createFullTextQuery(querySearch, FixUpTask.class);

		@SuppressWarnings("unchecked")
		final List<FixUpTask> result = jpaQuery.getResultList();

		/**
		 * PRINT THE RESULTS
		 */
		SchemaPrinter.print(result);

		SchemaPrinter.print("You got " + result.size() + " results.");
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
