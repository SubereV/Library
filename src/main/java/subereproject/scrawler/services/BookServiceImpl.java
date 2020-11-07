package subereproject.scrawler.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.models.BookCopies;
import subereproject.scrawler.repositories.BookRepository;

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
	public List<String> getAllTitles(){
		return bookRepository.findAllByTitle();
	}

	@Override
	public boolean existsById(Integer id) {
		return bookRepository.existsById(id);
	}

	private Map<String, Object> getBookInfo(Book book){
		Map<String, Object> details = new HashMap<>();
		details.put("id", book.getId());
		details.put("title", book.getTitle());
		details.put("author", book.getAuthor());
		details.put("available", book.getAvailable());
		details.put("total", book.getTotal());
		return details;
	}
	@Override
	public Set random30Books() {
		Integer total = findAllId().size();
		List<Book> allBooks = (List<Book>) bookRepository.findAll();
		Set<Map<String, Object>> books =  new HashSet<>();
		for (int i = 0; i < 30; i++) {
			Book book = allBooks.get(new Random().nextInt(total-i));
			books.add(getBookInfo(book));
			allBooks.remove(book);
		}
		return books;
	}

	@Override
	public void updateBook(int available, int total, String status,  int id) {
		bookRepository.updateBook(available, total, status, id);
	}
	
}
