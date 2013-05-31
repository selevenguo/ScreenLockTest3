package com.screenlocktest.test3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ScreenReciever extends BroadcastReceiver {
	private boolean isreceber = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_SCREEN_ON)) {
			Log.i("test", "aaaa");
			CacheState.isLockScreen = true;
			Intent it = new Intent();
			it.setClass(context, FullscreenActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(it);
			abortBroadcast();
		} else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
			if (FullscreenActivity.act != null) {
				CacheState.isLockScreen = false;
				FullscreenActivity.act.finish();
			}
			Log.i("test", "bbbb");
			abortBroadcast();
		}
	}

	public void registerScreenActionReceiver(Context mContext) {
		if (!isreceber) {
			isreceber = true;
			Log.i("test", "register");
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			filter.addAction(Intent.ACTION_SCREEN_ON);
			filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			mContext.registerReceiver(ScreenReciever.this, filter);
		}
	}

	public void unRegisterScreenActionReceiver(Context mContext) {
		if (isreceber) {
			isreceber = false;
			mContext.unregisterReceiver(ScreenReciever.this);
		}
	}
}
