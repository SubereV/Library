package subereproject.scrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import subereproject.scrawler.services.FindnBookService;
import subereproject.scrawler.services.UpdateBookDBService;

@Controller
@RequestMapping("database")
public class DatabaseController {

	@Autowired
	private UpdateBookDBService updateBookService;

	@Autowired
	private FindnBookService findnbookService;

	@RequestMapping("update")
	public String crawler() {
		updateBookService.updateBooks();
		return "success";
	}

	@RequestMapping("findNewBook")
	public String findnBook() {
		findnbookService.FindNewBook();
		return "success";
	}
}
