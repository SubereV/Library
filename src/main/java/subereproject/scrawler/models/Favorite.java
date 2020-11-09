package subereproject.scrawler.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;
import lombok.Data;

@Entity
@Table(name = "favorites")
@Data
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column(unique = true, nullable = false)
	@Nationalized
	private String name; 
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user; 
	
	@ManyToMany(mappedBy = "favorites")
	private Set<Book> books; 
}
