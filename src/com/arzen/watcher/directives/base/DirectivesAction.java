package com.arzen.watcher.directives.base;

import android.content.Context;

public abstract class DirectivesAction {
	
	public Context mContext;
	
	/**
	 * 解析分隔符
	 */
	private static final String PARSE_SPLIT_STR = " ";
	/**
	 * 分割后的指令对象
	 */
	protected String[] mDirectives;
	
	
	/**
	 * 获取指令关键字
	 * @return shortuct,msg,info,mark,banner
	 */
	protected String getDirectivesKey(){
		if(mDirectives != null && mDirectives.length > 0){
			return mDirectives[0];
		}
		return null;
	}
	/**
	 * 获取应用的范围
	 * @return all or tag1,tag2,tag3
	 */
	protected String getRange()
	{
		if(mDirectives != null && mDirectives.length > 0){
			return mDirectives[1];
		}
		return null;
	}
	
	
	/**
	 * 获取当前指令下需要执行的动作
	 * @return runAPK,download,等
	 */
	protected String getDirectivesAction()
	{
		if(mDirectives != null && mDirectives.length > 0){
			return mDirectives[2];
		}
		return null;
	}
	
	/**
	 * 获取当前指令下需要执行的参数
	 * @return 
	 */
	protected String getDirectivesParam()
	{
		if(mDirectives != null && mDirectives.length > 0){
			return mDirectives[3];
		}
		return null;
	}

	public void executionDirectivies(Context context,String[] directives){
		mContext = context;
		mDirectives = directives;
	};
}
