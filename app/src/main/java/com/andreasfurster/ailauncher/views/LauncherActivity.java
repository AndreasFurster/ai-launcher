package com.andreasfurster.ailauncher.views;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreasfurster.ailauncher.R;
import com.andreasfurster.ailauncher.models.App;
import com.andreasfurster.ailauncher.presenters.LauncherPresenter;

import java.util.ArrayList;

public class LauncherActivity extends Activity implements LauncherPresenter.View {

    private LauncherPresenter<LauncherActivity> _presenter;
    private ArrayList<App> _appsInfo;
    private PackageManager _packageManager;

    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        _presenter = new LauncherPresenter<>(this);
        _packageManager = getPackageManager();

        // Get recyclerView
        _recyclerView = findViewById(R.id.AppsListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        _recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);

        _presenter.LoadApps();
    }

    @Override
    public void ShowApps(ArrayList<App> availableIntents) {
        _appsInfo = availableIntents;

        // specify an adapter (see also next example)
        _adapter = new AppListAdapter(_appsInfo);
        _recyclerView.setAdapter(_adapter);
    }

    class AppListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        class AppListAdapterViewHolder extends RecyclerView.ViewHolder {
            ImageView iconView;
            TextView labelView;

            AppListAdapterViewHolder(View view){
                super(view);
                iconView = view.findViewById(R.id.iconView);
                labelView = view.findViewById(R.id.labelView);
            }
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = _recyclerView.getChildLayoutPosition(v);
                App item = _data.get(itemPosition);
                _presenter.StartActivity(item);
            }
        };

        private ArrayList<App> _data;

        AppListAdapter(ArrayList<App> data){
            _data = data;
        }

        @NonNull
        @Override
        public AppListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_appinfo, parent, false);
            view.setOnClickListener(mOnClickListener);
            return new AppListAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            AppListAdapterViewHolder viewHolder = (AppListAdapterViewHolder) holder;

            App item = _data.get(i);
            viewHolder.iconView.setImageDrawable(item.getIcon());
            viewHolder.labelView.setText(item.getLabel());
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }
    }
}
