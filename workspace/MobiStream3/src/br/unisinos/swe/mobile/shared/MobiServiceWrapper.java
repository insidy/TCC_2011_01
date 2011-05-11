package br.unisinos.swe.mobile.shared;

import java.util.ArrayList;
import java.util.List;

import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.ServiceBase;
import br.unisinos.swe.shared.msg.GetServicesMsg;
import br.unisinos.swe.shared.msg.ServiceRequestMsg;
import br.unisinos.swe.shared.msg.ServiceResponseMsg;


public class MobiServiceWrapper{
	
	protected ServiceBase mService;
	
	public MobiServiceWrapper(ServiceBase service) {
		this.mService = service;
	}

	public static List<ServiceBase> retrieveServices(MobiChannelWrapper channel) {
		// Define message parameters
		GetServicesMsg message = new GetServicesMsg();
		message = (GetServicesMsg)LocalConfiguration.getInstance().fillMessage(message);
		message.setInfo("Informação");
		
		// Example service list pattern: http://SERVER/channels/CHANNEL ID/services
		StringBuilder path = new StringBuilder();
		path.append(channel.getServer());
		path.append("/");
		path.append(LocalConfiguration.getInstance().getChannelsPath());
		path.append("/");
		path.append(channel.getBean().getId());
		path.append("/");
		path.append(LocalConfiguration.getInstance().getServicesPath());
		
		//Retrieve data
		
		List<ServiceBase> typedList = MobiUtil.getListFromServer(ServiceBase.class, path.toString(), message);
		
		return typedList;
	}

	public void execute(String action) {
		//Define message parameters
		StringBuilder path = new StringBuilder();
		path.append(this.mService.getServiceUrl());
		if(action != null) {
			path.append("/");
			path.append(action);
		}
		ServiceRequestMsg message = new ServiceRequestMsg();
		//message = (GetServicesMsg)LocalConfiguration.getInstance().fillMessage(message);
		
		ServiceResponseMsg responseMessage = MobiUtil.getObjectFromServer(ServiceResponseMsg.class, this.getBean().getServiceUrl(), message);
		
	}
	
	public ServiceBase getBean() {
		return this.mService;
	}

}
