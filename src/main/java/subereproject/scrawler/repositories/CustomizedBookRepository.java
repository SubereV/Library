package subereproject.scrawler.repositories;

import java.util.List;

import subereproject.scrawler.models.Book;

public interface CustomizedBookRepository {

	List<Book> findBookByKeywords(String keywords);

	void indexBooks() throws Exception;
 
}
