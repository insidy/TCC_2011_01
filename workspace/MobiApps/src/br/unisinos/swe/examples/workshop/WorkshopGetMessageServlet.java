package br.unisinos.swe.examples.workshop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.*;

import br.unisinos.swe.examples.PMF;

@SuppressWarnings("serial")
public class WorkshopGetMessageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		action(req, resp);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String canal = req.getParameter("canal");
		if(canal == null)
			canal = "";
		
		List<WorkshopMessage> messages = new ArrayList<WorkshopMessage>();
		

		try {
			Query query = pm.newQuery(WorkshopMessage.class);
			messages = (List<WorkshopMessage>)query.execute();
			
		} catch(Exception ex) {
			
		}
		
		StringBuilder strHtml = new StringBuilder();
		
		for(WorkshopMessage msg : messages) {
			if(canal.equals("")) { 
				strHtml.append(msg.getHtml());
			} else {
				if(canal.equals(msg.canal))
					strHtml.append(msg.getHtml());
			}
		}
		
		resp.setContentType("text/html");
		resp.getWriter().println(strHtml.toString());
	}
}
