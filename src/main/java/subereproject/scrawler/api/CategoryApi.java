package subereproject.scrawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subereproject.scrawler.models.Category;
import subereproject.scrawler.services.CategoryService;

import java.util.*;

@RestController
@RequestMapping("category-api")
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity getAllCategories() {
        Set<Map<String, String>> categories = new HashSet<>();
        categoryService.findAll().forEach(category -> {
            Map<String, String> cate = new HashMap<>();
            cate.put("id", String.valueOf(category.getId()));
            cate.put("name", category.getName());
            categories.add(cate);
        });
        return ResponseEntity.ok(categories);
    }

    @PostMapping("")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        return categoryService.findById(id)
                .map(cate -> {
                    cate.setName(category.getName());

                    return categoryService.save(cate);
                })
                .get();
    }
}
