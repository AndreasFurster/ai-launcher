package com.andreasfurster.ailauncher.managers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LauncherManager<T extends Activity & LauncherManager.View> {
    private final T _view;
    private final PackageManager _packageManager;

    public LauncherManager(T view) {
        _view = view;
        _packageManager = view.getPackageManager();
    }

    public void LoadApps() {
        Intent filterIntent = new Intent(Intent.ACTION_MAIN, null);
        filterIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableIntents = _packageManager.queryIntentActivities(filterIntent, 0);

        if (availableIntents.size() > 0) {
            Collections.sort(availableIntents, new Comparator<ResolveInfo>() {
                @Override
                public int compare(final ResolveInfo object1, final ResolveInfo object2) {
                    return object1.loadLabel(_packageManager).toString().compareTo(object2.loadLabel(_packageManager).toString());
                }
            });
        }

        _view.ShowApps(availableIntents);
    }

    public void StartActivity(ResolveInfo item) {
        Intent intent = new Intent();
        intent.setClassName(item.activityInfo.applicationInfo.packageName, item.activityInfo.name);

        _view.startActivity(intent);
    }

    public interface View {
        void ShowApps(List<ResolveInfo> availableIntents);
    }
}
