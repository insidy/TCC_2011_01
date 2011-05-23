package br.unisinos.swe.examples.workshop;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import br.unisinos.swe.examples.PMF;

@SuppressWarnings("serial")
public class WorkshopLoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String nick = req.getParameter("nickname");
		
		// do something?
        
        resp.setContentType("text/html");
		resp.getWriter().println("");
	}
	
}
