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

    @GetMapping("/amounts/{num}")
    public ResponseEntity allBooks(@PathVariable Integer num) {
        System.out.println(bookService.getRandomBooks(num).size());
        return ResponseEntity.ok(bookService.getRandomBooks(num));
    }

    @GetMapping("/{id}")
    public ResponseEntity bookDetails(@PathVariable int id) {
        return ResponseEntity.ok(bookService.findById(id).get());
    }

    @GetMapping
    public ResponseEntity getBookByCategory(@RequestParam(defaultValue = "1") Integer category, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "12") Integer books_per_page) {
        return ResponseEntity.ok(bookService.getBookByCategory(category, page, books_per_page));
    }

    @GetMapping("/titles/{title}")
    public ResponseEntity getTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findByTitle(title));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity search(@PathVariable String keyword) {
        return ResponseEntity.ok(bookService.getBooksByTitleAuthor(keyword));
    }

    @GetMapping("/the-most-borrowed")
    public ResponseEntity get10theMostBorrowedBooks(@RequestParam(defaultValue = "12") Integer amount) {
        return ResponseEntity.ok(bookService.getTheMostBookBorrowed(amount));
    }

    // not need
    @GetMapping("/titles")
    public ResponseEntity getAllTitles() {
        return ResponseEntity.ok(bookService.getAllTitles());
    }
}
