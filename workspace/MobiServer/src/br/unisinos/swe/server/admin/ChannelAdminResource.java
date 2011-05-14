package br.unisinos.swe.server.admin;

import java.util.List;

import org.restlet.data.Form;
import org.restlet.representation.Representation;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.ServiceBase;

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
				sObject = deleteChannel(representation);
				break;
				
			case list:
				sObject = getList();
				break;
			
			case apps:
				sObject = getAvailableApps(representation);
				break;
				
			case apps_save:
				sObject = saveApps(representation);
				break;
			
		}
		
		return sObject;
	}

	private String saveChannel(Representation representation) {
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
	
	private String getAvailableApps(Representation representation) {
		JSONArray array = new JSONArray();
		Form form = new Form(representation);
		
		try {
			long id = 0;
			
			if(form.getFirstValue("id") != null)
				id = Long.parseLong(form.getFirstValue("id"));
			
			if(id != 0) {
				
				ChannelBase selected = ChannelDatabaseFactory.getPersistance().getSingle(id);
				for(ServiceBase service : selected.getServices()) {
					JSONArray dataArray = new JSONArray();
					dataArray.put(service.getId());
					dataArray.put(service.getName());
					dataArray.put(service.getServiceUrl());
					dataArray.put(true);
					array.put(dataArray);
				}
				
				List<ServiceBase> allServices = ServiceDatabaseFactory.getPersistance().getList();
				for(ServiceBase service : allServices) {
					if(!selected.getServices().contains(service)) {
						JSONArray dataArray = new JSONArray();
						dataArray.put(service.getId());
						dataArray.put(service.getName());
						dataArray.put(service.getServiceUrl());
						dataArray.put(false);
						array.put(dataArray);
					}
				}
			}
			
		} catch(Exception e) {
			
		}
		
		return array.toString();
	}
	
	private String saveApps(Representation representation) {
		JSONArray array = new JSONArray();
		Form form = new Form(representation);
		
		try {
			long id = 0;
			
			if(form.getFirstValue("id") != null)
				id = Long.parseLong(form.getFirstValue("id"));
			
			if(id != 0) {
				String[] apps = form.getValuesArray("list[]");
				ChannelDatabaseFactory.getPersistance().linkApps(id, apps);
			}
			
		} catch(Exception e) {
			
		}
		
		return "";
	}
	
	private String deleteChannel(Representation representation) {
		Form form = new Form(representation);
		
		long id = 0;
		boolean wasDeleted = false;
		String anMessage = "";
		
		try {
			if(form.getFirstValue("id") != null)
				id = Long.parseLong(form.getFirstValue("id"));
		} catch(Exception e) {
			
		}
		
		if(id != 0)
			wasDeleted = ChannelDatabaseFactory.getPersistance().delete(id);
		
		if(wasDeleted)
			anMessage = "Canal deletado com sucesso";
		else
			anMessage = "Erro na exclus‹o do canal";
			
		return anMessage;
	}
	
	private enum EMethod {
		save, delete, list, apps, apps_save;
	}

}
