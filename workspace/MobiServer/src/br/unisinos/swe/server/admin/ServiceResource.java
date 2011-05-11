package br.unisinos.swe.server.admin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;  
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.server.StreamUtil;
import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.ServiceBase;
import br.unisinos.swe.shared.msg.GetChannelsMsg;
import br.unisinos.swe.shared.msg.GetServicesMsg;


public class ServiceResource extends MobiServerResource {

	protected Object processRequest(Representation representation) {
		String selectedChannel = retrieveAttribute("channel");
		
		GetServicesMsg message = StreamUtil.messageFromRepresentation(GetServicesMsg.class, representation);

		// TODO get channel list
		
		// TODO find channel in channel list
		
		// TODO find services assigned to that channel
		
		// TODO filter incompatible services
		
		Reference ref = this.getReference();
		List<ServiceBase> serviceList = new ArrayList<ServiceBase>();
		serviceList.add(new ServiceBase(0, "Twitter Hashtag", ref.getHostIdentifier() + "/services/0"));
		
		
		return serviceList;
	}

}
