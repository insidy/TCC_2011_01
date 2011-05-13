package br.unisinos.swe.server.admin;

import java.util.ArrayList;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import br.unisinos.swe.server.MobiServerResource;
import br.unisinos.swe.server.StreamUtil;
import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.msg.GetChannelsMsg;
import br.unisinos.swe.shared.serialization.MobiSerialization;


public class ChannelResource extends MobiServerResource {
	
	protected Object processRequest(Representation representation) {
		
		String channel = retrieveAttribute("channel");
		GetChannelsMsg message = StreamUtil.messageFromRepresentation(GetChannelsMsg.class, representation);
		
		// TODO get channel list
		
		// TODO filter incompatible channels
		
		List<ChannelBase> channelList = new ArrayList<ChannelBase>();
		//channelList.add(new ChannelBase(0, "Family Guy", "http://daily3gp.com/vids/family_guy_penis_car.3gp"));
		//channelList.add(new ChannelBase(1, "Family Guy 2", "http://devimages.apple.com/iphone/samples/bipbop/gear1/descfileSequence1.ts"));
		
		channelList = ChannelDatabaseFactory.getPersistance().getList();
		
		return channelList;
	}

}
