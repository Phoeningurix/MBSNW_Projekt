package de.htw.mbsnw_projekt.database.repositories;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.function.Consumer;


public abstract class AbstractRepository implements Repository {

    private static final String TAG = "AbstractRepository";

    private final Handler handler;

    protected AbstractRepository() {
        handler = new Handler(Looper.getMainLooper());
    }

    protected void doInBackground(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    protected <E> void doInBackground(Callable<E> getter, Consumer<E> task) {
        doInBackground(() -> {
            try {
                E entity = getter.call();
                handler.post(() -> task.accept(entity));
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: failed", e);
            }
        });
    }

}
