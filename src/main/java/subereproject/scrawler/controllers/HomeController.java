package subereproject.scrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import subereproject.scrawler.services.ScrawlerBookService;

@Controller
@RequestMapping("/")
public class HomeController {
	
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
}
