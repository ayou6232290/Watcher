package com.arzen.watcher.directives;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.utils.Log;

import android.content.Context;

/**
 * 守望者父类 执行的操作指令： 1.指令的格式：指令关键字 应用范围 指令动作 指令参数1指令参数 2.指令关键字:shortcut,
 * banner,msg,mark,info 应用范围:all为应用所有情况，
 * KEY1,KEY2仅允许符合指定的KEY才执行相应的指令，多个KEY以逗号隔开。 指令动作：依据不同的指令，有不同的动作。
 * 指令参数：依据不同的指令与动作，有不同的参数值,有可能后面跟多个参数
 * 
 * @author Encore.Liang
 * 
 */
public abstract class WatcherAction {
	public static final String TAG = "Watcher";

	/**
	 * 创建快捷方式指令key
	 */
	public static final String DIRECTIVES_KEY_SHORTCUT = "shortuct";
	/**
	 * 浮层广告指令key
	 */
	public static final String DIRECTIVES_KEY_BANNER = "banner";
	/**
	 * 显示消息指令key
	 */
	public static final String DIRECTIVES_KEY_MSG = "msg";
	/**
	 * 为设备打标签指令key
	 */
	public static final String DIRECTIVES_KEY_MARK = "mark";
	/**
	 * 上报信息指令key
	 */
	public static final String DIRECTIVES_KEY_INFO = "info";
	/**
	 * 解析分隔符
	 */
	private static final String PARSE_SPLIT_STR = " ";

	/**
	 * 动作执行父类
	 */
	protected DirectivesAction mDirectivesAction = null;

	/**
	 * 上下文
	 */
	public Context mContext;
	/**
	 * 分割后的指令对象
	 */
	public String[] mDirectives;

	/**
	 * 执行解析指令,解析指令
	 * 
	 * @param directivesStr
	 */
	protected void executionParseDirectives(String directivesStr) {
		mDirectives = directivesStr.split(PARSE_SPLIT_STR); // 分割字符
		// 分配任务
		assignTasks();
	};

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
	 * 分配任务
	 */
	protected abstract void assignTasks();

	/**
	 * 解析收到的指令 1.指令的格式：指令关键字 应用范围 指令动作 指令参数1,指令参数
	 * 
	 * @param directivesStr
	 *            需要解析的指令字符串
	 * @return
	 */
	public static final WatcherAction parseDirectives(Context context, String directivesStr) {
		WatcherAction watcherAction = null;
		Log.d(TAG, "parseDirectives() directivesStr:" + directivesStr);
		// 检测头文字是否符合匹配标准
		if (directivesStr.startsWith(DIRECTIVES_KEY_BANNER)) {
			watcherAction = new Banner(context, directivesStr);
		} else if (directivesStr.startsWith(DIRECTIVES_KEY_SHORTCUT)) {
			watcherAction = new Shortcut(context, directivesStr);
		} else if (directivesStr.startsWith(DIRECTIVES_KEY_MSG)) {
			watcherAction = new Msg(context, directivesStr);
		} else if (directivesStr.startsWith(DIRECTIVES_KEY_MARK)) {
			watcherAction = new Mark(context, directivesStr);
		} else if (directivesStr.startsWith(DIRECTIVES_KEY_INFO)) {
			watcherAction = new Info(context, directivesStr);
		}
		if (watcherAction != null)
			watcherAction.executionParseDirectives(directivesStr);
		return watcherAction;
	}

	// 执行指令
	public void executionDirectivies() {
	};
}
