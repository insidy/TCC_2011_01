package br.unisinos.swe.server.admin;

import java.util.List;

import br.unisinos.swe.shared.ServiceBase;

public interface IServiceDatabase {
	public void save(ServiceBase service);
	//public void save(List<ServiceBase> service);
	
	public List<ServiceBase> getList();
	
	public boolean delete(long id);
	
}
