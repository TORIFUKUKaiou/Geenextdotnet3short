package jp.torifuku.geenext.net;

import jp.torifuku.geenextdotnet3short.BuildConfig;
import net.nend.android.NendAdListener;
import net.nend.android.NendAdView;
import net.nend.android.NendAdView.NendError;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

public class AdManager {
	public static void adShow(final AdView adView, NendAdView nendAdView) {
		int ran = (int) ((Math.random() * 10) % 2);
		if (BuildConfig.DEBUG) {
			Log.i("TEST", "ran: " + ran);
			Toast.makeText(adView.getContext(), "ran: " + ran, Toast.LENGTH_SHORT).show();
		}
		if (ran == 0) {
			// AdMob表示
			showAdmob(adView, nendAdView);
		} else {
			// Nend表示
			showNend(nendAdView, adView);
		}
	}
	
	private static void showNend(NendAdView nendAdView, final AdView adView) {
		if (nendAdView == null) {
			return;
		}
		nendAdView.setVisibility(View.VISIBLE);
		NendAdListener listener = new NendAdListener() {
			@Override
			public void onClick(NendAdView arg0) {
				if (BuildConfig.DEBUG) Log.i("TEST", "onClick");
			}

			@Override
			public void onDismissScreen(NendAdView arg0) {
				if (BuildConfig.DEBUG) Log.i("TEST", "onDismissScreen");
			}

			@Override
			public void onFailedToReceiveAd(NendAdView arg0) {
				if (BuildConfig.DEBUG) {
					Log.i("TEST", "onFailedToReceiveAd");
					Toast.makeText(arg0.getContext(), "onFailedToReceiveAd", Toast.LENGTH_SHORT).show();
					NendError nendError = arg0.getNendError();
					// エラーメッセージをログに出力
					Log.e("TEST", nendError.getMessage());
				}
				arg0.setVisibility(View.GONE);
				if (adView != null) {
					showAdmob(adView, null);
				}
			}

			@Override
			public void onReceiveAd(NendAdView arg0) {
				if (BuildConfig.DEBUG) {
					Log.i("TEST", "onReceiveAd");
					Toast.makeText(arg0.getContext(), "Nend", Toast.LENGTH_SHORT).show();
				}
			}};
		nendAdView.setListener(listener);
		nendAdView.loadAd();
	}
	
	private static void showAdmob(final AdView adView, final NendAdView nendAdView) {
		if (adView == null) {
			return;
		}
		adView.setVisibility(View.VISIBLE);
		AdListener adListener = new AdListener() {
			@Override
			public void onDismissScreen(Ad arg0) {
			}

			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				adView.setVisibility(View.GONE);
				if (nendAdView != null) {
					showNend(nendAdView, null);
				}
			}

			@Override
			public void onLeaveApplication(Ad arg0) {
			}

			@Override
			public void onPresentScreen(Ad arg0) {
			}

			@Override
			public void onReceiveAd(Ad arg0) {
				if (BuildConfig.DEBUG) {
					Toast.makeText(adView.getContext().getApplicationContext(), "Admob", Toast.LENGTH_SHORT).show();
				}
			}};
		adView.setAdListener(adListener);
		adView.loadAd(new AdRequest());
	}
}
