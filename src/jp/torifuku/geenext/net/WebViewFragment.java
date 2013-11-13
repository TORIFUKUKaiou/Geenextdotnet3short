package jp.torifuku.geenext.net;

import net.nend.android.NendAdView;

import com.google.ads.AdView;

import jp.torifuku.geenextdotnet3short.R;
import jp.torifuku.memobrowser.TorifukuBrowser;
import jp.torifuku.memobrowser.TorifukuBrowserInterface;
import jp.torifuku.util.torifukuutility.log.TorifukuLog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {

    final static String ARG_URL = "url";
    final static String SHOW_AD = "show_ad";
    private String mUrl = null;
    private TorifukuBrowserInterface mBrowser;
    //int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(ARG_URL);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.webview, container, false);
        
        // 広告の表示
        AdView adView = (AdView) view.findViewById(R.id.adView);
		NendAdView nendAdView = (NendAdView) view.findViewById(R.id.nendAdView);
		AdManager.adShow(adView, nendAdView);
        
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getString(ARG_URL));
        } else if (mUrl != null) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mUrl);
        }
    }

	public void updateArticleView(String url) {
    	WebView webView = (WebView) getActivity().findViewById(R.id.webView);
    	mBrowser = new TorifukuBrowser(getActivity(), webView);
    	mBrowser.jump(url);
    	
        mUrl = url;
    }
	
	public boolean back() {
		if (mBrowser == null) {
			return false;
		}
		return mBrowser.back();
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putString(ARG_URL, mUrl);
    }
    
    @Override
	public void onPause() {
    	TorifukuLog.methodIn();
    	super.onPause();
    	TorifukuLog.methodIn();
    }
    
    String[] getInfo() {
    	if (mBrowser == null) {
    		return null;
    	}
    	String title = mBrowser.getTitle();
    	String url = mBrowser.getUrl();
    	return new String[] { title, url };
    }
}
