package subereproject.scrawler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import subereproject.scrawler.models.Category;
import subereproject.scrawler.services.CategoryService;

@Controller
@RequestMapping("category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String Cate() {
		return "bookList";
	}
	//Replace by book-api
	@GetMapping("/{id}")
	public String getBooks(ModelMap model,@PathVariable Integer id) {
		model.addAttribute("id", id);
		return "bookList";
	}
	
	@ModelAttribute(name = "categories")
	public List<Category> getCateList(){
		return categoryService.findAll();
	}
}
