package br.unisinos.swe.mobile.shared;

import java.util.ArrayList;
import java.util.List;

import br.unisinos.swe.mobile.view.MobiViewManager;
import br.unisinos.swe.shared.ChannelBase;
import br.unisinos.swe.shared.api.DeviceInfo;
import br.unisinos.swe.shared.msg.GetChannelsMsg;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

public class LocalConfiguration {
	private static LocalConfiguration uniqueInstance;
	private MobiViewManager mViewManager;
	
	protected boolean mMenuVisible = true;
	
	protected List<ChannelBase> mChannels;
	protected MobiChannelWrapper mSelectedChannel;
	protected SingleItemQueue mChannelsLoadQueue = new SingleItemQueue();
	protected SingleItemQueue mServiceLoadQueue = new SingleItemQueue();
	
	protected String mSelectedServer;

	private LocalConfiguration() {
	}
	
	public static LocalConfiguration getInstance() {
		if(uniqueInstance == null)
			initiate();
		
		return uniqueInstance;
	}

	private static void initiate() {
		uniqueInstance = new LocalConfiguration();
	}
	
	public String getChannelsPath() {
		return "channels";
	}
	
	public String getServicesPath() {
		return "services";
	}
	
	public void setViewManager(MobiViewManager viewManager) {
		this.mViewManager = viewManager;
	}

	public String getVideoPath() {
		if(this.mSelectedChannel != null)
			return mSelectedChannel.getBean().getStreamUrl();
		else
			return null;
	}
	
	public String getSelectedServer() {
		return this.mSelectedServer;
	}

	public boolean isMenuVisible() {
		return mMenuVisible;
	}

	public void setMenuVisible(boolean mMenuVisible) {
		this.mMenuVisible = mMenuVisible;
	}
	
	public List<ChannelBase> getChannels() {
		if(this.mChannels != null)
			return this.mChannels;
		else
			return new ArrayList<ChannelBase>();
	}

	public void setSelectedChannelAt(int which) {
		ChannelBase newChannel = mChannels.get(which);
		ChannelBase currentChannel = (this.mSelectedChannel != null ? this.mSelectedChannel.getBean() : null); 
		if(currentChannel != newChannel) {
			this.mSelectedChannel = new MobiChannelWrapper(newChannel);
			
			
			new DelayedLoadThread(this.mViewManager.mServiceLoadHandler, mServiceLoadQueue, new Runnable() {
				
				@Override
				public void run() {
					mSelectedChannel.retrieveServices();
				}
			});
			
		}
	}

	public MobiChannelWrapper getSelectedChannel() {
		return this.mSelectedChannel;
	}

	public GetChannelsMsg fillMessage(GetChannelsMsg message) {
		message.setDevice(new DeviceInfo());
		return message;
	}

	public void reload(SharedPreferences defaultSharedPreferences) {
		String newServer = defaultSharedPreferences.getString("server", "EMPTY");
		
		if(this.mSelectedServer == null)
			this.mSelectedServer = "";
		
		// Check if selected server is equal to configuration 
		if(!this.mSelectedServer.equals(newServer)) {
			if(!newServer.startsWith("http://"))
				this.mSelectedServer = "http://" + newServer;
			else
				this.mSelectedServer = newServer;
			
			// If not, clear channel list and selected channel
			reloadChannels();
		}
	}

	private void reloadChannels() {
		this.mSelectedChannel = null;
		if(this.mChannels != null)
			this.mChannels.clear();
			
		new DelayedLoadThread(this.mViewManager.mChannelLoadHandler, mChannelsLoadQueue, new Runnable() {
			
			@Override
			public void run() {
				mChannels = MobiChannelWrapper.retrieveChannels();
			}
		});
		
	}
	
	/** Nested class that performs heavy HTTP-related procedure call */
    public class DelayedLoadThread extends Thread {
        Handler mHandler;
        Runnable mHeavyMethod;
        SingleItemQueue mQueue;
        
        public final static int STATE_DONE = 0;
        public final static int STATE_RUNNING = 1;
        public final static int STATE_ERROR = 999;
       
        DelayedLoadThread(Handler handler, SingleItemQueue queue, Runnable heavyMethod) {
            mHandler = handler;
            mHeavyMethod = heavyMethod;
            mQueue = queue;
            
            this.mQueue.setNextThread(this);
        }
       
        public void run() {
        	Message msg = mHandler.obtainMessage();
            msg.arg1 = STATE_RUNNING;
            mHandler.sendMessage(msg);
            
            mHeavyMethod.run();
            
            msg = mHandler.obtainMessage();
            msg.arg1 = STATE_DONE; // Set as error (no channel found)
            
            /*
            // TODO validate if anything was loaded and release data buttons
            if(mValidate != null)
            	if(mValidate.size() > 0)
            		msg.arg1 = STATE_DONE; // change to channels found
            */
            
            mHandler.sendMessage(msg);
            mQueue.startNextThread();
        }
    }
    
    public class SingleItemQueue {
    	protected DelayedLoadThread currentThread = null;
    	protected DelayedLoadThread nextThread = null;
    	
    	public SingleItemQueue() {
    		
    	}
    	
    	public void setNextThread(DelayedLoadThread nextThread) {
    		this.nextThread = nextThread;
    		
    		if(this.currentThread == null) {
    			startNextThread();
    		}
    	}
    	
    	public void startNextThread() {
    		this.currentThread = null;
    		if(this.nextThread != null) {
	    		this.currentThread = this.nextThread;
	    		this.currentThread.start();
	    		this.nextThread = null;
    		}
    	}
    	
    }
}
