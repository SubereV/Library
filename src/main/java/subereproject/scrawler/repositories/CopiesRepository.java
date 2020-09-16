package subereproject.scrawler.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import subereproject.scrawler.models.BookCopies;

@Repository
public interface CopiesRepository extends CrudRepository<BookCopies, String>, CustomizedCopiesRepository {
	@Transactional
	@Modifying
	@Query("Update BookCopies c set c.status = :newStatus where c.code = :code")
	void updateBookCopy(@Param("newStatus") String status, @Param("code") String code);
	
	boolean existsByCode(String code);
}
