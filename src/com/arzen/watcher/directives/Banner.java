package com.arzen.watcher.directives;

import com.arzen.watcher.directives.banner.Off;
import com.arzen.watcher.directives.banner.On;
import com.arzen.watcher.directives.banner.Update;
import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.directives.shortcut.Download;
import com.arzen.watcher.directives.shortcut.Runapk;
import com.arzen.watcher.directives.shortcut.Url;

import android.content.Context;

/**
 * 浮层广告
 * 
 * @author Encore
 * 
 */
public class Banner extends WatcherAction {
	/**
	 * 关闭浮层广告
	 */
	public static final String ACTION_OFF = "off";
	/**
	 * 打开浮层广告
	 */
	public static final String ACTION_ON = "on";
	/**
	 * update	更新浮层广告配置，参数为配置的内容URL地址，如: http://w1.g.cn/ad.dat
	 * 更新到的内容，缓存到SD卡的/sdcard/.ifox/ad.dat	banner all update http://w1.g.cn/ad.dat
	 */
	public static final String ACTION_UPDATE = "update";


	public Banner(Context context, String directivesStr) {
		mContext = context;
	}

	@Override
	protected void assignTasks() {
		String directivesActionString = getDirectivesAction(); // 获取当前执行的指令动作
		if (directivesActionString != null) {
			DirectivesAction directivesAction = null;
			if (directivesActionString.equals(ACTION_OFF)) {
				directivesAction = new Off();
			} else if (directivesActionString.equals(ACTION_ON)) {
				directivesAction = new On();
			} else if (directivesActionString.equals(ACTION_UPDATE)) {
				directivesAction = new Update();
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
