package subereproject.scrawler.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Books")
@Indexed
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	private int id;
	@Nationalized
	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	private String title;
	@Nationalized
	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
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
	@OneToMany(mappedBy = "book")
	private List<BookCopies> copies;

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
