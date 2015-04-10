package kk;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class fed extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String sposled = "init";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s = req.getQueryString();

		if (s == null)
			s = stmpl.replace("qqq_sfilename_qqq", "new.txt").replace(
					"qqq_sdata_qqq", "");
		else if (s.trim().length() == 0)
			s = stmpl.replace("qqq_sfilename_qqq", "nov_rss").replace(
					"qqq_sdata_qqq", st.fir("nov_rss"));

		else if (s.equals("rss_add")) {
			s = _info.get_all_new_rss();
			_info.rss = s + _info.rss;
		} else if (s.equals("bup"))
			s = st.rfu_utf(sh + "/bup");
		else if (s.equals("_info.gm"))
			s = _info.gm;
		else if (s.equals("_info.gm1"))
			s = _info.gm1;
		else if (s.equals("_info.gm2"))
			s = _info.gm2;
		else if (s.equals("_info.gm3"))			
			s=st.rfu_utf(sh+"/adv_sport.txt")+ _info.gm3;			
		else if (s.equals("_info.viz"))
			s = _info.viz;

		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String s = "";
		String s_text = req.getParameter("text");
		String s_name = req.getParameter("name");
		String s_hidden = req.getParameter("hidden");

		if (s_hidden != null && s_text != null && s_name != null) {
			if (s_hidden.contains("удалить")) {
				st.send_mail("Админ", "admin@ddtor.com", "",
						"ymilov@gmail.com",
						"запрос удалить RSS " + st.get_date_msk(), s_name
								+ " <br/>\r\n " + s_text);
				s = "спасибо";
			} else {
				if (s_hidden.contains("file")) {
					st.send_mail("Админ", "admin@ddtor.com", "",
							"ymilov@gmail.com",
							"запись в файл  " + st.get_date_msk(), s_name
									+ " <br/>\r\n " + s_text);
					st.fiw(s_name, s_text);
					s = stmpl.replace("qqq_sfilename_qqq", s_name).replace(
							"qqq_sdata_qqq", st.fir(s_name));
				}
			}
		}

		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	String stmpl = "<!DOCTYPE html><html><head><meta content='text/html; charset=UTF-8' "
			+ "http-equiv='Content-Type' /></head><title>Fed</title>"
			+ "<body>"
			+ "<a href='bup'> bup </a>"
			+ "&nbsp;&nbsp;&nbsp;"
			+ "<a href='fed?_info.gm'> _info.gm </a> "
			+ "&nbsp;&nbsp;&nbsp;	"
			+ "<a href='fed?_info.gm1'> _info.gm1 </a> "
			+ "&nbsp;&nbsp;&nbsp;	"
			+ "<a href='fed?_info.gm2'> _info.gm2 </a> "
			+ "&nbsp;&nbsp;&nbsp;	"
			+ "<a href='fed?_info.gm3'> _info.gm3 </a> "
			+ "&nbsp;&nbsp;&nbsp;	"
			+ "<a href='fed?_info.viz'> _info.viz </a> "
			+ "&nbsp;&nbsp;&nbsp; "
			+ "<a href='http://www.ddtor.com/p/blog-page_21.html'>"
			+ " R_S_S   </a>"
			+ "&nbsp;&nbsp;&nbsp;"
			+ "<br /><br />"
			+ "<form action='fed' method='post'>"
			+ "<input type='text' name='name' value='qqq_sfilename_qqq'> <br /><br />"
			+ "<textarea name='text' cols='55' rows='3'>qqq_sdata_qqq</textarea><br />"
			+ "<br /> <input type='hidden' name='hidden' value='file'><input type=submit value='fed'>"
			+ "</form>" + "</body></html>";

}