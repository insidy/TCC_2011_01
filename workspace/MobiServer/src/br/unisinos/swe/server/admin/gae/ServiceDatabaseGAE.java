package br.unisinos.swe.server.admin.gae;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import br.unisinos.swe.server.admin.IServiceDatabase;
import br.unisinos.swe.shared.ServiceBase;

public class ServiceDatabaseGAE implements IServiceDatabase {
	
	private static ServiceDatabaseGAE instance = null;
	
	public static ServiceDatabaseGAE getInstance() {
		if(instance == null)
			instance = new ServiceDatabaseGAE();
		return instance;
	}
	
	@Override
	public void save(ServiceBase service) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			ServiceTransferObjectGAE persistant = new ServiceTransferObjectGAE(service);
			
            pm.makePersistent(persistant);
        } finally {
            pm.close();
        }
		
	}

	@Override
	public List<ServiceBase> getList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ServiceBase> services = new ArrayList<ServiceBase>();
		
		Query query = pm.newQuery(ServiceTransferObjectGAE.class);
		List<ServiceTransferObjectGAE> gaeList = (List<ServiceTransferObjectGAE>) query.execute();
		
		if(!gaeList.isEmpty()) {
			for(ServiceTransferObjectGAE transfer : gaeList) {
				ServiceBase service = new ServiceBase(transfer.mId, transfer.mName, transfer.mServiceUrl);
				services.add(service);
			}
		}
		
		
		return services;
	}

}
