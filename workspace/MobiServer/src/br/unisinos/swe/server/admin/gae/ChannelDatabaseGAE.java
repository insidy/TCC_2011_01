package br.unisinos.swe.server.admin.gae;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import br.unisinos.swe.server.admin.ChannelResource;
import br.unisinos.swe.server.admin.IChannelDatabase;
import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.ServiceBase;

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
			ChannelTransferObjectGAE persistant;
			if(channel.getId() == 0) {
				persistant = new ChannelTransferObjectGAE(channel);
			} else {
				persistant = pm.getObjectById(ChannelTransferObjectGAE.class, channel.getId());
				persistant.fillFromBase(channel);
			}
			
			
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

	@Override
	public ChannelBase getSingle(long channelId) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		ChannelTransferObjectGAE transfer = pm.getObjectById(ChannelTransferObjectGAE.class, channelId);
		ChannelBase channel = new ChannelBase(transfer.mId, transfer.mName, transfer.mStreamUrl);
		
		List<ServiceBase> services = ServiceDatabaseGAE.getInstance().getList();
		for(ServiceBase service : services) {
			if(transfer.mServicesKeys.contains(service.getId())) {
				channel.getServices().add(service);
			}
		}
		
		return channel;
	}

	@Override
	public boolean delete(long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		boolean wasDeleted = false;
		
		try {
			ChannelTransferObjectGAE transfer = pm.getObjectById(ChannelTransferObjectGAE.class, id);
			if(transfer != null) {
				pm.deletePersistent(transfer);
				wasDeleted = true;
			}
		} catch (Exception e) {
			
		}
		
		return wasDeleted;
	}

	@Override
	public boolean linkApps(long id, String[] appsId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		boolean wasUpdated = false;
		
		try {
			ChannelTransferObjectGAE transfer = pm.getObjectById(ChannelTransferObjectGAE.class, id);
			if(transfer.mServicesKeys == null)
				transfer.mServicesKeys = new HashSet<Long>();
			else
				transfer.mServicesKeys.clear();
			
			for(int idx = 0; idx < appsId.length; idx++) {
				long appId = Long.parseLong(appsId[idx]);
				transfer.mServicesKeys.add(appId);
			}
			pm.makePersistent(transfer);
			
			wasUpdated = true;
			
		} catch (Exception e) {
			
		} finally {
            pm.close();
        }
		
		return wasUpdated;
	}

}
