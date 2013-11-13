package jp.torifuku.geenext.net.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMenu implements Parcelable {
	private String mTitle;
	private String mUrl;

	public static final Parcelable.Creator<MyMenu> CREATOR
		= new Parcelable.Creator<MyMenu>() {
		public MyMenu createFromParcel(Parcel in) {
			return new MyMenu(in);
		}

		public MyMenu[] newArray(int size) {
			return new MyMenu[size];
		}
	};
	
	/**
	 * ctor
	 * 
	 * @param title
	 * @param url
	 */
	public MyMenu(String title, String url) {
		mTitle = title;
		mUrl = url;
	}
	
	/**
	 * ctor
	 * 
	 * @param in
	 */
	private MyMenu(Parcel in) {
		mTitle = in.readString();
		mUrl = in.readString();
	}
	
	/**
	 * getTitle
	 * 
	 * @return
	 */
	public String getTitle() {
		return mTitle;
	}
	
	/**
	 * getTitle
	 * 
	 * @return
	 */
	public String getUrl() {
		return mUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(mTitle);
		out.writeString(mUrl);
	}
}
