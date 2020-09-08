package subereproject.scrawler.repositories;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.hibernate.query.Query;
public class CustomizedCopiesRepositoryImpl implements CustomizedCopiesRepository{
	
	@Autowired
	private EntityManagerFactory entityManagerFactory; 

	@Override
	@Transactional
	public List<Integer> findTop7MostBorrowedBookId() {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class); 
		
		Query query = session.createQuery("Select c.book.id from BookCopies c where c.status='loan' group by c.book.id order by count(c.status) desc").setMaxResults(7);
		List<Integer> list = query.list(); 
		return list;
	}
	
}
