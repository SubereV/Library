package subereproject.scrawler.services;

import java.util.List;
import java.util.Optional;

import subereproject.scrawler.models.Category;

public interface CategoryService {

	void deleteAll();

	void deleteAll(Iterable<? extends Category> entities);

	void delete(Category entity);

	void deleteById(Integer id);

	long count();

	List<Category> findAllById(List<Integer> ids);

	List<Category> findAll();

	boolean existsById(Integer id);

	Optional<Category> findById(Integer id);

	List<Category> saveAll(List<Category> entities);

	Category save(Category entity);

}
