package br.unisinos.swe.shared;

import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class ServiceBase implements ISerializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5186666512765511406L;
	
	@TransferParameter("id") protected int mId;
	@TransferParameter("url") protected String mServiceUrl;
	@TransferParameter("name") protected String mName;
	
	public ServiceBase() {
		
	}
	
	public ServiceBase(int id, String name, String serviceUrl) {
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
	
	public int getId() {
		return this.mId;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
