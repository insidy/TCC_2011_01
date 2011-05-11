package br.unisinos.swe.mobile.shared;

import java.util.List;

import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.ServiceBase;
import br.unisinos.swe.shared.msg.GetChannelsMsg;
import br.unisinos.swe.shared.serialization.ISerializable;

public class MobiChannelWrapper  {

	/**
	 * 
	 */
	protected ChannelBase mChannel;
	protected MobiServiceWrapper mSelectedService;
	
	public MobiChannelWrapper(ChannelBase channel) {
		this.mChannel = channel;
	}

	public String getServer() {
		return LocalConfiguration.getInstance().getSelectedServer();
	}

	public void setSelectedServiceAt(int which) {
		if(which == -1)
			this.mSelectedService = null;
		else {
			if(getBean().getServices() != null)
				this.mSelectedService = new MobiServiceWrapper(getBean().getServices().get(which));
		}
	}

	public MobiServiceWrapper getSelectedService() {
		return this.mSelectedService;
	}

	public static List<ChannelBase> retrieveChannels() {
		// Define message parameters
		GetChannelsMsg message = new GetChannelsMsg();
		message = LocalConfiguration.getInstance().fillMessage(message);

		// Example service list pattern: http://SERVER/channels
		StringBuilder path = new StringBuilder();
		path.append(LocalConfiguration.getInstance().getSelectedServer());
		path.append("/");
		path.append(LocalConfiguration.getInstance().getChannelsPath());
		
		//Retrieve data
		List<ChannelBase> channels = MobiUtil.getListFromServer(ChannelBase.class, path.toString(), message);

		return channels;
	}

	public void retrieveServices() {
		this.mChannel.getServices().clear();
		this.mChannel.getServices().addAll(MobiServiceWrapper.retrieveServices(this));
	}

	public List<ServiceBase> getServices() {
		if(this.getBean() == null)
			return null;
		
		return this.getBean().getServices();
	}
	
	public ChannelBase getBean() {
		return this.mChannel;
	}

}
