package subereproject.scrawler.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subereproject.scrawler.models.Book;
import subereproject.scrawler.repositories.BookRepository;
import subereproject.scrawler.services.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Integer> findAllId() {
		return bookRepository.findAllId();
	}

	@Override
	public Book save(Book entity) {
		System.out.print("success");
		return (Book) bookRepository.save(entity);
	}

	@Override
	public Book findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	@Override
	public List<String[]> findByCategoryNull() {
		return bookRepository.findByCategoryNull();
	}

	@Override
	public List<Book> saveAll(List<Book> entities) {
		return (List<Book>) bookRepository.saveAll(entities);
	}

	@Override
	public Optional<Book> findById(Integer id) {
		return bookRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return bookRepository.existsById(id);
	}

	@Override
	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}

	@Override
	public List<Book> findAllById(List<Integer> ids) {
		return (List<Book>) bookRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return bookRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		bookRepository.deleteById(id);
	}

	@Override
	public void delete(Book entity) {
		bookRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Book> entities) {
		bookRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		bookRepository.deleteAll();
	}

	@Override
	public void updateBook(int available, int total, String status,  int id) {
		bookRepository.updateBook(available, total, status, id);
	}
	
}
