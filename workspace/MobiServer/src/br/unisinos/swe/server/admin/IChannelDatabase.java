package br.unisinos.swe.server.admin;

import java.util.List;

import br.unisinos.swe.shared.ChannelBase;

public interface IChannelDatabase {
	
	public void save(ChannelBase channel);
	//public void save(List<ChannelBase> channel);
	
	public List<ChannelBase> getList();
	
	public ChannelBase getSingle(long channelId);
	
	public boolean delete(long id);
	
	public boolean linkApps(long id, String[] appsId);
	

}
