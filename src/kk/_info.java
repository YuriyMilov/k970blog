package kk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// GAE k970 admin at ddtor quick-0
// github.com/YuriyMilov
// sport.ddtor.com

public class _info {

	public static int max = 55000;
	public static String gm = "";
	public static String gm1 = "";
	public static String gm2 = "";
	public static String gm3 = "";
	public static String viz = "";
	public static String rss = "";

	public static void main(String[] args) throws Exception {

		String s = "http://pix.ddtor.com/fed?_info.gm3";
		s = st.rfu_utf(s);
		s = parsyand(s);
		System.out.println(s);
	}

	public static String parsyand(String s) {
		String s1 = "";
		String s2 = "";
		Elements qq = Jsoup.parse(
				Jsoup.parse(s).getElementsByTag("ol").first().html())
				.getElementsByTag("li");
		for (Element em : qq) {
			s1 = em.html();			
			Element doc2 = Jsoup.parse(s1).getElementsContainingOwnText("Сюжет полностью").first();			
			if(doc2!=null)
				s1 = s1.replace(doc2.html(), "");
			s2=s2.replace("<b>", "").replace("</b>", "").replace("<a href=", "<a target='_blank'  href=");
			
			s2 = s2 + "<div class='box col 3'><table><tr><td  width=333px>"+s1 + "</td></tr></table></div>\r\n";
			
			
			
					
		}
		return s2;
	}

	public static String get_all_new_rss() {

		String s = st.get_first(
				st.rfu_utf("http://www.ddtor.com/p/blog-page_21.html"),
				"<!-- qqq_rss1_qqq -->", "<!-- qqq_rss2_qqq -->").replace(
				"<br />", "");

		int i = s.indexOf("http");

		if (i < 0)
			return "";

		String[] sss = s.substring(i + 4).split("http");
		s = "";
		for (String ss : sss) {
			s = s + get_new_rss("http" + ss) + "\r\n";
		}
		return s;
	}

	public static String get_new_rss(String s) {
		String s3 = "", s4 = "", tit = "", dat = "", title = "", link = "", description = "";
		try {

			s = st.rfu_utf(s);

			int i = s.indexOf("<title>");
			if (i < 0)
				return "";
			tit = s.substring(i + 7);
			i = tit.indexOf("</title>");
			tit = tit.substring(0, i);

			i = s.indexOf("<item>");
			if (i < 0)
				return "";

			String[] sss = s.substring(i + 6).split("<item>");

			for (String s2 : sss) {

				int k = s2.indexOf("<pubDate>");
				if (k > -1)
					dat = s2.substring(k + 9);
				k = dat.indexOf("</pubDate>");
				if (k > -1)
					dat = dat.substring(0, k);

				boolean bb = st.date_old(dat);

				// System.out.println(bb);

				if (!bb) {
					if (s2.contains("<title>")) {
						i = s2.indexOf("<title>");
						title = s2.substring(i + 7);
					}

					if (title.contains("</title>")) {
						i = title.indexOf("</title>");
						title = title.substring(0, i);
					}

					// System.out.println(" --------->>>>      "+title + " ");

					if (s2.contains("<link>")) {
						i = s2.indexOf("<link>");
						link = s2.substring(i + 6);
						i = link.indexOf("</link>");
						link = link.substring(0, i);
					}

					if (s2.contains("<description>")) {
						i = s2.indexOf("<description>");
						description = s2.substring(i + 13);
						i = description.indexOf("</description>");
						description = description.substring(0, i);

						description = description.replace("<![CDATA[", "");
						description = description.replace("]]>", "");
						description = description.replace("&lt;", "<")
								.replace("&gt;", ">").replace("&quot;", "\"");

						description = Jsoup.parse(description).text();

						if (description.length() > 444)
							description = description.substring(0, 444) + "...";

						i = description.indexOf("Запись ");
						if (i > 0)
							description = description.substring(0, i) + "...";

					}

					s3 = "<div><table><tr><td valign='top'>"
							+ "<br/><a href='"
							+ link
							+ "' target='_blank'><img src='http://bb.ddtor.com/rss2.png' /></a></td>"
							+ "<td>&nbsp;</td>"
							+ "<td valign='top'><div style=\"color:#aaaaaa;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ "<i>"
							+ tit
							+ "</i><br/><a href='"
							+ link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:14px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title
							+ "</b></a></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ Jsoup.parse(description).text()
							+ "</div></td></tr></table></div><hr/>";

					// if(st.dupl(link))
					s4 = s4 + s3;

				}

			}

		} catch (Exception e) {
		}

		return s4;
	}
}
