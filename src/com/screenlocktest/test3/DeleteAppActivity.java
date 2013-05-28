package com.screenlocktest.test3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.screenlocktest3.R;

public class DeleteAppActivity extends Activity {
	private ImageView iv;
	private TextView tv;
	private Button btok, btcn;
	private List<String> lsn;
	private SharedPreferences sp;
	private PackageManager packageManager;
	private int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_deleteapp);

		pos = getIntent().getIntExtra("pos", -1);

		lsn = new ArrayList<String>();

		packageManager = getPackageManager();

		iv = (ImageView) findViewById(R.id.iv);
		tv = (TextView) findViewById(R.id.tv);
		btok = (Button) findViewById(R.id.btok);
		btcn = (Button) findViewById(R.id.btcn);

		sp = getSharedPreferences("applist", MODE_PRIVATE);
		for (int i = 0; i < 9; i++) {
			String pacname = sp.getString("" + i, null);
			lsn.add(pacname);
			if (pacname != null) {
				try {
					if (pos == i) {
						Drawable da = packageManager
								.getApplicationIcon(pacname);
						iv.setImageDrawable(da);
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		btcn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteAppActivity.this.finish();
			}
		});

		btok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lsn.remove(pos);
				sp.edit().clear().commit();
				for (int i = 0; i < lsn.size(); i++) {
					sp.edit().putString("" + i, lsn.get(i)).commit();
				}
				DeleteAppActivity.this.finish();
			}
		});
	}
}
