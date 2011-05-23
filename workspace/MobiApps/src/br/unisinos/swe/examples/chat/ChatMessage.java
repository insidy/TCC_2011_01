package br.unisinos.swe.examples.chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class ChatMessage {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Key id;
	
	@Persistent
	public String sala;
	
	@Persistent
	public String nick;
	
	@Persistent
	public String msg;
	
	@Persistent
	public boolean system;
	
	@Persistent
	public Long time;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String sala, String nick, String msg) {
		initiate(sala, nick, msg, false);
	}
	
	public ChatMessage(String sala, String nick, String msg, boolean sysMsg) {
		initiate(sala, nick, msg, sysMsg);
	}
	
	public void initiate(String sala, String nick, String msg, boolean sysMsg) {
		this.sala = sala;
		this.nick = nick;
		this.msg = msg;
		this.system = sysMsg;
		this.time = Calendar.getInstance().getTimeInMillis();
	}
	
	public String getHtml() {
		StringBuilder strHtml = new StringBuilder();
		
		if(!this.system) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date dt = new Date(this.time);
			strHtml.append("<font color=\"blue\">");
			strHtml.append("[");
			strHtml.append(sdf.format(dt));
			strHtml.append("]&nbsp;");
			strHtml.append("&lt;");
			strHtml.append(this.nick);
			strHtml.append("&gt;&nbsp;&nbsp;");
			strHtml.append(this.msg);
			strHtml.append("</font>");
			strHtml.append("<br/>");
			
		} else {
			strHtml.append("<font color=\"red\">");
			strHtml.append(this.msg);
			strHtml.append("</font><br/>");
		}
		return strHtml.toString();
		
	}
	
	
}
