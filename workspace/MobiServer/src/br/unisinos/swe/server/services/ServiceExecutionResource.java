package br.unisinos.swe.server.services;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;  
import org.restlet.resource.ServerResource;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.shared.msg.GetChannelsMsg;


public class ServiceExecutionResource extends MobiServerResource {

	@Override
	protected String processRequestRaw(Representation representation) {
		String selectedService, selectedMethod;
		Object service = getRequestAttributes().get("service");
		Object method = getRequestAttributes().get("action");
		
		selectedService = (service != null ? service.toString() : "");
		selectedMethod = (method != null ? method.toString() : "");
		
		// TODO find service
		
		// TODO if any method selected, execute the method 
		
		// TODO retrieve device data
		
		// TODO execute sevice
		
        return  "Hello world";
	}

	@Override
	protected Object processRequest(Representation representation) {
		// TODO Auto-generated method stub
		return null;
	}

}
