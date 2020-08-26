package subereproject.scrawler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book")
public class BookController {
	
	@GetMapping
	public String book() {
		return "book-details";
	}

}
