package kk;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;


public class bup extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s="";
		
	if(_info.viz.length()>99000)			
			s=_info.viz.substring(33000);
			
	int i=s.lastIndexOf("<div class=\"box");
	
	if(i>0)
		{
		s=_info.viz.substring(i);
		_info.viz=_info.viz.substring(0,i);
		}
	
		st.send_mail("Администратор", "k970.quicklydone@gmail.com", "Админ", "k970.quicklydone.tverskoy970@blogger.com", "на "+ st.get_date_msk(),s );
		
		
	
		
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath();
			resp.sendRedirect(sh+"/fed");
	}
	private static final long serialVersionUID = 1L;
}

