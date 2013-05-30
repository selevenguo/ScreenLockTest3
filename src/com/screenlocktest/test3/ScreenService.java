package com.screenlocktest.test3;

import com.example.screenlocktest3.R;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

public class ScreenService extends Service {
	private ScreenReciever rec;
	private KeyguardManager km;
	private KeyguardLock kk;
	public static int pos;
	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("test", "ScreenService");
		// km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		// kk = km.newKeyguardLock("");
		// kk.disableKeyguard();

		rec = new ScreenReciever();
		rec.registerScreenActionReceiver(getApplicationContext());

		sp = getSharedPreferences("bacpos", MODE_PRIVATE);
		pos = sp.getInt("pos", 0);
		Constants.bp_lockbac = BitmapFactory.decodeResource(getResources(),
				R.drawable.lockbac);
		Constants.bp_lockbt = BitmapFactory.decodeResource(getResources(),
				R.drawable.lockbt);
		Constants.bp_lockpic = BitmapFactory.decodeResource(getResources(),
				R.drawable.lockpic);
		Constants.bp_lockbac1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.lockbac1);
		Constants.bp_appbt = BitmapFactory.decodeResource(getResources(),
				R.drawable.appbt);
		Constants.bp_appbt1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.appbt1);
		Constants.bp_title = BitmapFactory.decodeResource(getResources(),
				R.drawable.title);
		Constants.bp_appbac = BitmapFactory.decodeResource(getResources(),
				R.drawable.appbac);
		Constants.bp_appno = BitmapFactory.decodeResource(getResources(),
				R.drawable.appno);
		Constants.bp_appdown = BitmapFactory.decodeResource(getResources(),
				R.drawable.xia);
		Constants.bp_time[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t0);
		Constants.bp_time[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t1);
		Constants.bp_time[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t2);
		Constants.bp_time[3] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t3);
		Constants.bp_time[4] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t4);
		Constants.bp_time[5] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t5);
		Constants.bp_time[6] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t6);
		Constants.bp_time[7] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t7);
		Constants.bp_time[8] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t8);
		Constants.bp_time[9] = BitmapFactory.decodeResource(getResources(),
				R.drawable.t9);
		Constants.bp_time[10] = BitmapFactory.decodeResource(getResources(),
				R.drawable.tm);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		kk.reenableKeyguard();
		rec.unRegisterScreenActionReceiver(getApplicationContext());
	}
}
