package com.andreasfurster.ailauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andreasfurster.ailauncher.managers.LauncherManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LauncherActivity extends Activity implements LauncherManager.View {

    private LauncherManager<LauncherActivity> _manager;
    private List<ResolveInfo> _appsInfo;
    private PackageManager _packageManager;
    private ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        _manager = new LauncherManager<>(this);
        _packageManager = getPackageManager();
        _listView = findViewById(R.id.AppsListView);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo item = (ResolveInfo) parent.getItemAtPosition(position);
                _manager.StartActivity(item);
            }
        });

        _manager.LoadApps();
    }

    @Override
    public void ShowApps(List<ResolveInfo> availableIntents) {
        _appsInfo = availableIntents;

        _listView.setAdapter(new AppListAdapter());
    }

    class AppListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return _appsInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return _appsInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO: https://developer.android.com/guide/topics/ui/layout/recyclerview
            ResolveInfo info = (ResolveInfo) getItem(position);

            View view = getLayoutInflater().inflate(R.layout.fragment_appinfo, null);

            ImageView iconView = view.findViewById(R.id.iconView);
            Drawable icon = info.loadIcon(_packageManager);
            iconView.setImageDrawable(icon);

            TextView textView = view.findViewById(R.id.intentNameView);
            textView.setText(info.loadLabel(_packageManager));

            return view;
        }
    }
}
