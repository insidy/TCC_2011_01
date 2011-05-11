package br.unisinos.swe.mobile.view;

import br.unisinos.swe.mobile.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainMenuBar extends LinearLayout implements IMobiViewComponent {
	
	private MobiViewManager mController;
	
	private ImageButton btnOpen;
	
	public MainMenuBar(Context context) {
		super(context);
		initialize(context);
	}
	public MainMenuBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	private void initialize(Context context) {
		setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setWeightSum(1.0f);
        
        LayoutInflater.from(context).inflate(R.layout.menu, this, true);
        
        initializeButtonEvents();
	}
	
	public void hideMe() {
		if(this.getVisibility() != View.INVISIBLE) {
			Animation animation = AnimationUtils.loadAnimation(this.getContext(),
			                                                   android.R.anim.fade_out);
			this.startAnimation(animation);
			this.setVisibility(View.INVISIBLE);
		}
	}
	
	public void showMe() {
		if(this.getVisibility() != View.VISIBLE) {
			this.setVisibility(View.VISIBLE);
			Animation animation = AnimationUtils.loadAnimation(this.getContext(),
	                android.R.anim.fade_in);
			this.startAnimation(animation);
		}
	}

	private void initializeButtonEvents() {
		ImageButton btnPlay = (ImageButton) this.findViewById(R.id.play_button);
		if(btnPlay != null) {
	        btnPlay.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.playVideo();
	            }
	
	        });
		}
        
        ImageButton btnStop = (ImageButton) this.findViewById(R.id.stop_button);
        if(btnStop != null) {
	        btnStop.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.stopVideo();
	            }
	
	        });
        }
        
        
        // We may delegate Open and config button to menu/action bar
        btnOpen = (ImageButton) this.findViewById(R.id.open_button);
        if(btnOpen != null) {
	        btnOpen.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.openVideo();
	            }
	
	        });
        }
        /*
        ImageButton btnConfig = (ImageButton) this.findViewById(R.id.config_button);
        if(btnConfig != null) {
	        btnConfig.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.openConfig();
	            }
	
	        });
        }
		*/
	}

	@Override
	public void setController(MobiViewManager ctrl) {
		this.mController = ctrl;
	}
	@Override
	public void destroy() {
		
	}
	
	public void channelsButtonsEnabled(boolean isEnabled){
		this.btnOpen.setEnabled(isEnabled);
	}
	
	public boolean getChannelsButtonsEnabled() {
		return this.btnOpen.isEnabled();
	}
}
