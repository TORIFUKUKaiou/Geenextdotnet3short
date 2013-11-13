package jp.torifuku.geenext.net.model;

import java.util.ArrayList;
import java.util.List;

/**
 * MyData
 * 
 * @author torifuku.kaiou
 *
 */
public class MyData extends ArrayList<Subject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6660659587045588608L;
	
	public String[] getSubjectNames() {
		if (super.size() <= 0) {
			return null;
		}
		
		String[] subjectNames = new String[super.size()];
		int i = 0;
		for (Subject s : this) {
			subjectNames[i] = s.getName();
			i++;
		}
		return subjectNames;
	}
	
	public String[] getSubcategoryTitles(int index) {
		Subject subject = super.get(index);
		List<MyMenu> list = subject.getMenuList();
		String[] titles = new String[list.size()];
		int i = 0;
		for (MyMenu s : list) {
			titles[i] = s.getTitle();
			i++;
		}
		return titles;
	}
	
	public String[] getSubcategoryUrls(int index) {
		Subject subject = super.get(index);
		List<MyMenu> list = subject.getMenuList();
		String[] urls = new String[list.size()];
		int i = 0;
		for (MyMenu s : list) {
			urls[i] = s.getUrl();
			i++;
		}
		return urls;
	}
}
