package com.arzen.watcher.directives.msg;

import java.util.HashMap;
import java.util.Random;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arzen.watcher.directives.base.DirectivesAction;
import com.arzen.watcher.manager.AppInfoProvider;
import com.arzen.watcher.utils.DensityUtil;

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

			boolean isToWeb = true; // 是否跳转到浏览器
			HashMap<String, PackageInfo> maps = null;
			if (type.equals(MSG_TYPE_APK)) {
				maps = AppInfoProvider.getIntance().getAllPackageInfos(context);

				if (maps != null && maps.containsKey(paramContent)) { // 如果当前用户存在这个应用
					isToWeb = false;
				} else {
					paramContent = mDirectives[MSG_PARAM_URL];
				}
			} else { // 跳转网页
				isToWeb = true;
			}

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
			mBuilder.setSmallIcon(android.R.drawable.ic_dialog_email);
			mBuilder.setContentTitle("消息提示");
			mBuilder.setContentText(title);
			mBuilder.setTicker(title);
			mBuilder.setAutoCancel(true);// 自己维护通知的消失

			Intent intent = null;
			if (isToWeb) {
				Uri uri = Uri.parse(paramContent);
				intent = new Intent(Intent.ACTION_VIEW, uri);
			} else {
				PackageManager packageManager = context.getPackageManager();
				intent = packageManager.getLaunchIntentForPackage(paramContent);
			}
			PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// 设置通知主题的意图
			mBuilder.setContentIntent(resultPendingIntent);
			// 获取通知管理器对象
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(new Random().nextInt(99999), mBuilder.build());

			// 创建悬浮窗口消息提示
			createFloatView(context, title);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 创建悬浮窗口
	 */
	public void createFloatView(Context context, String content) {

		LinearLayout.LayoutParams msgLp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		msgLp1.leftMargin = DensityUtil.dip2px(context, 10);
		msgLp1.gravity = Gravity.CENTER_VERTICAL;

		LinearLayout llFloatView = new LinearLayout(context);// 容器
		llFloatView.setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout llParent = new LinearLayout(context);// 容器
		// 设置容器背景与透明度
		llParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		llParent.setBackgroundColor(Color.BLACK);
		llParent.getBackground().setAlpha(88);

		// 消息图标
		ImageView msgImageView = new ImageView(context);
		msgImageView.setImageResource(android.R.drawable.ic_dialog_email);
		msgImageView.setLayoutParams(msgLp1);

		// 消息view
		LinearLayout llMsgView = new LinearLayout(context);
		llMsgView.setOrientation(LinearLayout.VERTICAL);
		llMsgView.setLayoutParams(msgLp1);

		TextView tvTitle = new TextView(context);
		tvTitle.setText("消息提示");
		tvTitle.setTextColor(Color.WHITE);

		TextView tvContent = new TextView(context);
		tvContent.setText(content);
		tvContent.setTextColor(Color.WHITE);

		// 添加到父类容器
		llMsgView.addView(tvTitle);
		llMsgView.addView(tvContent);

		llParent.addView(msgImageView);// 添加图标
		llParent.addView(llMsgView); // 添加显示消息

		llFloatView.addView(llParent);

		// 显示消息通知
		showFloatView(context, llFloatView, llParent);
	}

	public void showFloatView(Context context, View floatView, final View amitionView) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();

		// 设置window type
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		// 设置Window flag
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = DensityUtil.dip2px(context, 50);
		params.gravity = Gravity.TOP;
		params.format = PixelFormat.TRANSLUCENT;
		wm.addView(floatView, params);

		new Handler().post(new Runnable() {

			@Override
			public void run() {
				amitionView.startAnimation(getEnterAnimtion(amitionView)); // 显示动画
			}
		});
	}

	/**
	 * 获取消息上面下来的动画
	 * 
	 * @param animtionView
	 * @return
	 */
	public TranslateAnimation getEnterAnimtion(final View animtionView) {
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f);

		translateAnimation.setDuration(1000);
		translateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				animtionView.startAnimation(getExitAnimation());
				animtionView.setVisibility(View.GONE);
			}
		});
		return translateAnimation;
	}

	/**
	 * 消息退出动画
	 * 
	 * @return
	 */
	public TranslateAnimation getExitAnimation() {
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);

		translateAnimation.setDuration(1000);
		translateAnimation.setStartOffset(2000);
		translateAnimation.setFillAfter(true);
		return translateAnimation;
	}
}
