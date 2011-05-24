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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class DynamicOverlay extends RelativeLayout implements IMobiViewComponent {

	private MobiViewManager mController; 
	protected WebView mWebView;
	
	private boolean mVisible = true;
	private boolean mInitialized = false;
	
	public DynamicOverlay(Context context) {
		super(context);
		initialize(context);
	}
	
	public DynamicOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}
	
	public DynamicOverlay(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	private void initialize(Context context) {
		LayoutInflater.from(context).inflate(R.layout.dynoverlay, this, true);
		
		initializeWebView();
        
	}

	private void initializeWebView() {
		mWebView = (WebView) this.findViewById(R.id.webview);
        mWebView.setBackgroundColor(0);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        
        
        /*
        mWebView.setWebViewClient(new WebViewClient(){  
            @Override  
            public void onPageFinished(WebView view, String url)  
            {  // TODO if the app was made for videos, we don't need to change the background
            	view.loadUrl("javascript:(function() { " +  
                        "document.getElementsByTagName('body')[0].style.backgroundColor = 'transparent'; " +  
                        "})()");  
            }  });
        */
        
        //String summary = "<html><body>You scored <b>192</b> points.</body></html>";
        //mWebView.loadData(summary, "text/html", "utf-8");
        
        mWebView.setWebViewClient(new WebViewClient(){  
            @Override  
            public void onPageFinished(WebView view, String url)  
            {
            	super.onPageFinished(view, url);
            	view.loadUrl("javascript:(function() { " +  
            			"MobiApp_Init('" + mController.getDeviceDataAsJson() + "');"  + 
                        "})()");  
            	mInitialized = true;
            }  });
		
	}

	public void loadService(String serviceUrl) {
		mInitialized = false;
		mWebView.clearView();
		mWebView.loadUrl(serviceUrl);
		this.showMe();
	}
	
	//@Override
	public void setController(MobiViewManager ctrl) {
		this.mController = ctrl;
	}

	public void invertVisibility() {
		this.mVisible = !this.mVisible;
		checkVisibility();
	}
	
	public void checkVisibility() {
		if(this.mVisible) {
			showMe();
		} else {
			hideMe();
		}
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

	public void refresh() {
		this.hideMe();
	}

	//@Override
	public void destroy() {
		this.refresh();
		
		this.stopService();
		
		
	}

	public void stopService() {
		mWebView.loadUrl("javascript:(function() { " +  
    			"MobiApp_Finish('" + mController.getDeviceDataAsJson() + "');"  + 
                "})()");  
		mInitialized = false;
	}

}
