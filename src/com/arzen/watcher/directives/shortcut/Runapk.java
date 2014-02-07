package com.arzen.watcher.directives.shortcut;

import android.content.Context;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.utils.Utils;

public class Runapk extends DirectivesAction {

	@Override
	protected void executionDirectiviesRangeAll(Context context) {
		createShortcut(context);
	}

	@Override
	protected void executionDirectiviesRangeMark(Context context, boolean isMatching) {
		// TODO Auto-generated method stub
		if (isMatching) { // 已匹配
			createShortcut(context);
		}
	}

	/**
	 * 创建快捷方式
	 * 
	 * @param context
	 */
	public void createShortcut(Context context) {
		String action = getDirectivesParam();
		// 创建启动apk的快捷方式
		if (action != null && !action.equals("")) {
			Utils.createShortcut(context, false, action);
		}
	}

}
