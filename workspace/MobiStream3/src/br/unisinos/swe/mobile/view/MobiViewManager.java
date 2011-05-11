package br.unisinos.swe.mobile.view;

import java.util.List;

import br.unisinos.swe.mobile.MobiPlayer;
import br.unisinos.swe.mobile.MobiPreferences;
import br.unisinos.swe.mobile.interaction.DynamicOverlay;
import br.unisinos.swe.mobile.interaction.MobiInteractivityManager;
import br.unisinos.swe.mobile.interaction.TopMenuBar;
import br.unisinos.swe.mobile.shared.LocalConfiguration;
import br.unisinos.swe.mobile.stream.StreamView;
import android.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

public class MobiViewManager {
	
	private Context mContext;
	private MobiPlayer mActivity;
	
	// Custom Visible Components
	private StreamView mSurface;
	private MainMenuBar mMenu;
	private TopMenuBar mTopMenu;
	
	// Standard Dialogs
	ProgressDialog waitDialog = null;
	AlertDialog alertDialog = null;
	
	// Configuration Variables
	private LocalConfiguration mConfig;

	
	public MobiViewManager(MobiPlayer activity, Context ctx) {
		mContext = ctx;
		mActivity = activity;
		mConfig = LocalConfiguration.getInstance();
		mConfig.setViewManager(this);
	}
	
	public void destroy() {
		this.dismissWaitDialog();
		MobiInteractivityManager.getInstance().destroy();
		
		if(this.mSurface != null)
			this.mSurface.destroy();
		
		if(this.mMenu != null)
			this.mMenu.destroy();
		
		if(this.mTopMenu != null)
			this.mTopMenu.destroy();
	}
	
	public void setSurface(StreamView surface) {
		this.mSurface = surface;
		if(this.mSurface != null)
			this.mSurface.setController(this);
		
	}

	public void setTopMenu(TopMenuBar topMenu) {
		this.mTopMenu = topMenu;
		if(this.mTopMenu != null) {
			this.mTopMenu.setController(this);
			checkMenuVisibility();
		}
	}
	
	public void setMenu(MainMenuBar menu) {
		this.mMenu = menu;
		if(this.mMenu != null) {
			this.mMenu.setController(this);
			checkMenuVisibility();
		}
	}
	
	public void setDynOverlay(DynamicOverlay overlay) {
		MobiInteractivityManager.getInstance().setDynOverlay(overlay);
		if(overlay != null)
			overlay.setController(this);
	}
	
	public void invertMenuVisibility() {
		setMenuVisibility(!this.mConfig.isMenuVisible());
	}
	
	public void setMenuVisibility(boolean visible) {
		this.mConfig.setMenuVisible(visible);
		checkMenuVisibility();
	}
	
	private void checkMenuVisibility(){
		if(this.mConfig.isMenuVisible()){
			if(this.mMenu != null)
				this.mMenu.showMe();
			
			if(this.mTopMenu != null)
				this.mTopMenu.showMe();
		} else {
			if(this.mMenu != null)
				this.mMenu.hideMe();
			
			if(this.mTopMenu != null)
				this.mTopMenu.hideMe();
			
		}
		MobiInteractivityManager.getInstance().checkVisibility();
	}

	/* Video actions */
	public void playVideo() {
		if(mSurface.playVideo(this.mConfig.getVideoPath())) { 
			showWaitDialog();
		}
	}

	public void stopVideo() {
		mSurface.stopVideoPlayback();
	}

