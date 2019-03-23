package com.andreasfurster.ailauncher.presenters;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.andreasfurster.ailauncher.models.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LauncherPresenter<T extends Activity & LauncherPresenter.View> {
    private final T _view;
    private final PackageManager _packageManager;

    public LauncherPresenter(T view) {
        _view = view;
        _packageManager = view.getPackageManager();
    }

    // TODO: Use viewmodel
    // TODO: Caching?
    public void LoadApps() {
        // Get all launch intents
        Intent filterIntent = new Intent(Intent.ACTION_MAIN, null);
        filterIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableIntents = _packageManager.queryIntentActivities(filterIntent, 0);

        // Sort intents
        if (availableIntents.size() > 0) {
            Collections.sort(availableIntents, new Comparator<ResolveInfo>() {
                @Override
                public int compare(final ResolveInfo object1, final ResolveInfo object2) {
                    return object1.loadLabel(_packageManager).toString().compareTo(object2.loadLabel(_packageManager).toString());
                }
            });
        }

        ArrayList<App> apps = new ArrayList<>();

        for (ResolveInfo resolveInfo : availableIntents) {
            App app = new App();
            app.setActivityInfoName(resolveInfo.activityInfo.name);
            app.setApplicationInfoPackageName(resolveInfo.activityInfo.applicationInfo.packageName);
            app.setIcon(resolveInfo.loadIcon(_packageManager));
            app.setLabel(resolveInfo.loadLabel(_packageManager).toString());

            apps.add(app);
        }

        _view.ShowApps(apps);
    }

    public void StartActivity(ResolveInfo item) {
        Intent intent = new Intent();
        intent.setClassName(item.activityInfo.applicationInfo.packageName, item.activityInfo.name);

        _view.startActivity(intent);
    }

    public interface View {
        void ShowApps(ArrayList<App> availableIntents);
    }
}
