package de.htw.mbsnw_projekt.app;

import android.app.Application;
import android.util.Log;

public class AndroidApp extends Application {

    private static final String TAG = "AndroidApp";

    private static volatile AndroidApp instance;

    public static AndroidApp getInstance() {
        if (instance == null) {
            synchronized (AndroidApp.class) {
                if (instance == null) {
                    try {
                        AndroidApp.class.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }

    private static void setInstance(AndroidApp app) {
        if (instance == null) {
            synchronized (AndroidApp.class) {
                if (instance == null) {
                    instance = app;
                    Log.d(TAG, "setInstance: Instance set.");
                    AndroidApp.class.notifyAll();
                }
            }
        } else {
            Log.w(TAG, "setInstance: Instance already set!!!");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }
}
