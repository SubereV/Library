package subereproject.scrawler.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.models.Category;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>, CustomizedBookRepository {
	@Transactional
	@Modifying
	@Query("Update Book b set b.available = :newAvailable, b.total = :newTotal, b.status = :newStatus where b.id = :newId")
	void updateBook(@Param("newAvailable") int available, @Param("newTotal") int total,
			@Param("newStatus") String status, @Param("newId") int id);

	@Query("select b.title from Book b")
	List<String> findAllTitles();

	@Query("select b.id from Book b")
	List<Integer> findAllId();

	@Query("select b.id, b.title, b.author, b.available, b.total from Book b where b.category is null")
	List<String[]> findByCategoryNull();

	Book findByTitle(String title);
	List<Book> findAllByAuthor(String author);
	List<Book> findByCategory(Category category);
}
