package subereproject.scrawler.services;

import java.util.regex.Pattern;

public interface FindnBookService {
	// Pattern lọc dữ liệu Book
	Pattern pAvai = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Rỗi: (.*?);");
	Pattern pUnavailable = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Bận: (.*?)&nbsp;");
	Pattern pLock = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Bận: (.*?)&nbsp");
	Pattern pType = Pattern.compile("Dạng tài liệu:(.*?)<TD>(.*?)</TD>");
	Pattern pStatus = Pattern.compile("FUDN:(.*?)\\[ (.*?) \\]");
	Pattern pDescription = Pattern.compile("<TD WIDTH='150px'><B>Thông tin mô tả:</B></TD> <TD><B>(.*?)</B><BR> (.*?)<BR> (.*?)<BR>(.*?)</B><BR><TR><TD>");
	// Pattern lọc dữ liệu bản Copy
	Pattern pDetail = Pattern.compile("<td>FUDN</td><td>(.*?)</td><td align=\"Center\">(.*?)</td><td align=\"Center\"><img src='images/(.*?).gif'></td>");

	void run();
}
