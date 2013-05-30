package com.screenlocktest.test3;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.screenlocktest3.R;

public class SettingActivity extends Activity {
	private Button bt1, bt2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!isServiceRunning(this, "com.screenlocktest.test3.ScreenService")) {
			Log.i("test", "FullscreenActivity-first go service");
			Intent intent = new Intent();
			intent.setClass(this, ScreenService.class);
			startService(intent);
		}
		setContentView(R.layout.ui_setting);

		bt1 = (Button) findViewById(R.id.setting_bt1);
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainIntent1 = new Intent();
				mainIntent1.addCategory("android.intent.category.HOME");
				mainIntent1.setAction("android.intent.action.MAIN");
				ResolveInfo rs = getPackageManager().resolveActivity(
						mainIntent1, PackageManager.MATCH_DEFAULT_ONLY);
				if ("com.android.internal.app.ResolverActivity"
						.equals(rs.activityInfo.name)) {
					// 暂无
				} else {
					// 判断是不是我们公司的桌面
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					intent.setComponent(new ComponentName(
							rs.activityInfo.applicationInfo.packageName,
							rs.activityInfo.name));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					showInstalledAppDetails(SettingActivity.this,
							rs.activityInfo.applicationInfo.packageName);
				}
			}
		});
	}

	public static void showInstalledAppDetails(Context context,
			String packageName) {

		// Intent localIntent = new
		// Intent("android.settings.APPLICATION_DETAILS_SETTINGS",
		// Uri.fromParts("package",
		// ((ResolveInfo)AppsFragment.this.ress.get(paramAnonymousInt)).activityInfo.packageName,
		// null));
		// localIntent.setFlags(276824064);
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
			// 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		context.startActivity(intent);
	}

	private static final String SCHEME = "package";
	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
	 */
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
	 */
	private static final String APP_PKG_NAME_22 = "pkg";
	/**
	 * InstalledAppDetails所在包名
	 */
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	/**
	 * InstalledAppDetails类名
	 */
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	public static boolean isServiceRunning(Context context, String SERVICENAME) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (SERVICENAME.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
