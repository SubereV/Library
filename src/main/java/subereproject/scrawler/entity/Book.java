package subereproject.scrawler.entity;

public class Book extends Page {

	private int id;
	private String type;
	private String author;
	private int available;

	public Book(String name, String url, int id, String type, String author, int available) {
		super(name, url);
		this.id = id;
		this.type = type;
		this.author = author;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

}
