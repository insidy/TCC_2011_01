package br.unisinos.swe.shared.msg;


import java.util.ArrayList;

import br.unisinos.swe.shared.api.DeviceInfo;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class GetServicesMsg extends GetChannelsMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -556285571096452635L;
	@TransferParameter("info") protected String mInfo;
	@TransferParameter("lista_simples") public ArrayList<DeviceInfo> devices;
	
	public GetServicesMsg() {
		super();
	}
	
	public String getInfo(){
		return this.mInfo;
	}
	
	public void setInfo(String info) {
		this.mInfo = info;
	}
}
