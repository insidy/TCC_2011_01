package br.unisinos.swe.examples.workshop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class WorkshopMessage {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Key id;
	
	@Persistent
	public String canal;
	
	@Persistent
	public String nick;
	
	@Persistent
	public String msg;

	@Persistent
	public Date time;
	
	public WorkshopMessage(String canal, String nick, String msg) {
		this.canal = canal;
		this.nick = nick;
		this.msg = msg;
		this.time = Calendar.getInstance().getTime();
	}
	
	public String getHtml() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		sb.append("<span>");
		sb.append("Quest‹o \"");
		sb.append(this.msg);
		sb.append("\" enviada por ");
		sb.append(this.nick);
		sb.append(" em ");
		sb.append(this.canal);
		sb.append(" as ");
		sb.append(sdf.format(this.time));
		sb.append("</span><br/>");
		
		return sb.toString();
	}

}
