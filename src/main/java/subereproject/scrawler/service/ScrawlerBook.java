package subereproject.scrawler.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

import subereproject.scrawler.bean.AttributeSearch;
import subereproject.scrawler.entity.Book;
import subereproject.scrawler.entity.PageList;

public class ScrawlerBook extends Thread implements AttributeSearch {

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
			e.printStackTrace();
		}

		return null;
	}

	public Book scan(int id) {
		String content;
		if ((content = getContentFrom(new PageList().getPage(2).getUrl() + id)) != null) {
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
				return book;
			}
		}
		return null;
	}

}
