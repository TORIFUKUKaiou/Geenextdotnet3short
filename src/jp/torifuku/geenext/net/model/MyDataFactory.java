package jp.torifuku.geenext.net.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.torifuku.geenextdotnet3short.BuildConfig;
import jp.torifuku.util.torifukuutility.net.TorifukuHttpClient;
import jp.torifuku.util.torifukuutility.net.UtilityJSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;

/**
 * MyDataFactory
 * 
 * @author torifuku.kaiou
 *
 */
public class MyDataFactory {
	private MyData mMyDataAsset = null;
	private AssetManager mAssetManager;

	/**
	 * ctor
	 * 
	 */
	public MyDataFactory(AssetManager assetManager) {
		mAssetManager = assetManager;
	}
	
	/**
	 * getMyData
	 * 
	 * @return
	 */
	public MyData getMyData() {
		// assetsからのデータ読み出しのみを対応
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String data = MyDataFactory.this.getAssetStringData(MyDataFactory.this.mAssetManager, "Chiebukuro_JSON_short.txt");
				if (data == null) {
					return;
				}
				mMyDataAsset = getMyData(data);
			}
		});
		thread.start();
		
		// Networkからの取得
		TorifukuHttpClient.Decoder decorder = new TorifukuHttpClient.Decoder() {
			@Override
			public Object decode(TorifukuHttpClient client,
				InputStream is) {
				return client.convertString(is, "UTF-8");
			}
		};
		TorifukuHttpClient client = new TorifukuHttpClient();
		String text = (String) client.getContent("http://www8.ocn.ne.jp/~tori29k0/data_torifuku/Chiebukuro_JSON_short.txt", decorder);
		if (BuildConfig.DEBUG) android.util.Log.d("TEST", text);

		MyData myData = null; 
		myData = this.getMyData(text);
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (myData != null) {
			return myData;
		} else {
			return mMyDataAsset;
		}
	}
	
	/**
	 * getMyData
	 * 
	 * @param rawData
	 * @return
	 */
	private MyData getMyData(String rawData) {
		/** java.lang.RuntimeException: An error occured while executing doInBackground()のための対策 */
		if (rawData == null) {
			return null;
		}
		MyData myData = null;
		
		try {
			JSONObject jsonObj = new JSONObject(rawData);
			JSONArray subjects = UtilityJSONObject.getJSONArray(jsonObj, "subjects");
			if (subjects == null) {
				return null;
			}
			myData = new MyData();
			for (int i = 0; i < subjects.length(); i++) {
				Subject subject = new Subject();
				
				JSONObject s = UtilityJSONObject.getJSONObject(subjects, i);
				if (s == null) {
					continue;
				}
				// subjectName
				String subjectName = UtilityJSONObject.getString(s, "subjectName");
				subject.setName(subjectName);
				
				// menuArray
				JSONArray menuArray = UtilityJSONObject.getJSONArray(s, "menuArray");
				if (menuArray == null) {
					continue;
				}
				for (int j = 0; j < menuArray.length(); j++) {
					JSONObject m = UtilityJSONObject.getJSONObject(menuArray, j);
					String menuTitle = m.getString("menuTitle");
					String menuUrl = m.getString("menuUrl");
					MyMenu menu = new MyMenu(menuTitle, menuUrl);
					
					subject.setMenuList(menu);
				}
				myData.add(subject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return myData;
	}
	
	/**
	 * getAssetStringData
	 * 
	 * @param assetManager
	 * @param fileName
	 * @return
	 */
	private String getAssetStringData(AssetManager assetManager, String fileName) {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			is = assetManager.open(fileName);
			//if (is != null) Log.d("TEST", "is != null");
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);

			String line;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			//Log.d("TEST", sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
				if (isr != null) isr.close();
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (sb != null) {
			return sb.toString();
		} else {
			return null;
		}
	}
}
