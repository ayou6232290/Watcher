package com.arzen.watcher.directives.base;

import com.arzen.watcher.manager.MarkManager;
import com.arzen.watcher.utils.Utils;

import android.content.Context;

public abstract class DirectivesAction {

	public static final String TAG = "DirectivesAction";
	// 当前范围全部
	public static final String RANGE_ALL = "all";
	/**
	 * msg消息坐标标题
	 */
	public static final int MSG_TITLE = 3;
	/**
	 * msg消息类型  url or apk 
	 */
	public static final int MSG_TYPE = 4;
	/**
	 * msg消息执行参数
	 */
	public static final int MSG_PARAM = 5;
	/**
	 * msg apk 消息url坐标
	 */
	public static final int MSG_PARAM_URL = 6;
	/**
	 * 消息提示类型 url
	 */
	public static final String MSG_TYPE_URL = "url";
	/**
	 * 消息提示类型 apk
	 */
	public static final String MSG_TYPE_APK = "apk";

	public Context mContext;

	/**
	 * 解析分隔符
	 */
	protected static final String PARSE_SPLIT_STR = ",";
	/**
	 * 分割后的指令对象
	 */
	protected String[] mDirectives;

	/**
	 * 获取指令关键字
	 * 
	 * @return shortuct,msg,info,mark,banner
	 */
	protected String getDirectivesKey() {
		if (mDirectives != null && mDirectives.length > 0) {
			return mDirectives[0];
		}
		return null;
	}

	/**
	 * 获取应用的范围
	 * 
	 * @return all or tag1,tag2,tag3
	 */
	protected String getRange() {
		if (mDirectives != null && mDirectives.length > 0) {
			return mDirectives[1];
		}
		return null;
	}

	/**
	 * 获取当前指令下需要执行的动作
	 * 
	 * @return runAPK,download,等
	 */
	protected String getDirectivesAction() {
		if (mDirectives != null && mDirectives.length > 0) {
			return mDirectives[2];
		}
		return null;
	}

	/**
	 * 获取当前指令下需要执行的参数
	 * 
	 * @return
	 */
	protected String getDirectivesParam() {
		if (mDirectives != null && mDirectives.length > 0) {
			return mDirectives[3];
		}
		return null;
	}

	/**
	 * 执行指令
	 * 
	 * @param context
	 * @param directives
	 */
	public void executionDirectivies(Context context, String[] directives) {
		mContext = context;
		mDirectives = directives;

		// 获取当前应用范围
		String range = getRange();
		// 应用范围全部
		if (range.equals(RANGE_ALL)) {
			executionDirectiviesRangeAll(context);
		} else {
			String[] tags = range.split(PARSE_SPLIT_STR); // 得到需要执行的标签
			boolean isMatching = MarkManager.getIntance().isMatching(context, tags);
			executionDirectiviesRangeMark(context, isMatching);
		}
	};

	/**
	 * 执行指令,范围是 all
	 * 
	 * @param context
	 */
	protected abstract void executionDirectiviesRangeAll(Context context);

	/**
	 * 执行指令,范围是有标签的,必须在当前标签下的机器才执行此动作
	 * tag1,tag2
	 * @param context
	 * @param isMatching 是否匹配当前标签
	 */
	protected abstract void executionDirectiviesRangeMark(Context context, boolean isMatching);
}
