package subereproject.scrawler.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import subereproject.scrawler.models.BookCopies;
import subereproject.scrawler.models.PageList;

@Service
public class UpdateBookDBServiceImpl implements UpdateBookDBService {

	@Autowired
	private BookService bookService;
	@Autowired
	private CopiesService copiesService;
	private HashMap<String, String> error = new HashMap<String, String>();

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

	private void getDetails(String content, int id) {
		try {
			if (content != null) {
				Matcher mState = pStatus.matcher(content);
				if (!mState.find()) {
					String status = "Khóa";
					mState = pNotAvailable.matcher(content);
					mState.find();
					int total = Integer.parseInt(mState.group(2).split("&")[0]);
					int available = total - Integer.parseInt(mState.group(4).split("&")[0]);
					bookService.updateBook(available, total, status, id);
					bookService.findById(id);
					String contentCopy = getContentFrom(new PageList().getPage(3).getUrl() + id);
					getCollection(contentCopy, id);
				} else {
					String status = mState.group(2).matches("Rỗi") ? "Còn" : "Hết";
					Matcher mLib = status.matches("Còn") ? pAvailable.matcher(content) : pNotAvailable.matcher(content);
					if (mLib.find()) {
						int total = Integer.parseInt(mLib.group(2).split("&")[0]);
						int available = status.matches("Còn") ? Integer.parseInt(mLib.group(4).split("&")[0]) : 0;
						bookService.updateBook(available, total, status, id);
						bookService.findById(id);
						String contentCopy = getContentFrom(new PageList().getPage(3).getUrl() + id);
						getCollection(contentCopy, id);
					}
				}
			}
		} catch (Exception ex) {
			error.put(String.valueOf(id), ex.getMessage());
		}
	}

	private void getCollection(String content, int id) {
		Matcher mDetail = pCopyDetail.matcher(content);
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

	private void scan(int id) {
		String contentBook = getContentFrom(new PageList().getPage(2).getUrl() + id);
		getDetails(contentBook, id);
	}

	@Override
	@Scheduled(cron = "0 0 8-16/2 * * Mon-Sat")
	public void updateBooks() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (Integer index : bookService.findAllId()) {
			scan(index);
		}
		executorService.shutdown();
		error.forEach((target, ex) -> {
			System.out.println("Error> " + target + ": " + ex);
		});
	}
}
