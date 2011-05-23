package br.unisinos.swe.examples.chat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.*;

import br.unisinos.swe.examples.PMF;



@SuppressWarnings("serial")
public class ChatGetMessageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ChatMessage> messages = new ArrayList<ChatMessage>();
		
		String sala = req.getParameter("sala");
		String nick = req.getParameter("nickname");
		Long tick = Long.parseLong(req.getParameter("tick"));
		
		try {
			Query query = pm.newQuery(ChatMessage.class, "sala == strSala && time >= lngTick");
			query.declareParameters("String strSala, Long lngTick");
			messages = (List<ChatMessage>)query.execute(sala, tick);
			
		} catch(Exception ex) {
			
		}
		
		StringBuilder strHtml = new StringBuilder();
		
		for(ChatMessage msg : messages) {
			if(!msg.nick.equals(nick)) {
				strHtml.append(msg.getHtml());
			}
		}
		
		resp.setContentType("text/html");
		resp.getWriter().println(strHtml.toString());
		
	}
}
