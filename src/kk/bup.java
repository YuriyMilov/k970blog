package kk;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class bup extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String sad=st.rfu_utf(sh + "/adv_pix.txt");		
		String s = "<link rel=\"stylesheet\" href=\"http://k970blog.appspot.com/css/style.css\" />"+ sad + _info.viz + sad ;
		st.send_mail("Администратор", "k970.quicklydone@gmail.com", "Админ",
				"k971.quicklydone.tverskoy@blogger.com",
				"на " + st.get_date_msk(), s);
		_info.viz="";
	}

	private static final long serialVersionUID = 1L;
}
