package br.unisinos.swe.shared.msg;

import br.unisinos.swe.shared.api.DeviceInfo;
import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class GetChannelsMsg implements ISerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4700586301804444929L;
	@TransferParameter("device") protected DeviceInfo mDevice; // recode to another object
	@TransferParameter("version") protected String mVersion;
	
	public GetChannelsMsg() {
		
	}
	
	public DeviceInfo getDevice(){
		return this.mDevice;
	}
	
	public void setDevice(DeviceInfo device) {
		this.mDevice = device;
	}
	
	public String getVersion(){
		return this.mVersion;
	}
	
	public void setVersion(String version) {
		this.mVersion = version;
	}
}
