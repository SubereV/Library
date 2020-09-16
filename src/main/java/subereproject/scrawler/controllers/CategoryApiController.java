package subereproject.scrawler.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import subereproject.scrawler.services.BookService;
import subereproject.scrawler.services.CategoryService;

@RestController
@RequestMapping("/cate/")
public class CategoryApiController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookService bookService;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/null")
	public ResponseEntity getallBooks() {
		Set<Map<String, String>> books = new HashSet<Map<String,String>>();
		bookService.findAll().forEach(book ->{
			Map<String, String> b = new HashMap<String, String>();
			b.put("id", String.valueOf(book.getId()));
			b.put("title", book.getTitle());
			books.add(b);
		});
		return ResponseEntity.ok(books);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("{id}")
	public ResponseEntity getBooks(@PathVariable Integer id) {
		Set<Map<String, String>> books = new HashSet<Map<String,String>>();
		categoryService.findById(id).get().getBooks().forEach(book ->{
			Map<String, String> b = new HashMap<String, String>();
			b.put("id", String.valueOf(book.getId()));
			b.put("title", book.getTitle());
			books.add(b);
		});
		return ResponseEntity.ok(books);
	}
	
}
