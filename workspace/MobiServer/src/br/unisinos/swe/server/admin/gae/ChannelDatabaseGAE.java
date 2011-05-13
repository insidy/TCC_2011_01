package br.unisinos.swe.server.admin.gae;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import br.unisinos.swe.server.admin.ChannelResource;
import br.unisinos.swe.server.admin.IChannelDatabase;
import br.unisinos.swe.shared.ChannelBase;

public class ChannelDatabaseGAE implements IChannelDatabase {
	
	private static ChannelDatabaseGAE instance = null;
	
	public static ChannelDatabaseGAE getInstance() {
		if(instance == null)
			instance = new ChannelDatabaseGAE();
		return instance;
	}
	
	@Override
	public void save(ChannelBase channel) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			ChannelTransferObjectGAE persistant = new ChannelTransferObjectGAE(channel);
			
            pm.makePersistent(persistant);
        } finally {
            pm.close();
        }
		
	}

	@Override
	public List<ChannelBase> getList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ChannelBase> channels = new ArrayList<ChannelBase>();
		
		Query query = pm.newQuery(ChannelTransferObjectGAE.class);
		List<ChannelTransferObjectGAE> gaeList = (List<ChannelTransferObjectGAE>) query.execute();
		if(!gaeList.isEmpty()) {
			for(ChannelTransferObjectGAE transfer : gaeList) {
				ChannelBase channel = new ChannelBase(transfer.mId, transfer.mName, transfer.mStreamUrl);
				channels.add(channel);
			}
		}
		
		
		return channels;
	}

}
