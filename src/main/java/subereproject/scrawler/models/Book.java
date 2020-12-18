package subereproject.scrawler.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Nationalized;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;
	@OneToMany(mappedBy = "book")
	private List<BookCopies> copies;
	@ManyToOne
	@JoinColumn(name = "cateid")
	private Category category;

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
