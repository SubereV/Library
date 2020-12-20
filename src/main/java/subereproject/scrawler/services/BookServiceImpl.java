package subereproject.scrawler.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;
    @Override
    public List<Integer> findAllId() {
        return bookRepository.findAllId();
    }

    @Override
    public Book save(Book entity) {
        System.out.print("success");
        return bookRepository.save(entity);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<String[]> findByCategoryNull() {
        return bookRepository.findByCategoryNull();
    }

    @Override
    public List<Book> saveAll(List<Book> entities) {
        return (List<Book>) bookRepository.saveAll(entities);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<String> getAllTitles() {
        return bookRepository.findAllTitles();
    }

    @Override
    public boolean existsById(Integer id) {
        return bookRepository.existsById(id);
    }

    private Map<String, Object> getBookInfo(Book book) {
        Map<String, Object> details = new HashMap<>();
        details.put("id", book.getId());
        details.put("title", book.getTitle());
        details.put("author", book.getAuthor());
        details.put("available", book.getAvailable());
        details.put("total", book.getTotal());
        return details;
    }

    @Override
    public Set getRandomBooks(int num) {
        Integer total = findAllId().size();
        List<Book> allBooks = (List<Book>) bookRepository.findAll();
        Set<Map<String, Object>> books = new HashSet<>();
        for (int i = 0; i < num; i++) {
            Book book = allBooks.get(new Random().nextInt(total - i));
            books.add(getBookInfo(book));
            allBooks.remove(book);
        }
        return books;
    }

    @Override
    public void updateBook(int available, int total, String status, int id) {
        bookRepository.updateBook(available, total, status, id);
    }

    @Override
    public Map<String, Object> getBookByCategory(Integer cateID, Integer page, Integer books_per_page) {
        List<Book> books = bookRepository.findByCategory(categoryService.findById(cateID).get());
        List<Object> books1 =new ArrayList<>();
        Map<String, Object> object = new HashMap<>();
        Map<String,Integer> pages = new HashMap<>();
        pages.put("total_page",books.size()/books_per_page+(books.size()%books_per_page==0?0:1));
        pages.put("current_page",page);
        object.put("pages",pages);
        for (int i = (page - 1) * books_per_page; i < page * books_per_page; i++) {
            if (i < books.size()) {
                books1.add(getBookInfo(books.get(i)));
            }
        }
        object.put("books",books1);

        return object;
    }

    @Override
    public Set getBooksByTitleAuthor(String keyword){
        List<Book> books = bookRepository.searchTitleAuthor(keyword);
        Set<Map> result = new HashSet<>();
        for (Book b :
                books) {
            result.add(getBookInfo(b));
        }
        return result;
    }

    @Override
    public Set getTheMostBookBorrowed(Integer amount){
        List<Book> books =  bookRepository.findTheMostBorrowedBooks(amount);
        Set<Map> result = new HashSet<>();
        for (Book b :
                books) {
            result.add(getBookInfo(b));
        }
        return result;
    }
}
