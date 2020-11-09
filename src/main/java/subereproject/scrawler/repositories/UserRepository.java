package subereproject.scrawler.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import subereproject.scrawler.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserNameOrEmail(String userName, String email); 
	
	boolean existsByEmail(String email);
	boolean existsByUserName(String userName); 
}
