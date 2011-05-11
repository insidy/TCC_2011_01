package br.unisinos.swe.server;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import br.unisinos.swe.shared.serialization.MobiSerialization;

public abstract class MobiServerResource extends ServerResource {
	protected String retrieveAttribute(String string) {
		Object obj = getRequestAttributes().get(string);
		if(obj != null)
			return obj.toString();
		return "";
	}
	
	@Post
	public String receivePost(Representation representation) {
        return processRequestRaw(representation);
	}
	
	@Get  
    public String receiveGet() {
        return processRequestRaw(null);
    }
	
	protected abstract Object processRequest(Representation representation);
	
	protected String processRequestRaw(Representation representation) {
		return doEncoding(processRequest(representation));
	}
	
	public String doEncoding(Object object) {
		return new MobiSerialization().encode(object);
	}
	
}
