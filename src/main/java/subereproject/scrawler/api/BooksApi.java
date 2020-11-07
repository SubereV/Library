package subereproject.scrawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import subereproject.scrawler.services.BookService;

@RestController
@RequestMapping("api/books")
public class BooksApi {
    @Autowired
    private BookService bookService;


    @GetMapping("/")
    public ResponseEntity allBooks() {
        System.out.println(bookService.random30Books().size());
        return ResponseEntity.ok(bookService.random30Books());
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

    //todo: fetch author - /author={String}&page={int}

    //todo: fetch new - /new={boolean}&page={int}

}
