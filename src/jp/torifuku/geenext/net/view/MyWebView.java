package jp.torifuku.geenext.net.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {

	public MyWebView(Context context) {
		super(context);
	}
	
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@SuppressWarnings("deprecation")
	public MyWebView(Context context, AttributeSet attrs, int defStyle,
			boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
	}

}
