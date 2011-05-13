package br.unisinos.swe.server.admin;

import br.unisinos.swe.server.StreamServerNode;
import br.unisinos.swe.server.admin.gae.ChannelDatabaseGAE;

public class ChannelDatabaseFactory {

	public static IChannelDatabase getPersistance() {
		IChannelDatabase persistance = null;
		
		switch(StreamServerNode.getInstance().getRunningMode()) {
		case STANDALONE:
			// Stand alone in-memory database
			
			break;
		case GAE:
			// Google app engine-based database
			persistance = ChannelDatabaseGAE.getInstance();
			break;
		}
		
		return persistance;
	}
}
