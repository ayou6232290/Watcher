package com.arzen.watcher.directives;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.directives.info.Devices;
import com.arzen.watcher.directives.info.Package;
import com.arzen.watcher.directives.msg.Alert;
import com.arzen.watcher.directives.msg.Notice;

import android.content.Context;

public class Info extends WatcherAction {
	
	/**
	 * 上传设备信息。包含：设备品牌、型号、分辨率、CPU，各指标以逗号分隔；
	 */
	public static final String ACTION_DEVICES = "devices";
	/**
	 * 将设备安装的所有应用进行上传。
	 */
	public static final String ACTION_PACKAGE = "package";
	
	
	public Info(Context context, String directivesStr) {
		mContext = context;
	}


	@Override
	protected void assignTasks() {
		// TODO Auto-generated method stub
		String directivesActionString = getDirectivesAction(); // 获取当前执行的指令动作
		if (directivesActionString != null) {
			DirectivesAction directivesAction = null;
			if (directivesActionString.equals(ACTION_DEVICES)) {
				directivesAction = new Devices();
			} else if (directivesActionString.equals(ACTION_PACKAGE)) {
				directivesAction = new Package();
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
