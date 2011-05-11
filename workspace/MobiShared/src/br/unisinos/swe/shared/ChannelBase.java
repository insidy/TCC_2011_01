package br.unisinos.swe.shared;

import java.util.ArrayList;
import java.util.List;

import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class ChannelBase implements ISerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4833378962300868277L;

	protected List<ServiceBase> mServiceList;
	
	@TransferParameter("id") protected int mId;
	@TransferParameter("url") protected String mStreamUrl;
	@TransferParameter("name") protected String mName;
	
	public ChannelBase() {
		
	}
	
	public ChannelBase(int id, String name, String url) {
		this.mId = id;
		this.mName = name;
		this.mStreamUrl = url;
	}
	
	public String getStreamUrl() {
		return this.mStreamUrl;
	}
	
	public String getName() {
		return this.mName;
	}
	
	public int getId() {
		return this.mId;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	public List<ServiceBase> getServices() {
		if(this.mServiceList == null)
			this.mServiceList = new ArrayList<ServiceBase>();
		
		return this.mServiceList; 
	}
	
	/**
	 * Convert this object to a JSON object for representation
	 */
	/*
	public JSONObject toJSON() {
		try{
			JSONObject object = new JSONObject();
			object.put(JSON_ID, this.mId);
			object.put(JSON_NAME, this.mName);
			object.put(JSON_URL, this.mStreamUrl);
			return object;
		}catch(Exception e){
			return null;
		}
	}
	
	public void fromJSON(String jsonobj) {
		try {
			JSONObject object = new JSONObject(jsonobj);
			this.mId = object.getInt(JSON_ID);
			this.mName = object.getString(JSON_NAME);
			this.mStreamUrl = object.getString(JSON_URL);
			
		} catch(JSONException ex) {
			
		}
	}*/
	
}
