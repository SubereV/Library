package subereproject.scrawler.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import subereproject.scrawler.models.Book;

public interface BookService {

    List<String> getAllTitles();

    boolean existsById(Integer id);

	Optional<Book> findById(Integer id);

	List<Book> saveAll(List<Book> entities);

	Set getRandomBooks(int num);

	public void updateBook(int available, int total, String status, int id);

	Book save(Book entity);

	List<Integer> findAllId();

	List<String[]> findByCategoryNull();

	Book findByTitle(String title);

	Map<String, Object> getBookByCategory(Integer cateID, Integer page, Integer book_per_page);

    Set getBooksByTitleAuthor(String keyword);

	Set getTheMostBookBorrowed(Integer amount);

    Object getTheNewestBook(Integer amount);
}
