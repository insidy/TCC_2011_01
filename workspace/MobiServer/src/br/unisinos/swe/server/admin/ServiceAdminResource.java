package br.unisinos.swe.server.admin;

import java.util.List;

import org.restlet.data.Form;
import org.restlet.representation.Representation;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.shared.ServiceBase;

public class ServiceAdminResource extends MobiServerResource {
	
	
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
					sObject = saveService(representation);
				} catch(Exception e) {
					
				}
			break;
			
			case delete:
				sObject = deleteService(representation);
				
			break;
				
			case list:
				sObject = getList();
				
			break;
		}
		
		return sObject;
	}
	
	private String deleteService(Representation representation) {
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
			wasDeleted = ServiceDatabaseFactory.getPersistance().delete(id);
		
		if(wasDeleted)
			anMessage = "Aplicativo deletado com sucesso";
		else
			anMessage = "Erro na exclus‹o do aplicativo";
			
		return anMessage;
	}
	
	private String saveService(Representation representation) {
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
			ServiceBase service = new ServiceBase(id, name, url);
			ServiceDatabaseFactory.getPersistance().save(service);
		}
		
		return "";
	}
	
	private String getList() {
		JSONObject rootObj = new JSONObject();
		JSONArray array = new JSONArray();
		
		try { 
			List<ServiceBase> services = ServiceDatabaseFactory.getPersistance().getList();
			for(ServiceBase service : services) {
				JSONArray dataArray = new JSONArray();
				dataArray.put(service.getId());
				dataArray.put(service.getName());
				dataArray.put(service.getServiceUrl());
				
				
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
