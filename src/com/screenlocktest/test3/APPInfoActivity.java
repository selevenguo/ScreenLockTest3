package com.screenlocktest.test3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.screenlocktest3.R;

public class APPInfoActivity extends Activity {
	private ListView lv;
	private int num = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_appinfo);

		lv = (ListView) findViewById(R.id.lv);
		num = getIntent().getIntExtra("num", -1);

		PackageManager packageManager = getPackageManager();

		final List<PackageInfo> list = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		// List<String> ls = new ArrayList<String>();
		// for (PackageInfo packageInfo : list) {
		// ls.add(packageInfo.applicationInfo.loadLabel(packageManager)
		// .toString());
		// }
		// lv.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, ls));

		lv.setAdapter(new APPInfoAdapter(this, list));

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getSharedPreferences("applist", MODE_PRIVATE)
						.edit()
						.putString("" + num,
								list.get(arg2).applicationInfo.packageName)
						.commit();
				APPInfoActivity.this.finish();
			}
		});
	}
}
