package subereproject.scrawler.models;

import java.util.List;

/**
 * @author kinvo
 *
 */
public class CategoryList {
	private List<Category> categories;

	public CategoryList() {
		// TODO Auto-generated constructor stub
	}

	public CategoryList(List<Category> categories) {
		super();
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
