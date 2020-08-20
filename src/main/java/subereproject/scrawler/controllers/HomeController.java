package subereproject.scrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import subereproject.scrawler.services.ScrawlerBookService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private ScrawlerBookService scrawler;
	
	@GetMapping
	public String get() {
		return "index.html";
	}

	@RequestMapping("test")
	public String test() {
		scrawler.run();
		return "success";
	}
}
