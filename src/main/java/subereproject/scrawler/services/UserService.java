package subereproject.scrawler.services;

import java.util.List;
import java.util.Optional;

import subereproject.scrawler.models.User;

public interface UserService {

	void deleteAll();

	void deleteAll(List<User> entities);

	void delete(User entity);

	void deleteById(Long id);

	long count();

	Iterable<User> findAllById(Iterable<Long> ids);

	Iterable<User> findAll();

	boolean existsById(Long id);

	Optional<User> findById(Long id);

	List<User> saveAll(List<User> entities);

	User save(User entity);

	boolean existsByEmail(String email);

	boolean existByUserName(String userName);

	Optional<User> findByUserNameOrEmail(String userName, String email);

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);


}
