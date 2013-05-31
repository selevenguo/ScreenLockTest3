package com.screenlocktest.test3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.example.screenlocktest3.R;
import com.screenlocktest.test3.MyView.On2SLockListener;
import com.screenlocktest.test3.MyView.OnAppClickListener;
import com.screenlocktest.test3.MyView.OnLockListener;
import com.screenlocktest.test3.MyView.OnLongClickListener;

@SuppressLint("NewApi")
public class FullscreenActivity extends Activity {
	public static FullscreenActivity act;
	private LockLayer lockLayer;
	private View view;
	private MyView slv;
	private int ho, mi, y, m, d;
	private SharedPreferences sp;
	private int num;
	private List<Drawable> lsd;
	private List<String> lsn;
	private boolean first = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sp = getSharedPreferences("applist", MODE_PRIVATE);
		packageManager = getPackageManager();
		String pacname = sp.getString("luncherpacname", null);
		if (pacname == null) {
			Intent intent = new Intent();
			intent.setClass(FullscreenActivity.this, LuncherListActivity.class);
			startActivity(intent);
			FullscreenActivity.this.finish();
		} else {
			if (!CacheState.isLockScreen) {
				Intent intent = packageManager
						.getLaunchIntentForPackage(pacname);
				startActivity(intent);
				FullscreenActivity.this.finish();
			}
		}

		// setContentView(R.layout.activity_main);
		act = this;

		// 设置锁屏窗体
		view = getLayoutInflater().inflate(R.layout.activity_main, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		lockLayer = new LockLayer(this);
		lockLayer.setLockView(view);
		lockLayer.lock();

		// setContentView(view);

		slv = (MyView) view.findViewById(R.id.mv);
		slv.setOll(new OnLockListener() {
			@Override
			public void onlock() {
				// TODO Auto-generated method stub
				CacheState.isLockScreen = false;
				FullscreenActivity.this.finish();
			}
		});
		slv.setOacl(new OnAppClickListener() {

			@Override
			public void onapp(int pos) {
				// TODO Auto-generated method stub
				Log.i("test", "pos:" + pos);
				if (pos == slv.getApps().size()) {
					Log.i("test", "aaaaaaaa11111");
					Intent intent = new Intent();
					intent.setClass(FullscreenActivity.this,
							APPInfoActivity.class);
					intent.putExtra("num", pos);
					startActivity(intent);
				} else if (pos < slv.getApps().size()) {
					String pacn = sp.getString("" + pos, null);
					if (pos != -1 && pacn != null) {
						Intent intent = packageManager
								.getLaunchIntentForPackage(pacn);
						if (intent != null) {
							startActivity(intent);
							CacheState.isLockScreen = false;
							FullscreenActivity.this.finish();
						}
					}
				}
			}
		});
		slv.setOlc(new OnLongClickListener() {

			@Override
			public void onlong(int pos) {
				// TODO Auto-generated method stub
				if (first) {
					first = false;
					Log.i("test", "pos:" + pos);
					Intent intent = new Intent();
					intent.setClass(FullscreenActivity.this,
							DeleteAppActivity.class);
					intent.putExtra("pos", pos);
					startActivity(intent);
				}
			}
		});

		slv.setO2ll(new On2SLockListener() {

			@Override
			public void onlock() {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setAction(Intent.ACTION_SCREEN_OFF);
				// Log.i("test", "asdas");
				// sendBroadcast(intent);
				FullscreenActivity.this.finish();
			}
		});

		hand.post(run);
	}

	private PackageManager packageManager;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 设置快速启动栏按钮

		lsd = new ArrayList<Drawable>();
		lsn = new ArrayList<String>();
		for (int i = 0; i < 9; i++) {
			String pacname = sp.getString("" + i, null);
			lsn.add(pacname);
			if (pacname != null) {
				try {
					Drawable da = packageManager.getApplicationIcon(pacname);
					lsd.add(da);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		slv.setApps(lsd);
		first = true;

		super.onResume();
	}

	private Handler hand = new Handler();

	private Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 设置date
			y = Calendar.getInstance().get(Calendar.YEAR);
			m = Calendar.getInstance().get(Calendar.MONTH);
			d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			ho = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			mi = Calendar.getInstance().get(Calendar.MINUTE);
			slv.setTime(String.format("%02d:%02d", ho, mi));
			// ?"2013/5/8       多云   30℃"
			slv.setCsdanddate(y + "/" + (m + 1) + "/" + d + "       " + "多云"
					+ "   " + "30℃");
			hand.postDelayed(run, 60000);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// lockLayer.unlock();
		hand.removeCallbacks(run);
		super.onDestroy();
	}
}
