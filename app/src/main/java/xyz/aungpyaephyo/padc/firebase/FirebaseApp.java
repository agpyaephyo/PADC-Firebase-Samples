package xyz.aungpyaephyo.padc.firebase;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by aung on 8/18/17.
 */

public class FirebaseApp extends Application {

    public static final String TAG = "PADC-Firebase-Samples";

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
    }
}
