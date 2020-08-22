package subereproject.scrawler.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import subereproject.scrawler.models.BookCopies;

@Repository
public interface CopiesRepository extends CrudRepository<BookCopies, String> {

}
