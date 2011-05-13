package br.unisinos.swe.server.admin.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import br.unisinos.swe.shared.ChannelBase;

@PersistenceCapable
public class ChannelTransferObjectGAE {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected int mId;
	
	@Persistent
	protected String mStreamUrl;
	
	@Persistent
	protected String mName;

	public ChannelTransferObjectGAE() {
		
	}
	
	public ChannelTransferObjectGAE(ChannelBase base) {
		this.fillFromBase(base);
	}
	
	public void fillFromBase(ChannelBase base) {
		this.mId = base.getId();
		this.mStreamUrl = base.getStreamUrl();
		this.mName = base.getName();
	}
	
	public ChannelBase toBase() {
		return new ChannelBase(mId, mStreamUrl, mName);
	}
}
