package subereproject.scrawler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.services.BookService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	BookService bookService;

	@GetMapping
	public ResponseEntity search(ModelMap model,@RequestParam String keywords) {
		List<Book> books = bookService.findBookByKeywords(keywords);
		return ResponseEntity.ok().body(books);
	}

	@GetMapping("/indexData")
	public String indexData() {
		try {
			bookService.indexBooks();
		} catch (Exception e) {
			System.out.println("Index> " + e);
		}
		return "success";
	}

}
