package subereproject.scrawler.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subereproject.scrawler.models.BookCopies;
import subereproject.scrawler.repositories.CopiesRepository;

@Service
public class CopiesServiceImpl implements CopiesService{
	
	@Autowired
	private CopiesRepository copiesRepository;

	@Override
	public BookCopies save(BookCopies entity) {
		return copiesRepository.save(entity);
	}

	@Override
	public List<BookCopies> saveAll(List<BookCopies> entities) {
		return (List<BookCopies>) copiesRepository.saveAll(entities);
	}

	@Override
	public Optional<BookCopies> findById(String id) {
		return copiesRepository.findById(id);
	}

	@Override
	public boolean existsById(String id) {
		return copiesRepository.existsById(id);
	}

	@Override
	public List<BookCopies> findAll() {
		return (List<BookCopies>) copiesRepository.findAll();
	}

	@Override
	public List<BookCopies> findAllById(Iterable<String> ids) {
		return (List<BookCopies>) copiesRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return copiesRepository.count();
	}

	@Override
	public void deleteById(String id) {
		copiesRepository.deleteById(id);
	}

	@Override
	public void delete(BookCopies entity) {
		copiesRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<BookCopies> entities) {
		copiesRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		copiesRepository.deleteAll();
	}
	
	
}
