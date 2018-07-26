package com.example.fcl.daggerdemo;

public class ComponentHolder {

    private static AppComponent appComponent;

    public static void setAppComponent(AppComponent appComponent) {
        ComponentHolder.appComponent = appComponent;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
