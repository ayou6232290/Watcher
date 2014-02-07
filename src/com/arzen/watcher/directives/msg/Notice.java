package com.arzen.watcher.directives.msg;

import java.util.HashMap;
import java.util.Random;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.manager.AppInfoProvider;

/**
 * 显示通知栏消息 msg all notice 标题 url http://w1.g.cn/dd.html
 * 
 * @author Encore.liang
 * 
 */
public class Notice extends DirectivesAction {

	@Override
	protected void executionDirectiviesRangeAll(Context context) {
		// TODO Auto-generated method stub
		showNotice(context);
	}

	@Override
	protected void executionDirectiviesRangeMark(Context context, boolean isMatching) {
		// TODO Auto-generated method stub
		if (isMatching) {
			showNotice(context);
		}
	}

	/**
	 * 显示通知
	 * 
	 * @param context
	 */
	public void showNotice(Context context) {
		try {
			String title = mDirectives[MSG_TITLE];
			String type = mDirectives[MSG_TYPE];
			String paramContent = mDirectives[MSG_PARAM];
			
			Intent intent = null;
			boolean isToWeb = true; //是否跳转到浏览器
			HashMap<String, PackageInfo> maps = null;
			if(type.equals(MSG_TYPE_APK)){
				maps = AppInfoProvider.getIntance().getAllPackageInfos(context);
				
				if(maps != null && maps.containsKey(paramContent)){ //如果当前用户存在这个应用
					isToWeb = false;
				}else{
					paramContent = mDirectives[MSG_PARAM_URL];
				}
			}else{ //跳转网页
				isToWeb = true;
			}
			
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
			mBuilder.setSmallIcon(android.R.drawable.ic_dialog_email);
			mBuilder.setContentTitle("消息提示");
			mBuilder.setContentText(title);
			mBuilder.setTicker(title);
			mBuilder.setAutoCancel(true);// 自己维护通知的消失
			
			if(isToWeb){
				Uri uri = Uri.parse(paramContent);
				intent = new Intent(Intent.ACTION_VIEW, uri);
			}else{
				if(maps != null){ //如果是
					PackageManager packageManager = context.getPackageManager();
					intent = packageManager.getLaunchIntentForPackage(paramContent);
				}
			}
			PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			 // 设置通知主题的意图
			mBuilder.setContentIntent(resultPendingIntent);
			//获取通知管理器对象
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(new Random().nextInt(99999), mBuilder.build());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
