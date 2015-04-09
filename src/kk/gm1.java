package kk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.Base64Utils;

public class gm1 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = "";
		try {

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage ms1 = new MimeMessage(session, req.getInputStream());
			Object msgContent = ms1.getContent();

			if (msgContent instanceof Multipart) {
				Multipart multipart = (Multipart) msgContent;
				for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
						s = bodyPart.getContent().toString();
				}
			} else
				s = ms1.getContent().toString();

		s = parjs(s).replaceAll("\\s+", " ");
		if (s.trim().length() > 0) {	
			s="<div class=\"box col2\">"+s+"</div>";
			if (!_info.viz.contains(s))
				if (_info.gm1.length() < _info.max) {
					_info.gm1 = s + _info.gm1;
					_info.viz = s + _info.viz;
				}
				else {
					_info.gm1 = s;
					_info.viz = s + _info.viz;
				}
			
		}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public static String parjs(String s) {

		Document doc = Jsoup.parse(s);
		Element qq1 = doc.getElementsByTag("div").first();
		String s1 = qq1.html();
		
		Document doc2 = Jsoup.parse(s1);
		Element qq2 = doc2.getElementsByTag("table").last();
		String s2 = "";
		if(qq2!=null)
			s2=qq2.html();

		Document doc3 = Jsoup.parse(s2);
		Element qq3 = doc3.getElementsByTag("div").last();

		String s3 = "";
		if (qq3 != null) {
			s3 = qq3.html();
			
			if(s3.indexOf("<a ")>-1)
				{
				s3="<table><tr><td width=222px>"+s3+"</td></tr></table>";
				s3=s3.replace("<a href=", "<a target='_blank' href=");
				}
		}
		return s3;

	}
	
	public static String pars(String s) {

		String savtor = get_avtor(s);

		String slink = "<a href=\"https://plus.google.com";

		if (s.contains(slink)) {
			slink = s.substring(s.indexOf(slink));
			slink = slink.substring(0, slink.indexOf("</a>") + 4);
		} else
			slink = " slink ";

		s = s.replace("&lt;", "<").replace("&gt;", ">");

		s = rem_last_sub(s, "<div", "</div>");
		s = rem_last_sub(s, "<div", "</div>");
		s = rem_last_sub(s, "<!--", "-->");

		String s2[] = s.split("<td");
		int i = s2.length;
		if (i > 1)
			s = cut_first(s, "<td", "</td>");

		s = s.replace("alt=\"картинка не отображается\"", "");
		s = s.replace(">Перейти к записи</a>",
				" target=\"_blank\">Перейти к записи</a>");

		if (s.contains("поделился с вами записью из ленты пользователя"))
			s = cut_first(s, "<table", "</table>");

		if (!s.contains("Перейти к записи")) {
			if (slink.contains(" style=")) {
				slink = slink.substring(0, slink.indexOf(" style="));
				slink = slink
						+ " style=\"background-color:#d44b38;border:solid 1px #dfdfdf;border-radius:3px;color:#fff;display:inline-block;font-family: Arial;font-size:13px;height:30px;line-height:30px;min-width:54px;padding:1px 20px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">Посмотреть на Google+</a>";

			} else
				slink = "<a href=. style=\"background-color:#d44b38;border:solid 1px #dfdfdf;border-radius:3px;color:#fff;display:inline-block;font-family: Arial;font-size:13px;height:30px;line-height:30px;min-width:54px;padding:1px 20px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">Посмотреть в Google+</a>";

			s = s.replace("</table></div></div></div>", "</table>" + slink
					+ "</div></div></div>");
		}

		s = s.replace(
				"<div style=\"margin:20px 0;border-bottom:solid 1px #dfdfdf;width:670px\"></div>",
				"");
		s = s.replace("d44b38", "008DC9");
		s = s.replace("Перейти к записи", "Посмотреть на Google+");

		String im = "";
		i = s.lastIndexOf("<img");
		if (i > 0) {
			im = s.substring(i);

			i = im.indexOf(">");
			if (i > 0)
				im = im.substring(0, i);

			i = im.indexOf("style");
			if (i > 0) {
				im = im.substring(0, i);
				im = im + " width='90'; height='auto'/>";
			}
		}

		byte[] data = s.getBytes(Charset.forName("UTF-8"));
		String scoded = Base64Utils.toBase64(data);

		s = Jsoup.parse(s).text();

		s = rem_all_sub(s, "http", " ");
		s = rem_all_sub(s, "//", " ");
		s = rem_all_sub(s, "#", " ");
		s = s.replace("Перейти к записи", "");
		s = s.replace("Посмотреть на Google+", "");

		i = s.lastIndexOf("©");
		if (i > 0)
			s = s.substring(0, i);
		i = s.lastIndexOf("Вы получили это сообщение");
		if (i > 0)
			s = s.substring(0, i);

		i = s.lastIndexOf("©");
		if (i > 0)
			s = s.substring(0, i);
		i = s.lastIndexOf("Вы получили это оповещение");
		if (i > 0)
			s = s.substring(0, i);

		s = s.replace(savtor, "");
		s = s.replace("Пользователь", "");
		s = s.replace("поделился с вами записью", "");
		s = s.replace("из ленты пользователя", "");

		s = s.trim();
		i = s.indexOf(".");
		if (i == 0)
			s = s.substring(1).trim();

		s = s.trim();
		i = s.indexOf(":");
		if (i == 0)
			s = s.substring(1).trim();

		s = s.trim();
		i = s.lastIndexOf("...") + 3;
		if (i != s.length())
			s = s + "...";
		// s = " &nbsp; " + s;

		String sfid = "", s_button = "", s_end = "";

		if (s.trim().length() > 12) {
			sfid = String.valueOf(Math.random());

			// i=s.indexOf(".");
			// if(i>0){
			// s="<b>"+ s.substring(0,i) + "</b>."+s.substring(i+1);
			// }

			s_button = "<a href=\"javascript: document.getElementById('myform"
					+ sfid
					+ "').submit();\" style=\"background-color:#eeeeee;border:solid 1px #dfdfdf;border-radius:3px;color:#0044bb;display:inline-block;font-family: Arial;font-size:12px;height:13px;min-width:15px;padding:2px 2px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">"
					+ " <b>G+ " + savtor + "</b> &nbsp; </a><br/><br/>&nbsp;&nbsp;&nbsp; ";

			s_end = "<form id=\"myform"
					+ sfid
					+ "\" action=\"http://bb.ddtor.com/mmlink2\" method=\"post\"  target=\"_blank\">"
					+ "<input type=hidden value=\"" + scoded
					+ "\" name=data></form> ";

			s = "<div><table><tr valign='top'><td valign='top'><a href=\"javascript: document.getElementById('myform"
					+ sfid
					+ "').submit();\" target=\"_blank\">"+im+ "</a></td><td>&nbsp;</td><td style='color:#444444;font-family: Arial;font-size:12px;'  valign='top'>"+s_button  + s + s_end + "</td></tr></table></div><hr/>";// s_button +
		} else
			s = "";

		return s;

	}

	public static String rem_first_sub(String s, String s1, String s2) {
		int i = s.indexOf(s1);
		String s3 = "";
		if (i > -1) {
			if (i > 0) {
				s3 = s.substring(0, i);
				s = s.substring(i);
			}
			String[] sss = s.split(s1);
			s = "";
			for (String ss : sss) {
				i = ss.indexOf(s2);
				if (i > -1)
					s = s + ss.substring(i + s2.length());
				break;
			}
		}
		return s3 + s;
	}

	public static String rem_last_sub(String s, String s1, String s2) {
		String s3 = "";

		int i = s.lastIndexOf(s1);
		int k = s.lastIndexOf(s2);
		if (i > -1 && k > i) {
			if (i > 0) {
				s3 = s.substring(0, i);
				s = s.substring(i);
			}

			i = s.indexOf(s2);
			if (i > -1)
				s = s.substring(i + s2.length());

		}

		return s3 + s;
	}

	public static String rem_all_sub(String s, String s1, String s2) {
		int i = s.indexOf(s1);
		String s3 = "";
		if (i > -1) {
			if (i > 0) {
				s3 = s.substring(0, i);
				s = s.substring(i);
			}
			String[] sss = s.split(s1);
			s = "";
			for (String ss : sss) {
				i = ss.indexOf(s2);
				if (i > -1)
					s = s + ss.substring(i + s2.length());
			}
		}
		return s3 + s;
	}

	public static String cut_first(String s, String s1, String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);
		int i2 = s1.indexOf(s2) + s2.length();
		if (i1 > 0 && i1 < s.length() && i2 > 0 && i2 < s1.length())
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}

	public static String cut_last(String s, String s1, String s2) {
		int i1 = s.lastIndexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);

		int i2 = s1.lastIndexOf(s2) + s2.length();
		if (i1 > 0 && i1 < s.length() && i2 > 0 && i2 < s1.length())
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}

	public static String get_first(String s, String s1, String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s = s.substring(i1);

		int i2 = s.indexOf(s2) + s2.length();
		if (i2 > 0)
			s = s.substring(0, i2);

		s = Jsoup.parse(s).text();

		return s;
	}

	public static String get_avtor(String s1) {

		String s3 = Jsoup.parse(s1).text();
		int k = s3.indexOf("поделился"), j = s3.indexOf("поделилась");

		if (k > 0 || j > 0) {
			if (k > 0)
				s3 = s3.substring(0, k).trim();

			j = s3.indexOf("поделилась");
			if (j > 0)
				s3 = s3.substring(0, j).trim();

			// k = s3.indexOf(".");

			// if (k > 0)
			// s3 = s3.substring(0, k).trim();

			s3 = s3.replace("Пользователь", "").trim();
		} else
			s3 = "";
		return s3;
	}

	private static final long serialVersionUID = 1L;

}