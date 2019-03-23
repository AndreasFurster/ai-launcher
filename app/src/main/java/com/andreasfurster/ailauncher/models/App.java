package com.andreasfurster.ailauncher.models;

import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class App {
    private String activityInfoName;
    private String applicationInfoPackageName;

    public String getLabel() {
        return label;
    }

    public App setLabel(String label) {
        this.label = label;
        return this;
    }

    public Drawable getIcon() {
        return icon;
    }

    public App setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    private String label;
    private Drawable icon;

    public String getActivityInfoName() {
        return activityInfoName;
    }

    public App setActivityInfoName(String activityInfoName) {
        this.activityInfoName = activityInfoName;
        return this;
    }

    public String getApplicationInfoPackageName() {
        return applicationInfoPackageName;
    }

    public App setApplicationInfoPackageName(String applicationInfoPackageName) {
        this.applicationInfoPackageName = applicationInfoPackageName;
        return this;
    }

}
