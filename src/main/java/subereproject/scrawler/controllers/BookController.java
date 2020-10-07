package subereproject.scrawler.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.services.BookService;

@Controller
@RequestMapping("book")
public class BookController {
	@Autowired
	private BookService bookService; 
	
	@GetMapping("/image/{id}")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> bookImage(@PathVariable int id) {
		Optional<Book> bookById = bookService.findById(id); 
		if (bookById.isPresent()) {
			Book book = bookById.get(); 
			try {
				Path fileName = Paths.get("img/book", book.getId()+".jpg"); 
				byte[] buffer = Files.readAllBytes(fileName); 
				
				ByteArrayResource bar = new ByteArrayResource(buffer); 
				return ResponseEntity.ok()
					   .contentLength(buffer.length)
					   .contentType(MediaType.parseMediaType("image/jpg"))
					   .body(bar); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public String book(ModelMap model, @RequestParam(name = "id") int id) {
		Optional<Book> bookById = bookService.findById(id); 
		if (bookById.isPresent()) {
			model.addAttribute("book", bookById.get()); 
		}
		return "book-details";
	}
	
	@ResponseBody
	@GetMapping("null")
	public List<String[]> getBookNull(){
		return bookService.findByCategoryNull();
	}
}
