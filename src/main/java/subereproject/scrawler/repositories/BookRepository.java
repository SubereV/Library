package subereproject.scrawler.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import subereproject.scrawler.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{

}
