package com.arzen.watcher.directives;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.directives.msg.Alert;
import com.arzen.watcher.directives.msg.Notice;
import com.arzen.watcher.directives.shortcut.Download;
import com.arzen.watcher.directives.shortcut.Runapk;
import com.arzen.watcher.directives.shortcut.Url;

import android.content.Context;

/**
 * 消息提示
 * 
 * @author Encore
 * 
 */
public class Msg extends WatcherAction {
	/**
	 * 在顶部的通知栏显示通知
	 */
	public static final String ACTION_NOTICE = "notice";
	/**
	 * 在桌面弹出一个对话框
	 */
	public static final String ACTION_ALERT = "alert";
	
	
	public Msg(Context context, String directivesStr) {
		mContext = context;
	}


	@Override
	protected void assignTasks() {
		// TODO Auto-generated method stub
		String directivesActionString = getDirectivesAction(); // 获取当前执行的指令动作
		if (directivesActionString != null) {
			DirectivesAction directivesAction = null;
			if (directivesActionString.equals(ACTION_NOTICE)) {
				directivesAction = new Notice();
			} else if (directivesActionString.equals(ACTION_ALERT)) {
				directivesAction = new Alert();
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
