package com.arzen.watcher.directives;

import com.arzen.watcher.directives.banner.Off;
import com.arzen.watcher.directives.banner.On;
import com.arzen.watcher.directives.banner.Update;
import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.directives.mark.Define;
import com.arzen.watcher.directives.mark.Installed;
import com.arzen.watcher.directives.mark.Leave;

import android.content.Context;

/**
 * 为设备大标签并上报符合条件的设备数量
 * 
 * @author Encore
 * 
 */
public class Mark extends WatcherAction {
	/**
	 * 将当前设备打个标签为 tag1
	 */
	public static final String ACTION_DEFINE = "define";
	/**
	 * 将安装了指定的包名应用的设备打个标签为tag1
	 */
	public static final String ACTION_INSTALLED = "installed";
	/**
	 * 将指定的包名应用且有7天没打开该应用的设备打个标签为tag1
	 */
	public static final String ACTION_LEAVE = "leave";


	public Mark(Context context, String directivesStr) {
		mContext = context;
	}

	@Override
	protected void assignTasks() {
		String directivesActionString = getDirectivesAction(); // 获取当前执行的指令动作
		if (directivesActionString != null) {
			DirectivesAction directivesAction = null;
			if (directivesActionString.equals(ACTION_DEFINE)) {
				directivesAction = new Define();
			} else if (directivesActionString.equals(ACTION_INSTALLED)) {
				directivesAction = new Installed();
			} else if (directivesActionString.equals(ACTION_LEAVE)) {
				directivesAction = new Leave();
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
