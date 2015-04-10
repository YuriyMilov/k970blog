package kk;

import java.io.IOException;
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

@SuppressWarnings("serial")
public class gm extends HttpServlet {

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

			s = st.parjs(s).replaceAll("\\s+", " ");
			//System.err.println("-- gm --> " + s);
			
			if (s.trim().length() > 0) {
				s = "<div class=\"box col2\">" + s + "</div>";
				if (!_info.viz.contains(s))
					if (_info.gm.length() < _info.max) {
						_info.gm = s + _info.gm;
						_info.viz = s + _info.viz;
					} else {
						_info.gm = s;
						_info.viz = s + _info.viz;
					}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req,resp);
	}

}