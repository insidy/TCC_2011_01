package br.unisinos.swe.shared;

import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class ServiceBase implements ISerializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5186666512765511406L;
	
	@TransferParameter("id") protected long mId;
	@TransferParameter("url") protected String mServiceUrl;
	@TransferParameter("name") protected String mName;
	
	public ServiceBase() {
		
	}
	
	public ServiceBase(long id, String name, String serviceUrl) {
		this.mId = id;
		this.mName = name;
		this.mServiceUrl = serviceUrl;
	}
	
	public String getServiceUrl() {
		return this.mServiceUrl;
	}
	
	public String getName() {
		return this.mName;
	}
	
	public long getId() {
		return this.mId;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) 
	    	return false;
	    if (other == this) 
	    	return true;
	    if (this.getClass() != other.getClass())
	    	return false;
	    
	    ServiceBase compare = (ServiceBase)other;
	    if(compare.getId() == this.getId())
	    	return true;
	    else
	    	return false;
	}

}
