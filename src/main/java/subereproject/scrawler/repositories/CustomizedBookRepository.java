package subereproject.scrawler.repositories;

import subereproject.scrawler.models.Book;

import java.util.List;

public interface CustomizedBookRepository {
    void indexBooks() throws Exception;

    List<Book> searchBookByKeyword(String keyword);
}
