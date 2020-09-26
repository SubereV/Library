package subereproject.scrawler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

	@GetMapping
	private String admin(ModelMap model) {
		model.addAttribute("dashboard", "active");
		model.addAttribute("page", "Dash Board");
		return "admin/index";
	}

	@RequestMapping("books")
	private String book(ModelMap model) {
		model.addAttribute("page", "Books");
		model.addAttribute("books", "active");
		return "admin/index";
	}

	@RequestMapping("new-books")
	private String newBooks() {
		return "";
	}
}
