package subereproject.scrawler.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Collections")
public class BookCopies implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 13)
	private String code;
	private String status;
	@Column(length = 7)
	private String store;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	@JsonIgnore
	private Book book;

	public BookCopies(String code, String status, String store, Book book) {
		super();
		this.code = code;
		this.status = status;
		this.store = store;
		this.book = book;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BookCopies() {
		// TODO Auto-generated constructor stub
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
