package subereproject.scrawler.services;

import java.util.regex.Pattern;

public interface UpdateBookDBService extends Runnable {
	// Pattern lọc dữ liệu Book
	Pattern pAvailable = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Rỗi: (.*?);");
	Pattern pNotAvailable = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Bận: (.*?)&nbsp");
	
	Pattern pType = Pattern.compile("Dạng tài liệu:(.*?)<TD>(.*?)</TD>");
	Pattern pStatus = Pattern.compile("FUDN:(.*?)\\[ (.*?) \\]");
	Pattern pDescription = Pattern.compile(
			"<TD WIDTH='150px'><B>Thông tin mô tả:</B></TD> <TD><B>(.*?)</B><BR> (.*?)<BR> (.*?)<BR>(.*?)</B><BR><TR><TD>");
	// Pattern lọc dữ liệu bản Copy
	Pattern pCopyDetail = Pattern.compile("<td>FUDN</td><td>(.*?)</td><td align=\"Center\">(.*?)</td><td align=\"Center\"><img src='images/(.*?).gif'></td>");
	void run();
	
}
