package com.andreasfurster.ailauncher.views;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andreasfurster.ailauncher.R;
import com.andreasfurster.ailauncher.presenters.LauncherPresenter;

import java.util.List;

public class LauncherActivity extends Activity implements LauncherPresenter.View {

    private LauncherPresenter<LauncherActivity> _manager;
    private List<ResolveInfo> _appsInfo;
    private PackageManager _packageManager;
    private ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        _manager = new LauncherPresenter<>(this);
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

    // TODO: https://developer.android.com/guide/topics/ui/layout/recyclerview
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
