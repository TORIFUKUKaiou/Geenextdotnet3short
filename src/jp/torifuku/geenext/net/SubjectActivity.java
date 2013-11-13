package jp.torifuku.geenext.net;

import jp.torifuku.geenextdotnet3short.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class SubjectActivity extends ActionBarActivity {	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); /** くるくるのため */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        	super.setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar);
        }
        super.setContentView(R.layout.menu_webview);
        super.setProgressBarIndeterminateVisibility(false); /** for arrows tablet 起動時にくるくるが回り続ける問題の解決 */
        
        WebViewFragment webViewFrag = (WebViewFragment)
        getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
        webViewFrag.updateArticleView("http://geenext.net/category/short/");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int id = item.getItemId();
    	if (id == R.id.action_close) {
    		AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setMessage(R.string.q_close_all)
    			.setPositiveButton(R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SubjectActivity.super.finish();
					}})
    			.setNegativeButton(R.string.no, null)
    			.show();
    		return true;
    	} else if (id == R.id.action_share) {
    		WebViewFragment webViewFrag = (WebViewFragment)
    		        getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
    		if (webViewFrag == null) {
    			return true;
    		}
    		String[] info = webViewFrag.getInfo();
    		if (info == null) {
    			return true;
    		}
    		if (info.length < 2) {
    			return true;
    		}
    		String title = info[0];
    		String url = info[1];
    		if (url.startsWith("http://geenext.net")) {
    			title = "知恵袋の「短いやつ」";
    			url = "https://play.google.com/store/apps/details?id=jp.torifuku.geenextdotnet3short";
    		}
    		
    		Intent intent = new Intent();
    		intent.setAction(Intent.ACTION_SEND);
    		intent.setType("text/plain");
    		intent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
            super.startActivity(intent);
            return true;
    	}
    	/* else if (id == R.id.action_manaberu) {
    		TorifukuPackageManager pm = new TorifukuPackageManager(this);
    		final String packageName = "jp.torifuku.geenext.net";
    		boolean install = pm.isInstalled(packageName);
    		if (install) {
    			Intent intent = new Intent(Intent.ACTION_MAIN);
    			intent.setClassName(packageName, packageName + ".SubjectActivity");
    			super.startActivity(intent);
    		} else {
    			pm.showInstallDialog(this, packageName, super.getString(R.string.q_install_manaberu));
    		}
    		return true;
    	}*/
    	
    	return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onBackPressed() {
		// Capture the article fragment from the activity layout
        WebViewFragment webViewFrag = (WebViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
        
        boolean handle = false;
        
        if (webViewFrag != null) {
        	// タブレットの場合
        	handle = webViewFrag.back();
        }
		
        // WebViewでBackをハンドルしないときは、Super classの処理
        if (!handle) {
        	super.onBackPressed();
        }
	}
}
