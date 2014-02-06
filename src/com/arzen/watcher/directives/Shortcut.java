package com.arzen.watcher.directives;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.directives.shortcut.Download;
import com.arzen.watcher.directives.shortcut.Runapk;
import com.arzen.watcher.directives.shortcut.Url;

import android.content.Context;

/**
 * 创建指定快捷方式到桌面
 * 
 * @author Encore
 * 
 */
public class Shortcut extends WatcherAction {
	/**
	 * 创建打开指定APK的action关键字
	 */
	public static final String ACTION_RUNAPK = "runapk";
	/**
	 * 创建打开指定url的action关键字
	 */
	public static final String ACTION_URL = "url";
	/**
	 * 创建下载指定APK的桌面快捷方式
	 */
	public static final String ACTION_DOWNLOAD = "download";


	public Shortcut(Context context, String directivesStr) {
		mContext = context;
	}

	@Override
	protected void assignTasks() {
		String directivesActionString = getDirectivesAction(); // 获取当前执行的指令动作
		if (directivesActionString != null) {
			DirectivesAction directivesAction = null;
			if (directivesActionString.equals(ACTION_URL)) {
				directivesAction = new Url();
			} else if (directivesActionString.equals(ACTION_RUNAPK)) {
				directivesAction = new Runapk();
			} else if (directivesActionString.equals(ACTION_DOWNLOAD)) {
				directivesAction = new Download();
			}
			if (directivesAction != null) {
				mDirectivesAction = directivesAction;
			}
		}
	}
	/**
	 * 执行指令
	 */
	@Override
	public void executionDirectivies() {
		super.executionDirectivies();
		if (mDirectivesAction != null) {
			mDirectivesAction.executionDirectivies(mContext, mDirectives);
		}
	}

}
