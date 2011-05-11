package br.unisinos.swe.mobile.stream;


import br.unisinos.swe.mobile.shared.MobiUtil;
import br.unisinos.swe.mobile.view.IMobiViewComponent;
import br.unisinos.swe.mobile.view.MobiViewManager;
import android.content.Context;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StreamView extends SurfaceView implements
OnBufferingUpdateListener, OnCompletionListener, OnErrorListener,
OnPreparedListener, OnVideoSizeChangedListener, OnInfoListener, SurfaceHolder.Callback, IMobiViewComponent {

	private MobiViewManager mController;
	private SurfaceHolder mHolder;
	private MediaPlayer mMediaPlayer = null;
	private int mVideoWidth;
	private int mVideoHeight;
	
	public StreamView(Context context) {
	    super(context);
	    initiate();
	}
	
	public StreamView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initiate();
	}
	
	public StreamView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initiate();
	}
	

	private void initiate() {
		mHolder = getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        
        this.setZOrderMediaOverlay(true);
	}

	  public boolean onTouchEvent(MotionEvent event) {
		/*
		Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.menu);
        
        
        Button btnPlay = (Button) dialog.findViewById(R.id.play_button);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	playVideo(null);
            }

        });
        

        WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
        WMLP.gravity = (Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WMLP.x = 0;
        WMLP.y = 0;
        dialog.getWindow().setAttributes(WMLP);

        dialog.show();
        */
		mController.onVideoTouch();
	    return super.onTouchEvent(event);
	}

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(MobiUtil.TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(MobiUtil.TAG, "onCompletion called");
        this.mController.onVideoCompleted();
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(MobiUtil.TAG, "onVideoSizeChanged called");
        mVideoWidth = width;
        mVideoHeight = height;
        
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(MobiUtil.TAG, "onPrepared called");
        this.mController.onVideoPrepared();
    }
    
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e(MobiUtil.TAG, "onError called");
		this.mController.onVideoError();
		return false;
	}

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(MobiUtil.TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(MobiUtil.TAG, "surfaceDestroyed called");
    }


    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MobiUtil.TAG, "surfaceCreated called");
        //playVideo("httplive://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8");
        //playVideo("http://www.opticodec.com/test/ps.3gp");
        //playVideo("http://daily3gp.com/vids/familyguy-s6-trailer.3gp");
    }
    
    public boolean playVideo(String path) {
    	if(path == null)
    		return false;
    	
    	try {
    		releaseMediaPlayer();
    		
    		if(mMediaPlayer == null) {
	    		mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(path);
		        mMediaPlayer.setDisplay(mHolder);
		        mMediaPlayer.setOnBufferingUpdateListener(this);
		        mMediaPlayer.setOnInfoListener(this);
		        mMediaPlayer.setOnCompletionListener(this);
		        mMediaPlayer.setOnPreparedListener(this);
		        mMediaPlayer.setOnVideoSizeChangedListener(this);
		        mMediaPlayer.setOnErrorListener(this);
		        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		        mMediaPlayer.setScreenOnWhilePlaying(true);
		        mMediaPlayer.setWakeMode(this.getContext(), PowerManager.PARTIAL_WAKE_LOCK);
		        mMediaPlayer.prepareAsync();
    		} else {
    			if(mMediaPlayer.isPlaying())
    				mMediaPlayer.stop();
    			mMediaPlayer.reset();
    			mMediaPlayer.setDataSource(path);
    			mMediaPlayer.setDisplay(mHolder);
    			mMediaPlayer.prepareAsync();
    		}
    		Log.v(MobiUtil.TAG, "MediaPlayer preparing");
    		
    	} catch(Exception e) {
    		Log.e(MobiUtil.TAG, "MediaPlayer creation failed: " + e.getMessage());
    		return false;
    	}
    	
    	return true;
		
	}
    
    public void releaseMediaPlayer() {
    	Log.v(MobiUtil.TAG, "release media player");
        if (mMediaPlayer != null) {
        	if(mMediaPlayer.isPlaying())
        		mMediaPlayer.stop();
        	mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void doCleanUp() {
        
    }

    public void startVideoPlayback() {
        Log.v(MobiUtil.TAG, "startVideoPlayback");
        //if(mVideoHeight != 0 && mVideoWidth != 0)
        	//this.mHolder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }

    public void pauseVideoPlayback() {
        Log.v(MobiUtil.TAG, "pauseVideoPlayback");
        mMediaPlayer.pause();
    }
    
    public void stopVideoPlayback() {
        Log.v(MobiUtil.TAG, "stopVideoPlayback");
        if(mMediaPlayer != null)
        	if(mMediaPlayer.isPlaying())
        		mMediaPlayer.stop();
    }

	@Override
	public void setController(MobiViewManager ctrl) {
		this.mController = ctrl;
	}

	@Override
	public void destroy() {
		Log.v(MobiUtil.TAG, "destroy");
		this.releaseMediaPlayer();
	}

	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		Log.v(MobiUtil.TAG, "on Info");
		return false;
	}
	
}
