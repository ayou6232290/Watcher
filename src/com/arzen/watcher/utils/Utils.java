package com.arzen.watcher.utils;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.arzen.watcher.directives.Shortcut;
import com.arzen.watcher.manager.AppInfoProvider;

public class Utils {
	/**
	 * 创建快捷方式
	 * 
	 * @param context
	 * @param isUrl
	 *            是否打开url true 打开网页,false 打开apk
	 * @param action
	 */
	public static void createShortcut(final Context context, final boolean isUrl, final String action) {
		new Thread() {
			public void run() {
				Log.e("Shortcut", "createShortcut()");
				
				Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
				Intent shortcutIntent = null;
				HashMap<String, PackageInfo> maps = AppInfoProvider.getIntance().getAllPackageInfos(context);
				if (maps == null) {
					return;
				}
				
				if (isUrl) { // 当前动作是,打开网页
					Uri uri = Uri.parse(action);
					shortcutIntent = new Intent(Intent.ACTION_VIEW, uri);
					// 快捷方式的名称
					shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "打开网页");
					int iconResId = android.R.drawable.sym_def_app_icon;
					// 快捷方式的图标
					ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, iconResId);
					shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
				} else { // 打开apk
					PackageManager packageManager = context.getPackageManager();
					if (maps.containsKey(action)) {
						PackageInfo packageInfo = maps.get(action);

						shortcutIntent = packageManager.getLaunchIntentForPackage(action);
						
						Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
						Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
						// 快捷方式的图标
						shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);
						// 快捷方式的名称
						shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,  packageInfo.applicationInfo.loadLabel(packageManager).toString());
					}
					if (shortcutIntent == null) {
						Log.e("Shortcut", "made launcheIntent is null!");
						return;
					}
					
				}
				
				// 快捷方式动作
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
				context.sendBroadcast(shortcut);
			};
		}.start();

	}
}
