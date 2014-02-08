package com.arzen.watcher.directives.msg;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.WindowManager;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.manager.AppInfoProvider;
/**
 * 在桌面弹出对话框消息
 * @author Encore.liang
 *
 */
public class Alert extends DirectivesAction {

	@Override
	protected void executionDirectiviesRangeAll(Context context) {
		// TODO Auto-generated method stub
		showAlert(context);
	}

	@Override
	protected void executionDirectiviesRangeMark(Context context, boolean isMatching) {
		// TODO Auto-generated method stub
		if (isMatching) {
			showAlert(context);
		}
	}

	private boolean mIsToWeb = true; // 是否跳转到浏览器
	private String mParamContent; // 需要执行的内容 apk 包名 or 浏览器网址

	/**
	 * 显示全局dialog
	 * 
	 * @param context
	 */
	public void showAlert(final Context context) {
		try {
			String title = mDirectives[MSG_TITLE];
			String type = mDirectives[MSG_TYPE];
			mParamContent = mDirectives[MSG_PARAM];

			mIsToWeb = true;
			HashMap<String, PackageInfo> maps = null;
			if (type.equals(MSG_TYPE_APK)) { // 判断当前类型是打开apk,还是网页
				maps = AppInfoProvider.getIntance().getAllPackageInfos(context);
				if (maps != null && maps.containsKey(mParamContent)) { // 如果当前用户存在这个应用
					mIsToWeb = false; // 打开当前应用
				} else {
					mParamContent = mDirectives[MSG_PARAM_URL];
				}
			} else { // 跳转网页
				mIsToWeb = true;
			}

			Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("消息提示");
			builder.setMessage(title);
			builder.setNegativeButton("取消", null);
			builder.setPositiveButton("打开", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dispose(context, mIsToWeb, mParamContent);
				}
			});
			final AlertDialog dialog = builder.create();
		    dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)); //系统级对话框
		    dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理
	 * 
	 * @param context
	 * @param isToWeb
	 * @param paramContent
	 */
	public void dispose(Context context, boolean isToWeb, String paramContent) {
		Intent intent = null;
		if (isToWeb) {
			Uri uri = Uri.parse(paramContent);
			intent = new Intent(Intent.ACTION_VIEW, uri);
		} else {
			PackageManager packageManager = context.getPackageManager();
			intent = packageManager.getLaunchIntentForPackage(paramContent);
		}
		if (intent != null) {
			context.startActivity(intent);
		}
	}
}
