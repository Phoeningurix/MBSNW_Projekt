package de.htw.mbsnw_projekt.app;

import de.htw.mbsnw_projekt.database.AppDatabase;

public final class App {
    //Blub

    public static AndroidApp getAndroidApp() {
        return AndroidApp.getInstance();
    }

    public static AppDatabase getDatabase() {
        return AppDatabase.getInstance(getAndroidApp().getApplicationContext());
    }

}
