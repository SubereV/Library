package subereproject.scrawler.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import subereproject.scrawler.models.Book;

public class CustomizedBookRepositoryImpl implements CustomizedBookRepository {
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> findBookByKeywords(String keywords) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		entityManager.getTransaction().begin();
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Book.class)
				.get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("title", "author")
				.matching(keywords).createQuery();
		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);
		List<Book> result = jpaQuery.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return result;
	}

	@Override
	public void indexBooks() throws Exception {
		try {
			Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
			FullTextSession fullTextSession = (FullTextSession) Search.getFullTextEntityManager(session);
			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			throw e;
		}
	}
}
