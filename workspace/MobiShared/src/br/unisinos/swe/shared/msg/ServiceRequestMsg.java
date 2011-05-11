package br.unisinos.swe.shared.msg;

import br.unisinos.swe.shared.api.DeviceInfo;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class ServiceRequestMsg extends ServiceBaseMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2426091751419516325L;
	// TODO code class
	@TransferParameter("device_info") protected DeviceInfo mInfo; // device info object
	//@TransferParameter("app_data") protected AppData mData; // array of app data
	
	public ServiceRequestMsg() {
		super();
	}
	
	public DeviceInfo getInfo(){
		return this.mInfo;
	}
	
	public void setInfo(DeviceInfo info) {
		this.mInfo = info;
	}
}