	public void openVideo() {
		/*
		// Old version, direct input
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);

		builder.setTitle("Servidor de stream");
		builder.setMessage("Digite o endereço do servidor");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this.mContext);
		input.setText(this.mConfig.getVideoPath());
		builder.setView(input);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  mConfig.setVideoPath(input.getText().toString());
		  playVideo();
		  }
		});

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    dialog.cancel();
		  }
		});
		builder.show();
		*/
		
		if(this.mMenu.getChannelsButtonsEnabled()) {
			int selectedIndex = -1;
			
			if(this.mConfig.getSelectedChannel() != null)
				selectedIndex = this.mConfig.getChannels().lastIndexOf(this.mConfig.getSelectedChannel());
			
			showListDialog("Selecione um canal", selectedIndex, this.mConfig.getChannels(), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	mConfig.setSelectedChannelAt(which);
	            	playVideo();
	            	dialog.cancel();
	            }});
		}
	}
	
	public void releaseVideo() {
		mSurface.releaseMediaPlayer();
		mSurface.doCleanUp();
		mTopMenu.channelButtonsEnabled(false);
	}
	
	// Define the Handler that receives messages from the thread and update the progress / channel opening button
    public final Handler mChannelLoadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.arg1 == LocalConfiguration.DelayedLoadThread.STATE_RUNNING) {
            	mActivity.setProgressBarIndeterminateVisibility(true);
            	mMenu.channelsButtonsEnabled(false);
            } else if(msg.arg1 == LocalConfiguration.DelayedLoadThread.STATE_DONE) {
            	mActivity.setProgressBarIndeterminateVisibility(false);
            	mMenu.channelsButtonsEnabled(true);
            } else {
            	mActivity.setProgressBarIndeterminateVisibility(false);
            	mMenu.channelsButtonsEnabled(false);
            }
        }
    };
    
    public final Handler mServiceLoadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.arg1 == LocalConfiguration.DelayedLoadThread.STATE_RUNNING) {
            	mActivity.setProgressBarIndeterminateVisibility(true);
            	mTopMenu.serviceButtonsEnabled(false);
            	mTopMenu.channelButtonsEnabled(false);
            } else if(msg.arg1 == LocalConfiguration.DelayedLoadThread.STATE_DONE) {
            	mActivity.setProgressBarIndeterminateVisibility(false);
            	mTopMenu.serviceButtonsEnabled(false);
            	mTopMenu.channelButtonsEnabled(true);
            } else {
            	mActivity.setProgressBarIndeterminateVisibility(false);
            	mTopMenu.serviceButtonsEnabled(false);
            	mTopMenu.channelButtonsEnabled(false);
            }
        }
    };
	
	/* Configuration functionalities */	
	public void reloadConfiguration() {
		this.mConfig.reload(PreferenceManager.getDefaultSharedPreferences(this.mContext));
		releaseVideo();
	}

	public void openConfig() {
		Intent settingsActivity = new Intent(this.mContext, MobiPreferences.class);
		//this.mActivity.startActivityForResult(settingsActivity, 0);
		this.mActivity.startActivity(settingsActivity);
	}

	/* Video events */
	public void onVideoPrepared() {
		this.dismissWaitDialog();
		setMenuVisibility(false);
		mSurface.startVideoPlayback();
	}
	
	public void onVideoError() {
		this.dismissWaitDialog();
		this.showAlertDialog("Error", "Unable to load video");
	}

	public void onVideoCompleted() {
		mSurface.releaseMediaPlayer();
	}
	
	public void onVideoTouch() {
		invertMenuVisibility();
	}

	/* Pop-up functionalities */
	
	/**
	 * Create a new list dialog with a given item collection using toString method as display
	 * @param title List title
	 * @param items Item collection (override toString for correct display)
	 * @param listener onClick listener
	 */
	private void showListDialog(String title, int selectedIndex, List items, DialogInterface.OnClickListener listener) {
		if(items.size() <= 0){
			showAlertDialog(title, "Nothing to display");
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
		builder.setTitle(title);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mContext, br.unisinos.swe.mobile.R.layout.list_dialog_single_choice);
		for(Object item : items) {
			adapter.add(item.toString());
		}
		
		builder.setSingleChoiceItems(adapter, selectedIndex, listener);
		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   dialog.cancel();
	           }
	       });
		
		builder.create().show();
	}
	
	private void showAlertDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
		builder.setMessage(message)
			   .setTitle(title)
			   .setIcon(android.R.drawable.ic_dialog_alert)
		       .setCancelable(false)
		       .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       })
		       /*.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                MyActivity.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       })*/
		       ;
		alertDialog = builder.create();
		alertDialog.show();
	}

	private void showWaitDialog() {
		//mActivity.setProgressBarIndeterminateVisibility(true);
		waitDialog = ProgressDialog.show(this.mContext, "",  "Loading video, please wait...", true, true, new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(dialog != null)
					dialog.cancel();
				//releaseVideo();
			}
		});
	}
	private void dismissWaitDialog() {
		if(waitDialog != null) {
			waitDialog.cancel();
			waitDialog = null;
		}
		//mActivity.setProgressBarIndeterminateVisibility(false);
		
	}

	/* Interactivity-based events */
	public void onServiceListClick() {
		// Open up available services list from selected channel
		int selectedIndex = -1;
		
		if(this.mConfig.getSelectedChannel() != null) {
			if(this.mConfig.getSelectedChannel().getSelectedService() != null)
				selectedIndex = this.mConfig.getSelectedChannel().getServices().lastIndexOf(this.mConfig.getSelectedChannel().getSelectedService().getBean());
		
		showListDialog("Selecione um serviço", selectedIndex, this.mConfig.getSelectedChannel().getServices(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	mConfig.getSelectedChannel().setSelectedServiceAt(which);
            	MobiInteractivityManager.getInstance().startSelectedService();
            	mTopMenu.serviceButtonsEnabled(true);
            	dialog.cancel();
            }});
		}
	}

	public void onServiceMaxClick() {
		// Show/hide current selected service
		MobiInteractivityManager.getInstance().onServiceMaxClick();
	}

	public void onServiceCloseClick() {
		// Exit current service and hide overlay
		MobiInteractivityManager.getInstance().onServiceCloseClick();
		mConfig.getSelectedChannel().setSelectedServiceAt(-1);
		this.mTopMenu.serviceButtonsEnabled(false);
	}
}
