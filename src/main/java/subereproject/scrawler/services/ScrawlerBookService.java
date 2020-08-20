package subereproject.scrawler.services;

import java.util.regex.Pattern;

import subereproject.scrawler.models.Book;

public interface ScrawlerBookService extends Runnable {
	// Pattern lọc dữ liệu
//	String code = "FUDN: GT/DNCN</B> [ Rỗi ] &nbsp;Tổng: 91;&nbsp;Rỗi: 36;&nbsp;Bận: 55&nbsp;";
	Pattern pLib1 = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Rỗi: (.*?);");
	Pattern pLib2 = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Bận: (.*?)&nbsp;");
	Pattern pType = Pattern.compile("Dạng tài liệu:(.*?)<TD>(.*?)</TD>");
	Pattern pState = Pattern.compile("FUDN:(.*?)\\[ (.*?) \\]");
	Pattern pDes = Pattern.compile(
			"<TD WIDTH='150px'><B>Thông tin mô tả:</B></TD> <TD><B>(.*?)</B><BR> (.*?)<BR> (.*?)<BR>(.*?)</B><BR><TR><TD>");

	void run();

	Book scan(int id);

	String getContentFrom(String link);
}
