package br.unisinos.swe.server.admin;

import br.unisinos.swe.server.StreamServerNode;
import br.unisinos.swe.server.admin.gae.ServiceDatabaseGAE;

public class ServiceDatabaseFactory {
	public static IServiceDatabase getPersistance() {
		IServiceDatabase persistance = null;
		
		switch(StreamServerNode.getInstance().getRunningMode()) {
		case STANDALONE:
			// Stand alone in-memory database
			
			break;
		case GAE:
			// Google app engine-based database
			persistance = ServiceDatabaseGAE.getInstance();
			break;
		}
		
		return persistance;
	}
}
