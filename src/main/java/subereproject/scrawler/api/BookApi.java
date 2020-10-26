package subereproject.scrawler.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.services.BookService;

@RestController
@RequestMapping("book-api")
public class BookApi {
	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<Set<Map<String, String>>> getAllBooks() {
		Set<Map<String, String>> books = new HashSet<Map<String, String>>();
		bookService.findAll().forEach(book -> {
			if (book.getCategory() != null) {
				Map<String, String> b = new HashMap<String, String>();
				b.put("id", String.valueOf(book.getId()));
				b.put("title", book.getTitle());
				b.put("category", book.getCategory().getName());
				b.put("author", book.getAuthor());
				books.add(b);
			}
		});
		return ResponseEntity.ok(books);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
		return ResponseEntity.ok(bookService.findById(id).get());
	}
	@GetMapping("/title/{title}")
	public ResponseEntity<Map<String, String>> getBookByTitle(@PathVariable String title) {
		Book book = bookService.findByTitle(title);
		Map<String, String> b = new HashMap<String, String>();
		b.put("id", String.valueOf(book.getId()));
		b.put("title", book.getTitle());
		b.put("description", book.getDescription());
		b.put("publisher", book.getPublisher());
		b.put("available", String.valueOf(book.getAvailable()));
		b.put("total", String.valueOf(book.getTotal()));
		b.put("status", String.valueOf(book.getStatus()));
		b.put("no", book.getNo());
		b.put("category", String.valueOf(book.getCategory().getName()));
		b.put("author", book.getAuthor());
		return ResponseEntity.ok(b);
	}
	@GetMapping("/newBook")
	public ResponseEntity<List<String[]>> getNewBook(){
		return ResponseEntity.ok(bookService.findByCategoryNull());
	}

}
