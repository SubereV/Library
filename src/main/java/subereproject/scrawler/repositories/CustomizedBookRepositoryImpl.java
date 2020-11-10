package subereproject.scrawler.repositories;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import subereproject.scrawler.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedBookRepositoryImpl implements CustomizedBookRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void indexBooks() throws Exception{
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Book> searchBookByKeyword(String keyword){
        Query query = getQueryBuilder()
                        .keyword()
                        .onFields("title", "author")
                        .matching(keyword)
                        .createQuery();

        List<Book> results = getJpaQuery(query).getResultList();
        return results;
    }

    private FullTextQuery getJpaQuery(Query luceneQuery){
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);
    }

    private QueryBuilder getQueryBuilder(){
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Book.class)
                .get();
    }
}
