package com.screenlocktest.test3;

import java.util.List;

import com.example.screenlocktest3.R;
import com.screenlocktest.test3.APPInfoAdapter.ViewHolder;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LuncherAdapter extends BaseAdapter {
	private List<ActivityInfo> list;
	private LayoutInflater lif;
	private PackageManager packageManager;

	public LuncherAdapter(Context context, List<ActivityInfo> list) {
		this.list = list;
		this.lif = LayoutInflater.from(context);
		packageManager = context.getPackageManager();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return list == null ? 0 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder;
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = lif.inflate(R.layout.item_lv, null);
			viewholder.apppic = (ImageView) convertView
					.findViewById(R.id.appicon);
			viewholder.appname = (TextView) convertView
					.findViewById(R.id.appname);

			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}

		viewholder.apppic.setImageDrawable(list.get(position).loadIcon(
				packageManager));
		viewholder.appname.setText(""
				+ list.get(position).loadLabel(packageManager).toString());
		return convertView;
	}

	public class ViewHolder {
		ImageView apppic;
		TextView appname;
	}
}
