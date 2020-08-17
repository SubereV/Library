package subereproject.scrawler.entity;

import java.util.ArrayList;
import java.util.List;

public class PageList {
	
	private List<Page> pages = new ArrayList<Page>();
	
	public PageList() {
		pages.add(new Page("home", "http://libol.fpt.edu.vn/opac/"));
		pages.add(new Page("login", "http://libol.fpt.edu.vn/opac/WPersonalPageLogin.aspx"));
		pages.add(new Page("store", "http://libol.fpt.edu.vn/opac/WShowDetail.aspx?intItemID="));
		pages.add(new Page("bStatus", "http://libol.fpt.edu.vn/opac/WHoldingCollection.aspx?intItemID="));
	}
	
	public Page getPage(int index) {
		return pages.get(index);
	}

	public PageList(List<Page> pages) {
		super();
		this.pages = pages;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
}
