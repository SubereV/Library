package subereproject.scrawler.controllers;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.services.BookService;
import subereproject.scrawler.services.CopiesService;
import subereproject.scrawler.services.ScrawlerBookService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private CopiesService copyCopiesService;
	
	@Autowired
	private BookService bookService; 
	
	@Autowired
	private ScrawlerBookService scrawler;
	
	@GetMapping
	public String index() {
		return "index";
	}

	@RequestMapping("book")
	public String book() {
		return "book-details";
	}
	
	@RequestMapping("contact")
	public String contact() {
		return "contact";
	}
	
	@RequestMapping("favorite")
	public String test() {
		return "shoping-cart";
	}
	
	@RequestMapping("database/crawler")
	public String crawler() {
		scrawler.run();
		return "success";
	}
	
	@ModelAttribute(name = "topPopularBooks")
	public List<Book> findById() {
		List<Book> books = new ArrayList<>();
		List<Integer> bookIds = copyCopiesService.findTop7MostBorrowedBookId(); 
		
		for (Iterator iterator = bookIds.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			
			books.add(bookService.findById(id).get()); 
		}
		return books; 
	}
}
