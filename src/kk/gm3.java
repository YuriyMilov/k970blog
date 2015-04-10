package kk;

import java.io.IOException;
import java.nio.charset.Charset;
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
import org.jsoup.select.Elements;

import com.google.gwt.user.server.Base64Utils;

public class gm3 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
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
			
			//System.err.println("-- gm3 --> " + s);
			
			st.send_mail("Администратор", "k970.quicklydone@gmail.com",
					"Админ", "k9731.tverskoy@blogger.com",
					"на " + st.get_date_msk(), parsyand(s, sh));
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public static String parsyand(String s, String sh) {
		String s1 = "", s2 = "";

		Elements qq = Jsoup.parse(
				Jsoup.parse(s).getElementsByTag("ol").first().html())
				.getElementsByTag("li");
		for (Element em : qq) {
			s1 = em.html();
			Element doc2 = Jsoup.parse(s1)
					.getElementsContainingOwnText("Сюжет полностью").first();
			if (doc2 != null)
				s1 = s1.replace(doc2.html(), "");
			s2 = s2.replace("<b>", "").replace("</b>", "")
					.replace("<a href=", "<a target='_blank'  href=");
			s2 = s2 + "<hr/><div class='box col 3'><table><tr><td>" + s1
					+ "</td></tr></table></div>\r\n";
		}
		s1 = st.rfu_utf(sh + "/adv_sport.txt");
		return s1 + s2 + s1;
	}

	private static final long serialVersionUID = 1L;

}