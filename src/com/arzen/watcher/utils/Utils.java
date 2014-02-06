package com.arzen.watcher.utils;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;

import com.arzen.watcher.R;

public class Utils {
	/**
	 * 创建快捷方式
	 * @param context
	 * @param isUrl 是否打开url true 打开网页,false 打开apk
	 * @param action
	 */
	public static void createShortcut(Context context,boolean isUrl, String action) {
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.setClassName(context, context.getClass().getName());

		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		//快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "");
		//快捷方式动作
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

		context.sendBroadcast(shortcut);
	}
}
