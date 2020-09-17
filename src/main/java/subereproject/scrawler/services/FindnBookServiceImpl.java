package subereproject.scrawler.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subereproject.scrawler.models.Book;
import subereproject.scrawler.models.BookCopies;
import subereproject.scrawler.models.PageList;

@Service
public class FindnBookServiceImpl implements FindnBookService {
	@Autowired
	private BookService bookService;
	@Autowired
	private CopiesService copiesService;
	private HashMap<String, String> error = new HashMap<String, String>();
	private List<Integer> newBooks = new ArrayList<Integer>();

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
			error.put(link, e.getMessage());
			return null;
		}
	}

	private boolean getDetails(String content, int id) {
		try {
			if (content != null) {
				Matcher mState = pStatus.matcher(content);
				if (!mState.find()) {
					String status = "Khóa";
					mState = pLock.matcher(content);
					mState.find();
					System.out.println("state: " + status);
					int total = Integer.parseInt(mState.group(2).split("&")[0]);
					int available = total - Integer.parseInt(mState.group(4).split("&")[0]);
					bookService.updateBook(available, total, status, id);
					bookService.findById(id);
					String contentCopy = getContentFrom(new PageList().getPage(3).getUrl() + id);
					getCollection(contentCopy, id);
				} else {
					String status = mState.group(2).matches("Rỗi") ? "Còn" : "Hết";
					Matcher mLib = status.matches("Còn") ? pAvai.matcher(content) : pUnavailable.matcher(content);
					Matcher mType = pType.matcher(content);
					Matcher mDes = pDescription.matcher(content);
					if (mLib.find()) {
						mType.find();
						mDes.find();
						String type = mType.group(2);
						int total = Integer.parseInt(mLib.group(2).split("&")[0]);
						int available = status.matches("Còn") ? Integer.parseInt(mLib.group(4).split("&")[0]) : 0;
						String title = mDes.group(2).split("/")[0];
						String author = mDes.group(2).split("/")[1].split(";")[0];
						String publisher = mDes.group(3);
						String no = mDes.group(4);
						bookService
								.save(new Book(id, title, author, type, publisher, no, available, total, status));
						newBooks.add(id);
						String contentCopy = getContentFrom(new PageList().getPage(3).getUrl() + id);
						getCollection(contentCopy, id);
					}
				}
			} else {
				return false;
			}
		} catch (Exception ex) {
			error.put(String.valueOf(id), ex.getMessage());
			return false;
		}
		return true;
	}

	private void getCollection(String content, int id) {
		Matcher mDetail = pDetail.matcher(content);
		String ecode = "";
		try {
			if (content != null) {

				while (mDetail.find()) {
					String store = mDetail.group(1);
					String code = mDetail.group(2);
					ecode = code;
					String status = mDetail.group(3);
					if (copiesService.existsByCode(code)) {
						copiesService.updateBookCopy(status, code);
					} else {
						copiesService.save(new BookCopies(code, status, store, bookService.findById(id).get()));
					}
				}
			}
		} catch (Exception ex) {
			error.put(ecode, ex.getMessage());
		}
	}

	private boolean scan(int id) {
		String contentBook = getContentFrom(new PageList().getPage(2).getUrl() + id);
		return getDetails(contentBook, id);
	}

	@Override
	public void run() {
		int fault = 0, index = 0, id = 0;
		List<Integer> bookID = bookService.findAllId();

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		while (fault < 5) {
			if (index != bookID.get(id)) {
				fault = scan(index) ? 0 : fault++;
				System.out.print("> " + index + " - " + bookID.get(id) + " ~ " + fault);
			} else {
				id++;
			}
			if(id == bookID.size()) {
				fault = 5;
			}
			index++;
		}
		executorService.shutdown();
		newBooks.forEach((book)->{
			System.out.println("New book: " + book);
		});
		error.forEach((target, ex) -> {
			System.out.println("Error> " + target + ": " + ex);
		});
	}
}
