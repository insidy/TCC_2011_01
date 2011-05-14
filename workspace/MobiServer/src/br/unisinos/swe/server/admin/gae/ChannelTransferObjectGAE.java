package br.unisinos.swe.server.admin.gae;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import br.unisinos.swe.server.admin.ServiceDatabaseFactory;
import br.unisinos.swe.shared.ChannelBase;

@PersistenceCapable
public class ChannelTransferObjectGAE {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Long mId;
	
	@Persistent
	public String mStreamUrl;
	
	@Persistent
	public String mName;
	
	@Persistent
	public Set<Long> mServicesKeys;

	public ChannelTransferObjectGAE() {
	}
	
	public ChannelTransferObjectGAE(ChannelBase base) {
		this.fillFromBase(base);
	}
	
	public void fillFromBase(ChannelBase base) {
		if(base.getId() != 0)
			this.mId = base.getId();
		this.mStreamUrl = base.getStreamUrl();
		this.mName = base.getName();
	}
	
	public ChannelBase toBase() {
		return new ChannelBase(mId, mName, mStreamUrl);
	}
}
