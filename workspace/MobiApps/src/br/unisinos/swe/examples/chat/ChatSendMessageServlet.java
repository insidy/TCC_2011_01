package br.unisinos.swe.examples.chat;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import br.unisinos.swe.examples.PMF;

@SuppressWarnings("serial")
public class ChatSendMessageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String sala = req.getParameter("sala");
		String nick = req.getParameter("nickname");
		String msg = req.getParameter("message");
		
		ChatMessage message = new ChatMessage(sala, nick, msg);
		
		try {
            pm.makePersistent(message);
        } finally {
            pm.close();
        }

		resp.setContentType("text/html");
		resp.getWriter().println(message.getHtml());
	}
}
