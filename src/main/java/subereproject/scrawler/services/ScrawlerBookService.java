package subereproject.scrawler.services;

import java.util.regex.Pattern;

public interface ScrawlerBookService extends Runnable {
	// Pattern lọc dữ liệu Book
//	String code = "FUDN: GT/DNCN</B> [ Rỗi ] &nbsp;Tổng: 91;&nbsp;Rỗi: 36;&nbsp;Bận: 55&nbsp;";
	Pattern pLib1 = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Rỗi: (.*?);");
	Pattern pLib2 = Pattern.compile("FUDN:(.*?)Tổng: (.*?);(.*?)Bận: (.*?)&nbsp;");
	Pattern pType = Pattern.compile("Dạng tài liệu:(.*?)<TD>(.*?)</TD>");
	Pattern pState = Pattern.compile("FUDN:(.*?)\\[ (.*?) \\]");
	Pattern pDes = Pattern.compile(
			"<TD WIDTH='150px'><B>Thông tin mô tả:</B></TD> <TD><B>(.*?)</B><BR> (.*?)<BR> (.*?)<BR>(.*?)</B><BR><TR><TD>");
	// Pattern lọc dữ liệu bản Copy
	Pattern pDetail = Pattern.compile("<td>FUDN</td><td>(.*?)</td><td align=\"Center\">(.*?)</td><td align=\"Center\"><img src='images/(.*?).gif'></td>");
	void run();
	
}
