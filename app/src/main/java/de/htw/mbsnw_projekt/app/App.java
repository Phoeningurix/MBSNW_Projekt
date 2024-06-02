package de.htw.mbsnw_projekt.app;

import de.htw.mbsnw_projekt.database.AppDatabase;
import de.htw.mbsnw_projekt.database.repositories.Repository;
import de.htw.mbsnw_projekt.database.repositories.RepositoryImpl;
import de.htw.mbsnw_projekt.logic.GameLogic;
import de.htw.mbsnw_projekt.logic.GameLogicImpl;

public final class App {
    //Blub

    public static AndroidApp getAndroidApp() {
        return AndroidApp.getInstance();
    }

    public static AppDatabase getDatabase() {
        return AppDatabase.getInstance(getAndroidApp().getApplicationContext());
    }

    public static Repository getRepository() {
        return RepositoryImpl.getInstance();
    }

    public static GameLogic getGameLogic() {
        return GameLogicImpl.getInstance();
    }

}
