package com.arzen.watcher.directives.mark;

import android.content.Context;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.utils.Log;
import com.arzen.watcher.utils.MarkManager;
/**
 * 为设备打上标签
 * 得到指令:mark tag1 define,将当前设备打个标签为 "tag1" 
 * @author Encore.Liang
 *
 */
public class Define extends DirectivesAction {
	
	@Override
	public void executionDirectivies(Context context, String[] directives) {
		super.executionDirectivies(context, directives);
		//获取需要保存的标签
		String mark = getRange();
		Log.d(TAG, "Define save mark:" + mark);
		if(mark != null && !mark.equals("")){
			MarkManager.getIntance().saveMark(context, mark); //保存标签
		}
	}
}
