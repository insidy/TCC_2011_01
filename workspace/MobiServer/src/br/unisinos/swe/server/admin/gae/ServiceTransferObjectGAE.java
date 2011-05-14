package br.unisinos.swe.server.admin.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import br.unisinos.swe.shared.ServiceBase;

@PersistenceCapable
public class ServiceTransferObjectGAE {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Long mId;
	
	@Persistent
	public String mServiceUrl;
	
	@Persistent
	public String mName;

	public ServiceTransferObjectGAE() {
		
	}
	
	public ServiceTransferObjectGAE(ServiceBase base) {
		this.fillFromBase(base);
	}
	
	public void fillFromBase(ServiceBase base) {
		if(base.getId() != 0)
			this.mId = base.getId();
		this.mServiceUrl = base.getServiceUrl();
		this.mName = base.getName();
	}
	
	public ServiceBase toBase() {
		return new ServiceBase(mId, mName, mServiceUrl);
	}
}
