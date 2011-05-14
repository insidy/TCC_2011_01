package br.unisinos.swe.server.admin;

import java.util.List;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.shared.ChannelBase;

public class ChannelAdminResource extends MobiServerResource {
	
	
	public boolean allowPost() {
		return true;
	}
	
	protected String processRequestRaw(Representation representation) {
		return (String)processRequest(representation);
	}
	
	protected Object processRequest(Representation representation) {
		
		String sMethod = retrieveAttribute("method");
		String sObject = "";
		
		
		EMethod selectedMethod = EMethod.valueOf(sMethod);
		
		switch(selectedMethod) {
			case save:
				try {
					sObject = saveChannel(representation);
				} catch(Exception e) {
					
				}
			break;
			
			case delete:
				
			break;
				
			case list:
				sObject = getList();
				
			break;
		}
		
		return sObject;
	}
	
	private String saveChannel(Representation representation) {
		JSONObject obj = null;
		
		Form form = new Form(representation);
		
		long id = 0;
		try {
			if(form.getFirstValue("id") != null)
				id = Long.parseLong(form.getFirstValue("id"));
		} catch(Exception e) {
			
		}
		
		String name = form.getFirstValue("name");
		String url = form.getFirstValue("url");
		
		if(name != null && url != null) {
			ChannelBase channel = new ChannelBase(id, name, url);
			ChannelDatabaseFactory.getPersistance().save(channel);
		}
		
		return "";
	}
	
	private String getList() {
		JSONObject rootObj = new JSONObject();
		JSONArray array = new JSONArray();
		
		try { 
			List<ChannelBase> channels = ChannelDatabaseFactory.getPersistance().getList();
			for(ChannelBase channel : channels) {
				JSONArray dataArray = new JSONArray();
				dataArray.put(channel.getId());
				dataArray.put(channel.getName());
				dataArray.put(channel.getStreamUrl());
				
				
				array.put(dataArray);
			}
			
			rootObj.put("aaData", array);
		} catch(Exception e) {
			
		}
		
		
		
		return rootObj.toString();
	}
	
	private enum EMethod {
		save, delete, list;
	}

}
