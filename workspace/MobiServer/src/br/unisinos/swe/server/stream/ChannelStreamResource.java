package br.unisinos.swe.server.stream;

import org.restlet.representation.Representation;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.server.StreamUtil;
import br.unisinos.swe.shared.msg.GetChannelsMsg;


public class ChannelStreamResource extends MobiServerResource {
	
	protected Object processRequest(Representation representation) {
		
		String channel = retrieveAttribute("channel");
		GetChannelsMsg message = StreamUtil.messageFromRepresentation(GetChannelsMsg.class, representation);
		
		// TODO get channel list
		
		// TODO filter incompatible channels
		
		
		return "";
	}

}
