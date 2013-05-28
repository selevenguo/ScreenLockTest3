package com.screenlocktest.test3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class StartServiceReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Log.i("test", "StartServiceReciever");
		Intent inte = new Intent();
		inte.setClass(context, ScreenService.class);
		context.startService(inte);
	}

}
