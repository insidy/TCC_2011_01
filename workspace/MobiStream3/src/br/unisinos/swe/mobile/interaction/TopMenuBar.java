package br.unisinos.swe.mobile.interaction;


import br.unisinos.swe.mobile.R;
import br.unisinos.swe.mobile.view.IMobiViewComponent;
import br.unisinos.swe.mobile.view.MobiViewManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class TopMenuBar extends LinearLayout implements IMobiViewComponent {

	private MobiViewManager mController;
	
	private ImageButton btnServiceList;
	private ImageButton btnServiceMax;
	private ImageButton btnServiceClose; 

	public TopMenuBar(Context context) {
		super(context);
		initialize(context);
	}
	
	public TopMenuBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	/*
	public TopMenuBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}*/
	
	private void initialize(Context context) {
		LayoutInflater.from(context).inflate(R.layout.topmenu, this, true);
		initializeButtonEvents();
		
		this.serviceButtonsEnabled(false);
		this.channelButtonsEnabled(false);
	}

	private void initializeButtonEvents() {
		btnServiceList = (ImageButton) this.findViewById(R.id.btn_service_list);
		if(btnServiceList != null) {
			btnServiceList.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.onServiceListClick();
	            }
	
	        });
		}
		
		btnServiceMax = (ImageButton) this.findViewById(R.id.btn_service_maximize);
		if(btnServiceMax != null) {
			btnServiceMax.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.onServiceMaxClick();
	            }
	
	        });
		}
		
		btnServiceClose = (ImageButton) this.findViewById(R.id.btn_service_close);
		if(btnServiceClose != null) {
			btnServiceClose.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	mController.onServiceCloseClick();
	            }
	
	        });
		}
		
	}

	//@Override
	public void setController(MobiViewManager ctrl) {
		this.mController = ctrl;
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

	//@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void serviceButtonsEnabled(boolean isEnabled){
		this.btnServiceMax.setEnabled(isEnabled);
		this.btnServiceClose.setEnabled(isEnabled);
	}
	
	public void channelButtonsEnabled(boolean isEnabled){
		this.btnServiceList.setEnabled(isEnabled);
		if(isEnabled == false) // If channel button isn't enabled, neither should services buttons be
			serviceButtonsEnabled(isEnabled);
	}

}
