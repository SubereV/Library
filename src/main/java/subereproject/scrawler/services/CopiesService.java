package subereproject.scrawler.services;

import java.util.List;
import java.util.Optional;

import subereproject.scrawler.models.BookCopies;

public interface CopiesService {
	public List<Integer> findTop7MostBorrowedBookId();

	void deleteAll();

	void deleteAll(List<BookCopies> entities);

	void delete(BookCopies entity);

	void deleteById(String id);

	long count();

	List<BookCopies> findAllById(Iterable<String> ids);

	List<BookCopies> findAll();

	boolean existsById(String id);

	Optional<BookCopies> findById(String id);

	List<BookCopies> saveAll(List<BookCopies> entities);

	BookCopies save(BookCopies entity);

}
