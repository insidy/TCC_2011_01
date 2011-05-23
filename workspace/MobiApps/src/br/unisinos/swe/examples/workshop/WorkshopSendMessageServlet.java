package br.unisinos.swe.examples.workshop;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import br.unisinos.swe.examples.PMF;

@SuppressWarnings("serial")
public class WorkshopSendMessageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String canal = req.getParameter("canal");
		String nick = req.getParameter("nickname");
		String msg = req.getParameter("message");
		
		WorkshopMessage message = new WorkshopMessage(canal, nick, msg);
		
		try {
            pm.makePersistent(message);
        } finally {
            pm.close();
        }
        
        StringBuilder strTxt = new StringBuilder();
        strTxt.append("Mensagem enviada para o canal ");
        strTxt.append(canal);

		resp.setContentType("text/html");
		resp.getWriter().println(strTxt.toString());
	}
}
