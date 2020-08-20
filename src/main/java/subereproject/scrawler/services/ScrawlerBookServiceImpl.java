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
import subereproject.scrawler.models.PageList;
import subereproject.scrawler.util.BooksIDUtil;

@Service
public class ScrawlerBookServiceImpl implements ScrawlerBookService {

	@Autowired
	private BookService bookService;

	@Override
	public String getContentFrom(String link) {
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

	@Override
	public Book scan(int id) {
		String content = getContentFrom(new PageList().getPage(2).getUrl() + id);

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
					String author = mDes.group(2).split("/")[1];
					String publisher = mDes.group(3);
					String no = mDes.group(4);

					Book book = new Book(id, title, author, type, publisher, no, available, total, status);
					System.out.println(book.getId());
					bookService.save(book);
					return book;
				}
			}

		} catch (Exception ex) {
			return null;
		}
		return null;
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
