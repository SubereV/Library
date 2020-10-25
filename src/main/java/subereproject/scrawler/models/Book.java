package subereproject.scrawler.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
public class Book {
	@Id
	private int id;
	
	@Nationalized
	private String title;
	
	@Nationalized
	private String author;
	
	@Nationalized
	private String type;
	
	@Nationalized
	private String publisher;
	
	@Nationalized
	private String no;
	
	private int available;
	private int total;
	
	@Nationalized
	private String status;
	
	@Column(columnDefinition = "Text")
	private String description;
	
	@OneToMany(mappedBy = "book")
	private List<BookCopies> copies;
	
	@ManyToOne
	@JoinColumn(name = "cateID")
	private Category category;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "book_favorite", 
			joinColumns = @JoinColumn(name = "book_id"), 
			inverseJoinColumns = @JoinColumn(name = "favorite_id"))
	private Set<Favorite> favorites; 
	
	public Book(int id, String title, String author, String type, String publisher, String no, int available, int total,
			String status) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.type = type;
		this.publisher = publisher;
		this.no = no;
		this.available = available;
		this.total = total;
		this.status = status;
	}

	@Override
	public String toString() {
		return "\nTiêu đề: " + title + "\nTác giả: " + author + "\nDạng tài liệu: " + type + "\nNXB: " + publisher
				+ "\nSố trang: " + no + "\n====\nTrạng thái: " + status + "\nTổng số bản: " + total + "\nSẵn có: "
				+ available + "\nĐã mượn: " + (total - available);
	}

}
