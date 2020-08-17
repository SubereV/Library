package subereproject.scrawler.service;

import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import subereproject.scrawler.entity.PageList;

public class ScrawlerBook {

	private static String getContentFrom(String link) throws IOException {
		URL url = new URL(link);
		Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
		scanner.useDelimiter("\\\\Z");
		String content = scanner.next();
		scanner.close();
		content = content.replaceAll("\\\\R", "");
		return content;
	}

	private static Book scan() {
		PageList pages = new PageList();
		String content;
		try {
			content = getContentFrom(pages.getPage(2).getUrl() + 9880);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Pattern pLib = Pattern.compile("FUDN: GT/DNCN(.*?)Tổng:(.*?);(.*?)Rỗi:(.*?);");
		Pattern pType = Pattern.compile("Dạng tài liệu:</B></TD><TD>(.*?)</TD>");
		Pattern pNumber = Pattern.compile("<b>(.*?)</b>");
		Pattern pDes = Pattern.compile(
				"<TD WIDTH='150px'><B>Thông tin mô tả:</B></TD> <TD><B>(.*?)</B><BR> (.*?)<BR> (.*?)<BR>(.*?)</B><BR><TR><TD>");
		Matcher mLib = pLib.matcher(content);
		Matcher mType = pType.matcher(content);
		Matcher mNum = pNumber.matcher(content);
		Matcher mDes = pDes.matcher(content);
		mType.find();
		String type = mType.group(1);
		mLib.find();
		String total = mLib.group(2);
		String available = mLib.group(4);
		mDes.find();

		String title = mDes.group(2).split("/")[0];
		String author = mDes.group(2).split("/")[1];
		String publish = mDes.group(3);
		String shape = mDes.group(4);

		System.out.println("Dạng tài liệu: " + type + "\n" + "Tác giả: " + author + "\n" + "Tiêu đề: " + title + "\n"
				+ "NXB: " + publish + "\n" + "Số trang: " + shape);
		System.out.println("Tổng số bản: " + total + "\n" + "Số bản rỗi: " + available);
//		System.out.println("========================================");
//		System.out.println(content);

		return new Book();
	}

	public static void main(String[] args) throws IOException {
		scan();
//		System.out.println(content);
	}
}
