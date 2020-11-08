package subereproject.scrawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import subereproject.scrawler.services.BookService;

@RestController
@RequestMapping("api/books")
public class BooksApi {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity allBooks() {
        System.out.println(bookService.getRandom30Books().size());
        return ResponseEntity.ok(bookService.getRandom30Books());
    }

    @GetMapping("/{id}")
    public ResponseEntity bookDetails(@PathVariable int id) {
        return ResponseEntity.ok(bookService.findById(id).get());
    }

    @GetMapping("/titles")
    public ResponseEntity getAllTitles() {
        return ResponseEntity.ok(bookService.getAllTitles());
    }

    //Todo: fetch category - /category={int}&page={int}
    @GetMapping
    public ResponseEntity getBookByCategory(@RequestParam Integer category, @RequestParam Integer page) {
        if (page == null) page = 1;
        if (category == null) category = 1;
        return ResponseEntity.ok(bookService.getBookByCategory(category, page));
    }

    @GetMapping("/titles/{title}")
    public ResponseEntity getTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.findByTitle(title));
    }
    //todo: fetch author - /author={String}&page={int}

    //todo: fetch new - /new={boolean}&page={int}

}
