package br.unisinos.swe.shared.api;

import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.TransferParameter;

public class DeviceInfo implements ISerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5846864906742451321L;
	@TransferParameter("os") protected String mOs;
	@TransferParameter("os_version") protected String mOsVersion;
	@TransferParameter("resolution") protected String mResolution;
	@TransferParameter("connection_type") protected String mConnectionType;
	@TransferParameter("bandwidth") protected String mBandwidth;

	public void setOs(String os) {
		this.mOs = os;
	}
	
	public String getOs() {
		return this.mOs;
	}
}
