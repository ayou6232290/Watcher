package com.arzen.watcher.directives.mark;

import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.manager.AppInfoProvider;
import com.arzen.watcher.manager.MarkManager;
/**
 * 将安装了指定的包名应用的设备打个标签为"tag1"  例:mark tag1 installed com.game1.g2
 * @author Encore.liang
 *
 */
public class Installed extends DirectivesAction {
	

	@Override
	protected void executionDirectiviesRangeAll(Context context) {
		saveMark(context);
	}

	@Override
	protected void executionDirectiviesRangeMark(Context context, boolean isMatching) {
		// TODO Auto-generated method stub
		if(!isMatching){ //当前是设置标签, 当前得到结果是,本地没有这个标签的记录,需要保存这个标签
			saveMark(context);
		}
	}
	/**
	 * 保存标签,先判断当前应用是否有这个包名,如果有再保存
	 * @param context
	 */
	public void saveMark(Context context)
	{
		try {
			String pkg = getDirectivesParam(); //得到需要匹配的包名
			String mark = getRange(); //得到范围值
			HashMap<String, PackageInfo> maps = AppInfoProvider.getIntance().getAllPackageInfos(context);
			if (maps != null && maps.containsKey(pkg)) { //判断当前应用是否有这个包名,有再保存当前标签
				MarkManager.getIntance().saveMark(context, mark);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
