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
import subereproject.scrawler.models.Category;
import subereproject.scrawler.services.BookService;
import subereproject.scrawler.services.CategoryService;
import subereproject.scrawler.services.CopiesService;

@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping
	public String index() {
		return "index";
	}
}
