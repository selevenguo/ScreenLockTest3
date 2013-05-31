package com.screenlocktest.test3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.screenlocktest3.R;

public class LuncherListActivity extends Activity {
	private ListView ls;
	private SharedPreferences sp;
	private PackageManager packageManager;
	private List<ActivityInfo> lst;
	private LuncherAdapter adp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_luncher);
		sp = getSharedPreferences("applist", MODE_PRIVATE);

		ls = (ListView) findViewById(R.id.lunchls);

		packageManager = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> list = packageManager
				.queryIntentActivities(intent, 0);
		lst = new ArrayList<ActivityInfo>();
		for (ResolveInfo resolveInfo : list) {
			lst.add(resolveInfo.activityInfo);
		}
		adp = new LuncherAdapter(this, lst);
		ls.setAdapter(adp);

		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				sp.edit()
						.putString(
								"lunchername",
								((ActivityInfo) (adp.getItem(arg2))).loadLabel(
										packageManager).toString()).commit();
				sp.edit()
						.putString(
								"luncherpacname",
								((ActivityInfo) (adp.getItem(arg2))).packageName)
						.commit();
				LuncherListActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}
