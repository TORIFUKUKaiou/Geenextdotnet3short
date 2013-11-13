package jp.torifuku.geenext.net.model;

import java.util.ArrayList;

/**
 * Subject
 * 
 * @author torifuku.kaiou
 *
 */
public class Subject {

	private String mName;
	private ArrayList<MyMenu> mMenuList = new ArrayList<MyMenu>();
	
	
	void setName(String name) {
		mName = name;
	}
	public String getName() {
		return mName;
	}
	void setMenuList(MyMenu menu) {
		mMenuList.add(menu);
	}
	public ArrayList<MyMenu> getMenuList() {
		return mMenuList;
	}
}
