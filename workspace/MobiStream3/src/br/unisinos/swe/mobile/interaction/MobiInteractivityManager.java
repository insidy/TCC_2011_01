package br.unisinos.swe.mobile.interaction;

import br.unisinos.swe.mobile.shared.*;

public class MobiInteractivityManager {
	private static MobiInteractivityManager uniqueInstance;
	private LocalConfiguration mConfig;
	private DynamicOverlay mDynOverlay;
	
	private MobiInteractivityManager() {
	}
	
	public static MobiInteractivityManager getInstance() {
		if(uniqueInstance == null)
			initiate();
		
		return uniqueInstance;
	}
	
	private static void initiate() {
		uniqueInstance = new MobiInteractivityManager();
	}
	
	private LocalConfiguration getConfiguration() {
		if(this.mConfig == null)
			this.mConfig = LocalConfiguration.getInstance();
		
		return this.mConfig;
	}
	
	public void destroy() {
		this.stopSelectedService();
		
		if(this.mDynOverlay != null)
			this.mDynOverlay.destroy();
	}
	
	public void setDynOverlay(DynamicOverlay overlay) {
		this.mDynOverlay = overlay;
	}
	
	public void startSelectedService() {
		try {
			startService(getConfiguration().getSelectedChannel().getSelectedService());
		} catch(NullPointerException ex) {
			
		}
	}

	private void startService(MobiServiceWrapper service) {
		// TODO Code start service
		//service.execute(null);
		this.mDynOverlay.loadService(service.getBean().getServiceUrl());
	}

	public void stopSelectedService() {
		try {
			
			stopService(getConfiguration().getSelectedChannel().getSelectedService());
		} catch(NullPointerException ex) {
			
		}
		
	}

	private void stopService(MobiServiceWrapper service) {
		this.mDynOverlay.stopService();
	}

	public void checkVisibility() {
		//if(this.mDynOverlay != null)
		//	this.mDynOverlay.checkVisibility();
	}

	public void onServiceMaxClick() {
		this.mDynOverlay.invertVisibility();
	}

	public void onServiceCloseClick() {
		this.stopSelectedService();
		this.mDynOverlay.refresh();
	}
}
