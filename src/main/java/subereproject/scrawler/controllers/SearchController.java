package subereproject.scrawler.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subereproject.scrawler.services.BookService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	BookService bookService;

	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity search(ModelMap model, @RequestParam String keywords) {
		Set<Map<String, String>> books = new HashSet<Map<String, String>>();
		bookService.findBookByKeywords(keywords).forEach((b) -> {
			Map<String, String> book = new HashMap<String, String>();
			book.put("id", String.valueOf(b.getId()));
			book.put("title", b.getTitle());
			books.add(book);
		});
		if (bookService.findBookByKeywords(keywords).size() == 0) {
			return ResponseEntity.ok("false");
		}

		return ResponseEntity.ok("false");
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
