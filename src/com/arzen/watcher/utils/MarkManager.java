package com.arzen.watcher.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * 标签管理类
 * 
 * @author Encore.Liang
 * 
 */
public class MarkManager {
	private static final String SHARED_NAME = "mark";
	private static final String MARK_KEY = "markStr";
	private final String MARK_SPLIT = ",";
	//保存标签的集合
	private ArrayList<String> mMarkList = new ArrayList<String>();
	//保存标签的map集合
	private HashMap<String, String> mMarkMap = new HashMap<String, String>();
	
	private static MarkManager mMarkManager;

	public static MarkManager getIntance() {
		if (mMarkManager == null) {
			mMarkManager = new MarkManager();
		}
		return mMarkManager;
	}

	/**
	 * 保存标签
	 * 
	 * @param context
	 * @param mark
	 */
	public void saveMark(Context context, String mark) {
		ArrayList<String> markList = getAllMarkList(context);
		if(markList != null){
			if(markList.size() < 5){ //少于5条直接保存到列表里面
				markList.add(mark);
			}else{ //等于5条数据先进先出
				markList.remove(0);
				markList.add(mark);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		String marks = "";
		for (int i = 0; i < markList.size(); i++) { //循环拼接所有标签
			sb.append(markList.get(i)).append(MARK_SPLIT);
		}
		int lastIndex = sb.toString().lastIndexOf(MARK_SPLIT);
		marks = marks.toString().substring(0, lastIndex);
		
		SettingUtils settingUtils = new SettingUtils(context, SHARED_NAME, Context.MODE_PRIVATE);
		settingUtils.putString(MARK_KEY, marks);
		
		mMarkMap.clear();
		mMarkList.clear();
	}

	public HashMap<String, String> getAllMarkMap(Context context) {
		SettingUtils settingUtils = new SettingUtils(context, SHARED_NAME, Context.MODE_PRIVATE);
		String marksString =  settingUtils.getString(MARK_KEY, null);
		if(marksString != null){
			String[] marks = marksString.split(MARK_SPLIT);
			for (int i = 0; i < marks.length; i++) {
				String m = marks[i];
				mMarkMap.put(m, m);
			}
		}
		return mMarkMap;
	}
	/**
	 * 获取全部标签列表
	 * @param context
	 * @return
	 */
	public ArrayList<String> getAllMarkList(Context context) {
		SettingUtils settingUtils = new SettingUtils(context, SHARED_NAME, Context.MODE_PRIVATE);
		String marksString =  settingUtils.getString(MARK_KEY, null);
		if(marksString != null){
			String[] marks = marksString.split(MARK_SPLIT);
			mMarkList.toArray(marks);
		}
		return mMarkList;
	}

	public boolean isExistsMark(Context context,String mark) {
		SettingUtils settingUtils = new SettingUtils(context, SHARED_NAME, Context.MODE_PRIVATE);
		String marks =  settingUtils.getString(MARK_KEY, null);
		if(marks != null && marks.indexOf(mark) != -1){
			return true;
		}
		return false;
	}
}
