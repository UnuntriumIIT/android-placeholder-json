package com.example.vsomaku;

import android.app.Application;

import com.example.vsomaku.components.AppComponent;
import com.example.vsomaku.components.DaggerAppComponent;
import com.example.vsomaku.modules.AppContextModule;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().appContextModule(new AppContextModule(this)).build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
