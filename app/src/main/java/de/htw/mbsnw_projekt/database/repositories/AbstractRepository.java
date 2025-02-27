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

    /**
     * Führt eine Datenbankanfrage auf einem Backgroundthread aus
     * @param runnables Datenbankanfrage
     */
    protected void doInBackground(Runnable... runnables) {
        Thread t = new Thread(() -> {
            for (Runnable r : runnables) {
                r.run();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * Führt eine Datenbankanfrage auf einem Backgroundthread aus und führt eine Task aus
     * @param getter Datenbankanfrage
     * @param task Task
     */
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
