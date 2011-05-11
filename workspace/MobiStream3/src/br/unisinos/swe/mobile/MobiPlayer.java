package br.unisinos.swe.mobile;

import br.unisinos.swe.mobile.interaction.DynamicOverlay;
import br.unisinos.swe.mobile.interaction.TopMenuBar;
import br.unisinos.swe.mobile.stream.StreamView;
import br.unisinos.swe.mobile.view.MainMenuBar;
import br.unisinos.swe.mobile.view.MobiViewManager;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;

public class MobiPlayer extends Activity {
	
	private MobiViewManager mController;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setTheme(R.style.Theme_Transparent);
        setContentView(R.layout.main);
        
        mController = new MobiViewManager(this, MobiPlayer.this);
        mController.setSurface((StreamView) findViewById(R.id.surface));
        mController.setMenu((MainMenuBar) findViewById(R.id.menu_touch));
        mController.setDynOverlay((DynamicOverlay) findViewById(R.id.dyn_overlay));
        mController.setTopMenu((TopMenuBar) findViewById(R.id.menu_top));
        
        getWindow().setFormat(PixelFormat.TRANSPARENT);
        //window.setFormat(PixelFormat.RGBA_8888);
        
    }
    @Override
    protected void onPause() {
        super.onPause();
        //mController.releaseVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.releaseVideo();
        mController.destroy();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	this.mController.reloadConfiguration();
    	this.mController.setMenuVisibility(true);
    }
    
    /*
    @Override 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
      super.onActivityResult(requestCode, resultCode, data); 
      //this.mController.reloadConfiguration();
    }
    */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.open:
            this.mController.openVideo();
            return true;
        case R.id.config:
        	this.mController.openConfig();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    

}