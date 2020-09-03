package subereproject.scrawler.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subereproject.scrawler.models.Book;
import subereproject.scrawler.models.BookCopies;
import subereproject.scrawler.models.PageList;
import subereproject.scrawler.util.BooksIDUtil;

@Service
public class ScrawlerBookServiceImpl implements ScrawlerBookService {

	@Autowired
	private BookService bookService;
	@Autowired
	private CopiesService copiesService;

	private String getContentFrom(String link) {
		URL url;
		try {
			url = new URL(link);
			Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
			scanner.useDelimiter("\\\\Z");
			String content = scanner.next();
			scanner.close();
			content = content.replaceAll("\\\\R", "");
			return content;
		} catch (IOException e) {
			return null;
		}
	}

	private void getDetails(String content, int id) {
		try {
			if (content != null) {
				Matcher mState = pState.matcher(content);
				mState.find();
				String status = mState.group(2).matches("Rỗi") ? "Còn" : "Hết";
				Matcher mLib = status.matches("Còn") ? pLib1.matcher(content) : pLib2.matcher(content);
				Matcher mType = pType.matcher(content);
				Matcher mDes = pDes.matcher(content);
				if (mLib.find()) {
					mType.find();
					mDes.find();
					String type = mType.group(2);
					int total = Integer.parseInt(mLib.group(2));
					int available = status.matches("Còn") ? Integer.parseInt(mLib.group(4)) : 0;
					String title = mDes.group(2).split("/")[0];
					String author = mDes.group(2).split("/")[1].split(";")[0];
					String publisher = mDes.group(3);
					String no = mDes.group(4);

					Book book = new Book(id, title, author, type, publisher, no, available, total, status);
					System.out.println(book.getId());
					bookService.save(book);
					String contentCopy = getContentFrom(new PageList().getPage(3).getUrl() + id);
					getCollection(contentCopy, book);
				}
			}
		} catch (Exception ex) {
			System.out.println("Error" + ex);
		}

	}

	private void getCollection(String content, Book book) {
		try {
			if (content != null) {
				Matcher mDetail = pDetail.matcher(content);
				while (mDetail.find()) {

					String store = mDetail.group(1);
					String code = mDetail.group(2);
					String status = mDetail.group(3);
					BookCopies copy = new BookCopies(code, status, store, book);
					copiesService.save(copy);
				}
			}

		} catch (Exception ex) {
			System.out.println("Error" + ex);
		}

	}

	private void scan(int id) {
		String contentBook = getContentFrom(new PageList().getPage(2).getUrl() + id);
		getDetails(contentBook, id);

	}

	@Override
	public void run() {
		System.out.println("FUDN: ");
		BooksIDUtil books = new BooksIDUtil();
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for (Integer index : books.getBooks()) {
			scan(index);
		}
		executorService.shutdown();
	}

}
