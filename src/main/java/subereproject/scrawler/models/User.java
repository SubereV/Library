package subereproject.scrawler.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
	@Column(nullable = false)
	@Nationalized
	private String name; 
	
	@Column(nullable = false)
	@Nationalized
	private String userName; 
	
	@Column(unique = true)
	private String email; 
	
	@Column(nullable = false)
	private String password;
		
	private String mssv; 
	
	private String imageUrl; 
	
	@Column(nullable = false)
	private boolean emailVerified = false; 
	
	@Enumerated(EnumType.STRING)
	private AuthProvider provider; 
	
	private String providerId;
	
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private Role role; 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Favorite> favorites; 
}
